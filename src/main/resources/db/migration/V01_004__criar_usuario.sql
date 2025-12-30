CREATE TABLE usuario(
id uuid not null primary key,
nome varchar,
codigo varchar,
email varchar,
senha varchar,
data_exclusao timestamp
);