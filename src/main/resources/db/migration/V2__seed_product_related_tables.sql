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

-- ——— 7. Reset Sequences ———
-- Updates the PostgreSQL internal counters so future inserts don't throw primary key errors.
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
SELECT setval('countries_id_seq', (SELECT MAX(id) FROM countries));