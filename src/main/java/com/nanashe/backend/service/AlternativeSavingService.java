package com.nanashe.backend.service;

import com.nanashe.backend.dto.alternatives.response.AiAlternativeResponseDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Product product = productRepository.findFirstByAliasesNameIn(event.aliases())
                .orElseGet(() -> createProduct(event));
        log.debug("Found product matching aliases {}: {}", event.aliases(), product != null ? product.getId() : "none");

        if (product == null) return;

        List<Alternative> saved = new ArrayList<>();
        for (AiAlternativeResponseDto dto : event.alternatives()) {
            if (alternativeRepository.existsByNameIgnoreCase(dto.name())) {
                log.debug("Alternative '{}' already exists, skipping", dto.name());
                continue;
            }
            log.debug("Saving new alternative '{}'", dto.name());
            toAlternative(dto).ifPresent(alt -> saved.add(alternativeRepository.save(alt)));
        }

        if (saved.isEmpty()) {
            log.info("No new alternatives to link for product='{}', all skipped", event.productName());
            return;
        }

        if (product.getAlternatives() == null) {
            product.setAlternatives(new ArrayList<>());
        }
        product.getAlternatives().addAll(saved);
        productRepository.save(product);

        log.info("Saved {} alternative(s) and linked them to product '{}' for aliases {}",
                saved.size(), event.productName(), event.aliases());
    }

    private Product createProduct(KafkaAlternativesEvent event) {
        log.debug("createProduct: looking up category='{}', country='{}'", event.productCategory(), event.productCountry());

        Optional<Category> category = categoryRepository.findByNameIgnoreCase(event.productCategory());
        Optional<Country> country = countryRepository.findByNameIgnoreCase(event.productCountry());

        if (category.isEmpty() || country.isEmpty()) {
            log.warn("Cannot create product '{}': category='{}' found={}, country='{}' found={}",
                    event.productName(), event.productCategory(), category.isPresent(),
                    event.productCountry(), country.isPresent());
            return null;
        }

        List<Alias> aliases = event.aliases().stream()
                .map(Alias::fromString)
                .toList();

        Product saved = productRepository.save(Product.builder()
                .name(event.productName())
                .category(category.get())
                .origin(country.get())
                .aliases(aliases)
                .build());
        log.info("Created new product id={} name='{}' with {} alias(es): {}",
                saved.getId(), saved.getName(), aliases.size(), event.aliases());
        return saved;
    }

    private Optional<Alternative> toAlternative(AiAlternativeResponseDto dto) {
        log.debug("toAlternative: resolving category='{}', country='{}' for alternative='{}'",
                dto.category(), dto.country(), dto.name());

        Optional<Category> category = categoryRepository.findByNameIgnoreCase(dto.category());
        Optional<Country> country = countryRepository.findByNameIgnoreCase(dto.country());

        if (category.isEmpty() || country.isEmpty()) {
            log.warn("Skipping alternative '{}': category='{}' found={}, country='{}' found={}",
                    dto.name(), dto.category(), category.isPresent(), dto.country(), country.isPresent());
            return Optional.empty();
        }

        Alternative alt = new Alternative();
        alt.setName(dto.name());
        alt.setCategory(category.get());
        alt.setOrigin(country.get());
        alt.setDescription(dto.description());
        alt.setUrl(dto.url());
        alt.setAiGenerated(true);
        log.debug("Built alternative: name='{}', category='{}', country='{}'", alt.getName(), dto.category(), dto.country());
        return Optional.of(alt);
    }
}
