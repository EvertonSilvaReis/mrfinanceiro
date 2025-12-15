CREATE TABLE tipo_pagamento(
id uuid not null primary key,
descricao varchar,
codigo varchar,
parcelado boolean,
parcelas bigint,
data_exclusao timestamp
);