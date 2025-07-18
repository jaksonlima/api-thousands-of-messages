create table wallet (
    id varchar(36) primary key,
    name varchar(255) not null,
    active boolean not null default true,
    account_id varchar(36) not null,
    created_at timestamp without time zone not null default current_timestamp,
    updated_at timestamp without time zone not null default current_timestamp,
    constraint fk_wallet_account foreign key (account_id) references public.account (id)
);

create table category (
    id varchar(36) primary key,
    name varchar(255) not null,
    created_at timestamp without time zone not null default current_timestamp,
    updated_at timestamp without time zone not null default current_timestamp
);

create table transaction (
    id varchar(36) primary key,
    amount numeric(18, 2) not null,
    amount_type varchar(50) not null,
    currency varchar(10) not null,
    description text,
    wallet_id varchar(36) not null,
    category_id varchar(36) not null,
    created_at timestamp without time zone not null default current_timestamp,
    updated_at timestamp without time zone not null default current_timestamp,
    constraint fk_transaction_wallet foreign key (wallet_id) references wallet (id),
    constraint fk_transaction_category foreign key (category_id) references category (id)
);


