create sequence produto_servico_seq;

create table produto_servico(

	id bigint not null default nextval ('produto_servico_seq'),
	codigo varchar(36) not null,
	nome varchar(50) not null,
	descricao varchar(100) not null,
	preco decimal(10,2) not null,
	tipo varchar(10) not null,
	ativo boolean not null,
	
	
	primary key(id)

)