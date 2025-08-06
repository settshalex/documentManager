# Getting Started

### Command
./mvnw spring-boot:run

```
CREATE EXTENSION pg_trgm;
CREATE EXTENSION btree_gin;
create table public.users
(
    id          bigserial
        constraint users_pk
            primary key,
    username    varchar(200) not null,
    preferences jsonb,
    password    varchar(200),
    role        varchar(30) default 'USER'::character varying
);

alter table public.users
    owner to postgres;

create unique index users_id_uindex
    on public.users (id);

create unique index users_email_uindex
    on public.users (username);

create table public.document_type
(
    id   bigserial
        constraint document_type_pk
            primary key,
    type text not null
);

alter table public.document_type
    owner to postgres;

create table public.documents
(
    id            bigserial
        constraint documents_pk
            primary key,
    title         text        not null,
    created_by    bigint      not null
        constraint documents_users_id_fk
            references public.users
            on update cascade on delete set null,
    last_updated  timestamp,
    document_type bigint
        constraint documents_document_type_id_fk
            references public.document_type
            on delete set null,
    tags          text[],
    description   text,
    data          bytea,
    text          text,
    sha256        char(64),
    mime_type     varchar(255),
    filename      text
);

alter table public.documents
    owner to postgres;

create unique index documents_id_uindex
    on public.documents (id);

create unique index documents_sha256_uindex
    on public.documents (sha256, created_by);

create index documents_text_index
    on public.documents using gin (text);

create unique index document_type_id_uindex
    on public.document_type (id);

create unique index document_type_type_uindex
    on public.document_type (type);

create table public.document_comments
(
    id         bigserial
        constraint document_comments_pk
            primary key,
    created_by bigint not null
        constraint document_comments_users_id_fk
            references public.users
            on delete set null,
    comments   text   not null
);

alter table public.document_comments
    owner to postgres;

create unique index document_comments_id_uindex
    on public.document_comments (id);

create table public.document_versioning
(
    id          bigserial
        constraint document_versioning_pk
            primary key,
    created_by  bigint not null
        constraint document_versioning_users_id_fk
            references public.users
            on delete set null,
    patch       text   not null,
    document_id bigint not null
        constraint document_versioning_documents_id_fk
            references public.documents
            on delete cascade
);

alter table public.document_versioning
    owner to postgres;

create unique index document_versioning_id_uindex
    on public.document_versioning (id);
    
CREATE TABLE document_shares (
    id BIGSERIAL PRIMARY KEY,
    document_id BIGINT NOT NULL REFERENCES documents(id),
    shared_with_user_id BIGINT NOT NULL REFERENCES users(id),
    shared_by_user_id BIGINT NOT NULL REFERENCES users(id),
    shared_at TIMESTAMP NOT NULL,
    permission VARCHAR(10) NOT NULL,
    UNIQUE(document_id, shared_with_user_id)
);
CREATE TABLE document_tags (
    id BIGSERIAL PRIMARY KEY,
    document_id BIGINT NOT NULL REFERENCES documents(id) ON DELETE CASCADE,
    tag VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(document_id, tag)
);

CREATE INDEX idx_document_tags_tag ON document_tags(tag);


```