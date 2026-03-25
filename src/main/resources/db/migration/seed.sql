-- ——— 1. Categories ———
INSERT INTO categories (id, parent_id, name, icon)
VALUES
-- Parent Categories
(1, NULL, 'Technologies', '🖥'),
(2, NULL, 'Content Makers', '🎙'),
(3, NULL, 'Video Games', '🕹'),
(4, NULL, 'International Sponsors', '💸'),

-- Technologies Subcategories
(5, 1, 'Messengers', '💬'),
(6, 1, 'Programming languages & Dev tools', '⚙️'),
(7, 1, 'Business Software (CRM/ERP)', '💼'),
(8, 1, 'Website Builders', '🏗️'),
(9, 1, 'Antivirus Software', '🛡️'),
(10, 1, 'Educational Platforms', '🎓'),

-- Content Makers Subcategories
(11, 2, 'Musicians', '🎵'),
(12, 2, 'YouTubers & Bloggers', '📹'),
(13, 2, 'Movies & TV Series', '🎬'),
(14, 2, 'Online Courses', '📚'),

-- Video Games Subcategories
(15, 3, 'PC & Console Games', '🎮'),
(16, 3, 'Mobile Games', '📱'),

-- International Sponsors Subcategories
(17, 4, 'Food & Snacks', '🍔'),
(18, 4, 'Cosmetics & Hygiene', '🧴'),
(19, 4, 'Retail Chains', '🛒'),
(20, 4, 'Electronics', '🔌');


-- ——— 2. Countries ———
INSERT INTO countries (id, code, name, is_friendly)
VALUES (1, 'RU', 'Russia', false),
       (2, 'UA', 'Ukraine', true),
       (3, 'US', 'United States', true),
       (4, 'PL', 'Poland', true),
       (5, 'CH', 'Switzerland', true);


-- ——— 3. Products ———
INSERT INTO products (id, category_id, name, emoji, origin_id)
VALUES (1, 5, 'Telegram', '✈️', 1), -- mapped to 5 (Messengers)
       (2, 9, 'Kaspersky', '🦠', 1), -- mapped to 9 (Antivirus Software)
       (3, 7, '1C:Enterprise', '🧮', 1); -- mapped to 7 (Business Software (CRM/ERP))


-- ——— 4. Aliases ———
INSERT INTO aliases (id, product_id, name)
VALUES (1, 1, 'TG'),
       (2, 1, 'Телеграм'),
       (3, 2, 'Kaspersky Anti-Virus'),
       (4, 3, '1C');


-- ——— 5. Alternatives ———
INSERT INTO alternatives (id, name, origin_id, pricing_model, description, url, ai_generated)
VALUES (1, 'Signal', 3, 4.9, 'free', 'End-to-end encrypted messaging app.', 'https://signal.org', false),
       (2, 'Threema', 5, 4.8, 'paid', 'Secure, privacy-first messenger.', 'https://threema.ch', false),
       (3, 'Bitdefender', 3, 4.7, 'paid', 'Comprehensive cybersecurity and antivirus.', 'https://bitdefender.com',
        false),
       (4, 'MacPaw CleanMyMac', 2, 4.9, 'freemium', 'Mac optimization and malware removal.', 'https://macpaw.com',
        false),
       (5, 'Finmap', 2, 4.6, 'freemium', 'Cash flow management tool for modern business.', 'https://finmap.online',
        false);


-- ——— 6. Product Alternatives (The Mapping) ———
INSERT INTO product_alternatives (product_id, alternative_id)
VALUES (1, 1), -- Telegram -> Signal
       (1, 2), -- Telegram -> Threema
       (2, 3), -- Kaspersky -> Bitdefender
       (2, 4), -- Kaspersky -> MacPaw
       (3, 5); -- 1C -> Finmap


-- ——— 7. Reset Sequences ———
-- Updates the PostgreSQL internal counters so future inserts don't throw primary key errors.
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
SELECT setval('countries_id_seq', (SELECT MAX(id) FROM countries));
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
SELECT setval('aliases_id_seq', (SELECT MAX(id) FROM aliases));
SELECT setval('alternatives_id_seq', (SELECT MAX(id) FROM alternatives));