--liquibase formatted sql
--changeset kpichlinski:4
alter table product add full_description text default null after description;