CREATE TABLE hibernate_sequence (
    sequence_name varchar(255) NOT NULL,
    next_val bigint
);

INSERT INTO hibernate_sequence values ('default', 1);
)