ALTER TABLE lancamento ADD COLUMN IF NOT EXISTS id_conta uuid;

ALTER TABLE lancamento ADD CONSTRAINT lancamento_conta_fk FOREIGN KEY (id_conta) REFERENCES conta(id);

ALTER TABLE pessoa ADD COLUMN IF NOT EXISTS ativo boolean;

ALTER TABLE tipo_pagamento ADD COLUMN IF NOT EXISTS ativo boolean;