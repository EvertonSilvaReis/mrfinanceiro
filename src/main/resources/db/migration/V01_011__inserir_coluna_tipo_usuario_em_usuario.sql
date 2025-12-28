ALTER TABLE usuario ADD COLUMN IF NOT EXISTS data_exclusao timestamp;
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS tipo_usuario bigint;