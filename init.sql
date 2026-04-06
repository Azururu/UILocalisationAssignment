CREATE DATABASE IF NOT EXISTS fuel_calculator_localization

CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE fuel_calculator_localization;
CREATE TABLE IF NOT EXISTS calculation_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    distance DOUBLE NOT NULL,
    consumption DOUBLE NOT NULL,
    price DOUBLE NOT NULL,
    total_fuel DOUBLE NOT NULL,
    total_cost DOUBLE NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS localization_strings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL,
    UNIQUE KEY unique_key_lang (`key`, `language`)
);

INSERT INTO localization_strings (`key`, value, language) VALUES

-- 🇺🇸 English
('tripDistanceText', 'Trip distance (km)', 'en'),
('fuelConsumptionRateText', 'Consumption rate (L/100 km)', 'en'),
('fuelCostText', 'Fuel cost (€)', 'en'),
('btnCalculate', 'Calculate', 'en'),
('resultText', 'Total fuel needed: {0} liters\nTotal fuel cost: {1}€', 'en'),
('invalidInput', 'Invalid input', 'en'),
('dbError', 'Failed to load language data', 'en'),

-- 🇫🇷 French
('tripDistanceText', 'Distance du trajet (km)', 'fr'),
('fuelConsumptionRateText', 'Consommation (L/100 km)', 'fr'),
('fuelCostText', 'Prix du carburant (€)', 'fr'),
('btnCalculate', 'Calculer', 'fr'),
('resultText', 'Carburant total nécessaire : {0} litres\nCoût total du carburant : {1}€', 'fr'),
('invalidInput', 'Entrée invalide', 'fr'),
('dbError', 'Échec du chargement des données', 'fr'),

-- 🇯🇵 Japanese
('tripDistanceText', '走行距離 (km)', 'ja'),
('fuelConsumptionRateText', '燃費 (L/100km)', 'ja'),
('fuelCostText', '燃料価格 (€)', 'ja'),
('btnCalculate', '計算する', 'ja'),
('resultText', '必要な燃料: {0} リットル\n合計コスト: {1}€', 'ja'),
('invalidInput', '無効な入力です', 'ja'),
('dbError', 'データの読み込みに失敗しました', 'ja'),

-- 🇮🇷 Persian (Farsi)
('tripDistanceText', 'مسافت سفر (کیلومتر)', 'fa'),
('fuelConsumptionRateText', 'مصرف سوخت (لیتر در 100 کیلومتر)', 'fa'),
('fuelCostText', 'هزینه سوخت (€)', 'fa'),
('btnCalculate', 'محاسبه', 'fa'),
('resultText', 'سوخت مورد نیاز: {0} لیتر\nهزینه کل: {1}€', 'fa'),
('invalidInput', 'ورودی نامعتبر است', 'fa'),
('dbError', 'بارگذاری داده‌ها ناموفق بود', 'fa')

ON DUPLICATE KEY UPDATE value = VALUES(value);