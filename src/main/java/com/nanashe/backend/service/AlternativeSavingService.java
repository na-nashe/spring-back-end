package com.nanashe.backend.service;

import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;
import com.nanashe.backend.entity.Alias;
import com.nanashe.backend.entity.Alternative;
import com.nanashe.backend.entity.Category;
import com.nanashe.backend.entity.Country;
import com.nanashe.backend.entity.Product;
import com.nanashe.backend.kafka.event.KafkaAlternativesEvent;
import com.nanashe.backend.repository.AlternativeRepository;
import com.nanashe.backend.repository.CategoryRepository;
import com.nanashe.backend.repository.CountryRepository;
import com.nanashe.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlternativeSavingService {

    private final AlternativeRepository alternativeRepository;
    private final CategoryRepository categoryRepository;
    private final CountryRepository countryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void saveAlternatives(KafkaAlternativesEvent event) {
        log.debug("saveAlternatives called: product='{}', aliases={}, alternatives={}",
                event.productName(),
                event.hasAliases() ? event.aliases().size() : 0,
                event.hasAlternatives() ? event.alternatives().size() : 0);

        if (!event.hasAliases() || !event.hasAlternatives()) {
            log.warn("saveAlternatives aborted: aliases or alternatives are null/empty for product='{}'", event.productName());
            return;
        }

        Product product = productRepository.findByAliasesNameIn(event.aliases())
                .orElseGet(() -> createProduct(event));
        log.debug("Found product matching aliases {}: {}", event.aliases(), product != null ? product.getId() : "none");

        if (product == null) return;

        List<Alternative> newAlternatives = findNewAlternatives(event);

        if (newAlternatives.isEmpty()) {
            log.info("No new alternatives to link for product='{}', all skipped", event.productName());
            return;
        }

        product.addAlternatives(newAlternatives);
        productRepository.save(product);

        log.info("Saved {} alternative(s) and linked them to product '{}' for aliases {}",
                newAlternatives.size(), event.productName(), event.aliases());
    }

    private Product createProduct(KafkaAlternativesEvent event) {
        log.debug("createProduct: looking up category='{}', country='{}'", event.productCategory(), event.productCountry());

        Optional<Category> category = categoryRepository.findByNameIgnoreCase(event.productCategory());

        if (category.isEmpty()) {
            log.warn("Cannot create product '{}': category='{}' not found",
                    event.productName(), event.productCategory());
            return null;
        }

        Country country = countryRepository.findByNameIgnoreCase(event.productCountry())
                .orElseGet(() -> {
                    log.info("Creating new country '{}'", event.productCountry());
                    return countryRepository.save(Country.builder()
                            .name(event.productCountry())
                            .isFriendly(true)
                            .build());
                });

        List<Alias> aliases = event.aliases().stream()
                .map(Alias::fromString)
                .toList();

        Product saved = productRepository.save(Product.builder()
                .name(event.productName())
                .category(category.get())
                .origin(country)
                .aliases(aliases)
                .build());

        log.info("Created new product id={} name='{}' with {} alias(es): {}",
                saved.getId(), saved.getName(), aliases.size(), event.aliases());
        return saved;
    }

    private List<Alternative> findNewAlternatives(KafkaAlternativesEvent event) {
        String[] names = event.alternatives().stream()
                .map(AlternativeResponseDto::getName)
                .toArray(String[]::new);
        Set<String> newNames = new HashSet<>(alternativeRepository.findNewAlternativeNames(names));

        return event.alternatives()
                .stream()
                .filter(alternative -> newNames.contains(alternative.getName()))
                .map(dto -> toAlternative(dto, event))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<Alternative> toAlternative(AlternativeResponseDto dto, KafkaAlternativesEvent event) {
        log.debug("toAlternative: resolving category='{}', country='{}' for alternative='{}'",
                event.productCategory(), dto.getCountry(), dto.getName());

        Optional<Category> category = categoryRepository.findByNameIgnoreCase(event.productCategory());
        Optional<Country> country = countryRepository.findByNameIgnoreCase(dto.getCountry());

        if (category.isEmpty() || country.isEmpty()) {
            log.warn("Skipping alternative '{}': category='{}' found={}, country='{}' found={}",
                    dto.getName(), event.productCategory(), category.isPresent(), dto.getCountry(), country.isPresent());
            return Optional.empty();
        }

        Alternative alt = Alternative.builder()
                .name(dto.getName())
                .category(category.get())
                .origin(country.get())
                .description(dto.getDescription())
                .url(dto.getUrl())
                .aiGenerated(true)
                .build();
        log.debug("Built alternative: name='{}', category='{}', country='{}'", alt.getName(), event.productCategory(), dto.getCountry());
        return Optional.of(alt);
    }
}
