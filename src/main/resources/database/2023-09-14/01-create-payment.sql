--liquibase formatted sql
--changeset kpichlinski:14
create table payment(
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(64) not null,
    type varchar(32) not null,
    default_payment boolean default false,
    note text
);

insert into payment(id, name, type, default_payment, note)
values (1, 'Bank transfer', 'BANK_TRANSFER', true, 'Please make the transfer on this bank account:\n30 1030 1739 5825 1518 9904 4499\n and put the order number into the title');