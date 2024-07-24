CREATE SCHEMA IF NOT EXISTS students;

CREATE TABLE IF NOT EXISTS students.students
(
    id bigint NOT NULL DEFAULT nextval('students.students_id_seq'::regclass),
    firstname character varying COLLATE pg_catalog."default" NOT NULL,
    lastname character varying COLLATE pg_catalog."default" NOT NULL,
    patronymic character varying COLLATE pg_catalog."default",
    birthday date NOT NULL,
    group_id bigint NOT NULL,
    student_id bigint NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (id),
    CONSTRAINT unique_student_id UNIQUE (student_id)
);

TABLESPACE pg_default;
