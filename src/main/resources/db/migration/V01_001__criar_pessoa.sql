CREATE TABLE pessoa(
id uuid not null primary key,
nome varchar,
cpf_cnpj varchar,
codigo varchar,
data_cadastro date,
tipo_pessoa varchar,
data_exclusao timestamp
);