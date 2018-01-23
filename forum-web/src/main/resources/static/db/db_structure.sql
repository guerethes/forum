-- Table: forum.role
-- DROP TABLE forum.role;
CREATE TABLE forum.role (
  role_id bigint NOT NULL,
  role character varying(255),
  CONSTRAINT role_pkey PRIMARY KEY (role_id)
)
WITH (  OIDS=FALSE );
ALTER TABLE forum.role OWNER TO comum_user;

-- Table: forum.user
-- DROP TABLE forum.user;
CREATE TABLE forum.user (
  user_id bigint NOT NULL,
  active integer,
  email character varying(255) NOT NULL,
  last_name character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  CONSTRAINT usuario_pkey PRIMARY KEY (user_id)
)
WITH ( OIDS=FALSE );
ALTER TABLE forum.user OWNER TO comum_user;

------------------------------------------------

-- Sequence: forum.role_seq
-- DROP SEQUENCE forum.role_seq;
CREATE SEQUENCE forum.role_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE forum.role_seq OWNER TO comum_user;

-- Sequence: forum.user_seq
-- DROP SEQUENCE forum.user_seq;
CREATE SEQUENCE forum.user_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE forum.user_seq OWNER TO comum_user;
