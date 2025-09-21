-- Client Processing
DROP DATABASE IF EXISTS client_processing_db;
DROP USER IF EXISTS client_user;

CREATE DATABASE client_processing_db;
CREATE USER client_user WITH PASSWORD '123';
GRANT ALL PRIVILEGES ON DATABASE client_processing_db TO client_user;

-- Account Processing
DROP DATABASE IF EXISTS account_processing_db;
DROP USER IF EXISTS account_user;

CREATE DATABASE account_processing_db;
CREATE USER account_user WITH PASSWORD '456';
GRANT ALL PRIVILEGES ON DATABASE account_processing_db TO account_user;

-- Credit Processing
DROP DATABASE IF EXISTS credit_processing_db;
DROP USER IF EXISTS credit_user;

CREATE DATABASE credit_processing_db;
CREATE USER credit_user WITH PASSWORD '789';
GRANT ALL PRIVILEGES ON DATABASE credit_processing_db TO credit_user;
