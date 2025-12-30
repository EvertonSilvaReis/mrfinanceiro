CREATE TABLE usuario_perfil(
id uuid not null primary key,
id_usuario uuid,
role varchar,

constraint fk_usurio_perfil_usuario foreign key (id_usuario) references usuario(id)
);