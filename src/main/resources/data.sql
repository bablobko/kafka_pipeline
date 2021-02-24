DROP TABLE IF EXISTS Trade;

CREATE TABLE Trade (
  id INT AUTO_INCREMENT PRIMARY KEY,
  trade_id VARCHAR(100) NOT NULL,
  version INT NOT NULL,
  counter_party_id VARCHAR(100) NOT NULL,
  book_id VARCHAR(100) NOT NULL,
  maturity_date DATE,
  created_date DATE,
  expired char(1)
);


