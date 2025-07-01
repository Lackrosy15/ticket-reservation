CREATE TABLE IF NOT EXISTS seats (
    id SERIAL PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    passport_number VARCHAR(32)
);

INSERT INTO seats (status, passport_number) VALUES
  ('free', NULL),
  ('free', NULL),
  ('free', NULL),
  ('free', NULL),
  ('free', NULL); 