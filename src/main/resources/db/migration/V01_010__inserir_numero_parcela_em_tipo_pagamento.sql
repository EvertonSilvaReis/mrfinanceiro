ALTER TABLE tipo_pagamento DROP COLUMN IF EXISTS parcelas;

ALTER TABLE tipo_pagamento ADD COLUMN IF NOT EXISTS numero_parcela bigint;