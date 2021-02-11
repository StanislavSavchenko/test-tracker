CREATE SEQUENCE subdivision_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE subdivision
(
    id BIGINT NOT NULL DEFAULT nextval('subdivision_id_seq'::regclass),
    title VARCHAR(50) NOT NULL,
    created_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);
ALTER TABLE ONLY subdivision ADD CONSTRAINT xuusubdivision PRIMARY KEY (id);
ALTER TABLE subdivision ADD CONSTRAINT cusubdivision_title UNIQUE (title);



CREATE SEQUENCE user_data_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE user_data
(
    id BIGINT NOT NULL DEFAULT nextval('user_data_id_seq'::regclass),
    name VARCHAR(50) NOT NULL,
    password VARCHAR(50),
    created_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);
ALTER TABLE ONLY user_data ADD CONSTRAINT xuuser_data PRIMARY KEY (id);



CREATE TABLE user_subdivision
(
    user_id BIGINT NOT NULL,
    subdivision_id BIGINT NOT NULL
);
ALTER TABLE user_subdivision ADD CONSTRAINT cuuser_subdivision_pair UNIQUE (user_id, subdivision_id);



CREATE SEQUENCE task_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE task
(
    id BIGINT NOT NULL DEFAULT nextval('task_id_seq'::regclass),
    name VARCHAR(50) NOT NULL,
    topic VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    author_id BIGINT NOT NULL,
    performer_id BIGINT NOT NULL,
    created_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);
ALTER TABLE ONLY task ADD CONSTRAINT xutask PRIMARY KEY (id);
ALTER TABLE ONLY task ADD CONSTRAINT fk1_task_author FOREIGN KEY (author_id) REFERENCES user_data(id) ON DELETE CASCADE;
ALTER TABLE ONLY task ADD CONSTRAINT fk2_task_performer FOREIGN KEY (performer_id) REFERENCES user_data(id) ON DELETE CASCADE;



CREATE SEQUENCE task_attachment_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE task_attachment
(
    id BIGINT NOT NULL DEFAULT nextval('task_attachment_id_seq'::regclass),
    name TEXT NOT NULL,
    path TEXT NOT NULL,
    task_id BIGINT NOT NULL,
    created_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);
ALTER TABLE ONLY task_attachment ADD CONSTRAINT xutask_attachment PRIMARY KEY (id);
ALTER TABLE ONLY task_attachment ADD CONSTRAINT fk1_task_attachment_task FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE;



CREATE SEQUENCE comment_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE comment
(
    id BIGINT NOT NULL DEFAULT nextval('comment_id_seq'::regclass),
    text VARCHAR(500) NOT NULL,
    task_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    created_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_dt TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);
CREATE INDEX on comment(task_id);
ALTER TABLE ONLY comment ADD CONSTRAINT xucomment PRIMARY KEY (id);
ALTER TABLE ONLY comment ADD CONSTRAINT fk1_comment_task FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE;
ALTER TABLE ONLY comment ADD CONSTRAINT fk2_comment_author FOREIGN KEY (author_id) REFERENCES user_data(id) ON DELETE CASCADE;



