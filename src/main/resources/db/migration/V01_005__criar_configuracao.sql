CREATE TABLE configuracao(
id uuid not null primary key default gen_random_uuid(),
descricao varchar,
valor varchar
);

INSERT INTO configuracao(descricao, valor) VALUES ('ultimo-codigo-pessoa', '0');
INSERT INTO configuracao(descricao, valor) VALUES ('ultimo-codigo-lancamento', '0');
INSERT INTO configuracao(descricao, valor) VALUES ('ultimo-codigo-tipo-pagamento', '0');
INSERT INTO configuracao(descricao, valor) VALUES ('ultimo-codigo-usuario', '0');
