-- Drop refresh_token table since tokens are now stored in Redis
DROP TABLE IF EXISTS refresh_token CASCADE;
