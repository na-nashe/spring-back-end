-- ——— 1. Categories ———
INSERT INTO categories (id, parent_id, name, icon)
VALUES
-- Parent Categories
(1, NULL, 'Технології', '🖥'),
(2, NULL, 'Контент-мейкери', '🎙'),
(3, NULL, 'Відеоігри', '🕹'),
(4, NULL, 'Міжнародні спонсори', '💸'),

-- Technologies Subcategories
(5, 1, 'Месенджери', '💬'),
(6, 1, 'Мови програмування та інструменти розробки', '⚙️'),
(7, 1, 'ПЗ для бізнесу (CRM/ERP)', '💼'),
(8, 1, 'Конструктори сайтів', '🏗️'),
(9, 1, 'Антивіруси', '🛡️'),
(10, 1, 'Освітні платформи', '🎓'),

-- Content Makers Subcategories
(11, 2, 'Музиканти', '🎵'),
(12, 2, 'Ютубери та блогери', '📹'),
(13, 2, 'Фільми та серіали', '🎬'),
(14, 2, 'Онлайн-курси', '📚'),

-- Video Games Subcategories
(15, 3, 'Ігри для ПК та консолей', '🎮'),
(16, 3, 'Мобільні ігри', '📱'),

-- International Sponsors Subcategories
(17, 4, 'Їжа та снеки', '🍔'),
(18, 4, 'Косметика та гігієна', '🧴'),
(19, 4, 'Роздрібні мережі', '🛒'),
(20, 4, 'Електроніка', '🔌');


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