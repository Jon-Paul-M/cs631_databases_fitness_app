-- This is where the ddl will go


DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE SEQUENCE hibernate_sequence START WITH 500 INCREMENT BY 1;

-- USER is a reserved word in postgres
CREATE TABLE PERSON (
	USER_ID             INTEGER NOT NULL,
	EMAIL               VARCHAR(512) NOT NULL,
	NAME                VARCHAR(512) NOT NULL,
	ENABLED             BOOLEAN NOT NULL,
	TOKEN_EXPIRED       BOOLEAN NOT NULL,
	PASSWORD_HASH        VARCHAR(512) NOT NULL,
	PRIMARY KEY (USER_ID)
);

CREATE TABLE ROLE (
    ROLE_ID INTEGER NOT NULL,
    NAME VARCHAR(64) NOT NULL,
    PRIMARY KEY (ROLE_ID)
);

ALTER TABLE ROLE
    ADD CONSTRAINT UK_ROLE_NAME UNIQUE (NAME);

CREATE TABLE USERS_ROLES (
    USER_ID INTEGER NOT NULL,
    ROLE_ID INTEGER NOT NULL,
    PRIMARY KEY (USER_ID, ROLE_ID)
);
ALTER TABLE USERS_ROLES
    ADD CONSTRAINT FK_USERS_ROLES_USER_ID
    FOREIGN KEY (USER_ID)
    REFERENCES PERSON (USER_ID);
ALTER TABLE USERS_ROLES
    ADD CONSTRAINT FK_USERS_ROLES_ROLE_ID
    FOREIGN KEY (ROLE_ID)
    REFERENCES ROLE (ROLE_ID);

CREATE TABLE PRIVILEGE (
    PRIVILEGE_ID INTEGER NOT NULL,
    NAME VARCHAR(64) NOT NULL,
    PRIMARY KEY (PRIVILEGE_ID)
);
ALTER TABLE PRIVILEGE
    ADD CONSTRAINT UK_PRIVILEGE_NAME UNIQUE (NAME);

CREATE TABLE ROLES_PRIVILEGES (
    ROLE_ID INTEGER NOT NULL,
    PRIVILEGE_ID INTEGER NOT NULL,
    PRIMARY KEY (ROLE_ID, PRIVILEGE_ID)
);
ALTER TABLE ROLES_PRIVILEGES
    ADD CONSTRAINT FK_ROLES_PRIVILEGES_ROLE_ID
    FOREIGN KEY (ROLE_ID)
    REFERENCES ROLE (ROLE_ID);
ALTER TABLE ROLES_PRIVILEGES
    ADD CONSTRAINT FK_ROLES_PRIVILEGES_PRIVILEGE_ID
    FOREIGN KEY (PRIVILEGE_ID)
    REFERENCES PRIVILEGE (PRIVILEGE_ID);

CREATE TABLE MEMBERSHIP(
    MEMBERSHIP_ID INTEGER NOT NULL,
    MEMBERSHIP_TYPE VARCHAR(64) NOT NULL,
    FEE DECIMAL NOT NULL,
    ADVANTAGES VARCHAR(4096),
    PRIMARY KEY (MEMBERSHIP_ID)
);

CREATE TABLE MEMBER(
    USER_ID INTEGER NOT NULL,
    ADDRESS1         VARCHAR(512),
    ADDRESS2         VARCHAR(512),
    CITY            VARCHAR(512),
    COUNTY          VARCHAR(512),
    STATE           VARCHAR(512),
    POSTAL_CODE     VARCHAR(32),
    REGISTRATION_DATE TIMESTAMP,
    MEMBERSHIP_ID INTEGER NOT NULL,
    PRIMARY KEY (USER_ID),
    FOREIGN KEY (USER_ID) REFERENCES PERSON (USER_ID),
    FOREIGN KEY (MEMBERSHIP_ID) REFERENCES MEMBERSHIP(MEMBERSHIP_ID)
);

CREATE TABLE EXERCISE (
    EXERCISE_ID INTEGER NOT NULL,
    NAME VARCHAR(512),
    DESCRIPTION VARCHAR(4096),
    PRIMARY KEY (EXERCISE_ID)
);

CREATE TABLE ROOM (
    ROOM_ID INTEGER NOT NULL,
    CAPACITY INTEGER NOT NULL,
    ROOM_NUMBER VARCHAR(256),
    BUILDING_NAME VARCHAR(512),
    PRIMARY KEY (ROOM_ID)
);

CREATE TABLE HOURLY_INSTRUCTOR (
    USER_ID INTEGER NOT NULL,
    HOURS DECIMAL NOT NULL,
    HOURLY_WAGE DECIMAL NOT NULL,
    PRIMARY KEY (USER_ID),
    FOREIGN KEY (USER_ID) REFERENCES PERSON (USER_ID)
);

CREATE TABLE SALARIED_INSTRUCTOR (
    USER_ID INTEGER NOT NULL,
    SALARY DECIMAL,
    PRIMARY KEY (USER_ID),
    FOREIGN KEY (USER_ID) REFERENCES PERSON (USER_ID)
);

CREATE TABLE CLASS (
    CLASS_ID       INTEGER NOT NULL,
    EXERCISE_ID    INTEGER NOT NULL,
    ROOM_ID        INTEGER,
    INSTRUCTOR_ID  INTEGER,
    START_DATETIME TIMESTAMP NOT NULL,
    DURATION       INTEGER NOT NULL,
    -- PRIMARY KEY (CLASS_ID, EXERCISE_ID), See #34
    PRIMARY KEY (CLASS_ID),
    FOREIGN KEY (EXERCISE_ID) REFERENCES EXERCISE(EXERCISE_ID),
    FOREIGN KEY (ROOM_ID) REFERENCES ROOM (ROOM_ID),
    FOREIGN KEY (INSTRUCTOR_ID) REFERENCES PERSON (USER_ID)
);
ALTER TABLE CLASS
ADD CONSTRAINT CLASS_DURATION_CHECK
CHECK  (DURATION > 0 AND DURATION < 9);

CREATE TABLE REGISTER (
    USER_ID INTEGER NOT NULL,
    CLASS_ID INTEGER NOT NULL,
    -- EXERCISE_ID INTEGER NOT NULL, -- See issue #34
    PRIMARY KEY (USER_ID, CLASS_ID),  -- , EXERCISE_ID),
    FOREIGN KEY (USER_ID) REFERENCES PERSON(USER_ID),
    FOREIGN KEY (CLASS_ID) REFERENCES CLASS(CLASS_ID) --, See issue #34
    -- FOREIGN KEY (EXERCISE_ID) REFERENCES EXERCISE (EXERCISE_ID)
);

CREATE FUNCTION one() RETURNS integer AS $$
    SELECT 1 AS result;
$$ LANGUAGE SQL;


CREATE OR REPLACE FUNCTION INSTRUCTOR_HOURS_BY_TIME_INTERVAL (INTEGER, TIMESTAMP, TIMESTAMP)
RETURNS DECIMAL AS $$
declare
	IN_INSTRUCTOR_ID ALIAS FOR $1;
    IN_INTERVAL_BEGIN ALIAS FOR $2;
    IN_INTERVAL_END ALIAS FOR $2;
	total INTEGER;
BEGIN
	SELECT sum(C.duration) into total FROM CLASS C
		WHERE C.INSTRUCTOR_ID = IN_INSTRUCTOR_ID
		AND C.START_DATETIME >= IN_INTERVAL_BEGIN
		AND C.START_DATETIME < IN_INTERVAL_END;
   RETURN total;
END;
$$ LANGUAGE plpgsql;

^^^
