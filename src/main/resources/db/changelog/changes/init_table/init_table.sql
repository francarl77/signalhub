CREATE TABLE signal_entity (
    index_signal bigserial not null ,
    signal_id bigint not null,
    object_id varchar(255) not null,
    eservice_id varchar(255) not null,
    object_type varchar(255) not null,
    signal_type varchar(255) not null,
    tms_insert timestamp not null,
    PRIMARY KEY(index_signal)
);

CREATE UNIQUE INDEX u01 ON signal_entity (signal_id, eservice_id);