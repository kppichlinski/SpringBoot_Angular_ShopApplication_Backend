--liquibase formatted sql
--changeset kpichlinski:14
alter table `order` add payment_id bigint;
update `order` set payment_id = 1;
alter table `order` modify payment_id bigint not null;
alter table `order` add constraint fk_order_payment_id foreign key (payment_id) references payment(id);