--liquibase formatted sql
--changeset kpichlinski:2
alter table product add image varchar(128) after currency;