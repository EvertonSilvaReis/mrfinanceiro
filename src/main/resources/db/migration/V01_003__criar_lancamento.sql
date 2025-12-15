CREATE TABLE lancamento(
id uuid not null primary key,
descricao varchar,
codigo varchar,
numero_documento varchar,
tipo_lancamento varchar,
valor_titulo decimal,
desconto decimal,
data_emissao date,
data_vencimento date,
valor_pagamento decimal,
data_pagamento date,
situacao varchar,
observacao varchar,
id_tipo_pagamento uuid,
parcela bigint,
id_pessoa uuid,
data_exclusao timestamp,

constraint lancamento_tipo_pagamento_fk foreign key(id_tipo_pagamento) references tipo_pagamento(id),
constraint lancamento_pessoa_fk foreign key(id_pessoa) references pessoa(id)
);