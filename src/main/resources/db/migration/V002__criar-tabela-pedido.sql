create sequence pedido_seq;

create table pedido(

    id bigint not null default nextval ('pedido_seq'),
    codigo varchar(36) not null,
    subtotal decimal(10,2) not null,
    valor_total decimal(10,2) not null,
    desconto varchar(3) not null,
    status varchar(10) not null,
    data_criacao timestamp(0) not null,
    data_confirmacao timestamp(0) null,
    data_cancelamento timestamp(0) null,
    data_entrega timestamp(0) null,

    primary key (id)
    
);