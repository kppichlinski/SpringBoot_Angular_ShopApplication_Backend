--liquibase formatted sql
--changeset kpichlinski:9

alter table review add column moderated boolean default false;