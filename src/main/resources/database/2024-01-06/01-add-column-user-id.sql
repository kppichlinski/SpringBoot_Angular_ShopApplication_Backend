--liquibase formatted sql
--changeset kpichlinski:21
alter table `order` add user_id bigint;
--changeset kpichlinski:22
alter table `order` add constraint fk_order_user_id foreign key (user_id) references users(id);