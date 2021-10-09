create sequence item_pedido_seq;

create table item_pedido(
    id bigint not null default nextval ('item_pedido_seq'),
    codigo varchar(36) not null,
    quantidade smallint not null,
    preco_unitario decimal(10,2) not null,
    preco_total decimal(10,2) not null,
    observacao varchar(255) null,
    pedido_id bigint not null,
    produto_servico_id bigint not null,
 
 	primary key (id),
    constraint uk_item_pedido_produto_servico unique (pedido_id, produto_servico_id),

    constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id),
    constraint fk_item_pedido_produto_servico foreign key (produto_servico_id) references produto_servico (id)
 
 );
