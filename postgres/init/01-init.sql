CREATE USER mp_tezea WITH CREATEROLE CREATEDB ENCRYPTED PASSWORD 'mp_tezea';
ALTER USER mp_tezea WITH SUPERUSER;
ALTER DATABASE tezea OWNER TO mp_tezea;
ALTER SCHEMA public OWNER TO mp_tezea;