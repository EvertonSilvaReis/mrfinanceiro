CREATE TABLE conta(
id uuid not null primary key,
descricao varchar,
numero_conta numeric,
agencia numeric,
saldo_inicial decimal,
saldo_atual decimal,
ativo boolean,
data_exclusao timestamp
);