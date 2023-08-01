CREATE TABLE signal_entity (
    index_signal serial not null ,
    signal_id varchar(255),
    object_id varchar(255),
    eservice_id varchar(255),
    object_type varchar(255),
    signal_type varchar(255),
    tms_insert timestamp,
    PRIMARY KEY(index_signal)
)