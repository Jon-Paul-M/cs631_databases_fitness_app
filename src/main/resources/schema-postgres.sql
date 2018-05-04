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
	PASSWORD_HASH       VARCHAR(512) NOT NULL,
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
    END_DATETIME   TIMESTAMP,
    -- PRIMARY KEY (CLASS_ID, EXERCISE_ID), See #34
    PRIMARY KEY (CLASS_ID),
    FOREIGN KEY (EXERCISE_ID) REFERENCES EXERCISE(EXERCISE_ID),
    FOREIGN KEY (ROOM_ID) REFERENCES ROOM (ROOM_ID),
    FOREIGN KEY (INSTRUCTOR_ID) REFERENCES PERSON (USER_ID)
);
ALTER TABLE CLASS
ADD CONSTRAINT CLASS_DURATION_CHECK
CHECK  (DURATION > 0 AND DURATION <= 480); -- DURATION is in minutes

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


CREATE OR REPLACE FUNCTION INSTRUCTOR_MINUTES_BY_TIME_INTERVAL (INTEGER, TIMESTAMP, TIMESTAMP)
RETURNS INTEGER AS $$
declare
	IN_INSTRUCTOR_ID ALIAS FOR $1;
    IN_INTERVAL_BEGIN ALIAS FOR $2;
    IN_INTERVAL_END ALIAS FOR $3;
	total INTEGER;
BEGIN
	SELECT sum(C.duration) into total FROM CLASS C
		WHERE C.INSTRUCTOR_ID = IN_INSTRUCTOR_ID
		AND C.START_DATETIME >= IN_INTERVAL_BEGIN
		AND C.START_DATETIME < IN_INTERVAL_END;
   RETURN total;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GENERATE_PAYROLL(interval_begin timestamp, 
                                            interval_end timestamp, 
                                            fed_rate decimal,
                                            state_rate decimal,
                                            other_rate decimal) 
	RETURNS refcursor AS $$
DECLARE
	ref refcursor;       -- Declare a cursor variable
BEGIN
	OPEN ref FOR 
		SELECT 
		    user_id AS id,
		    name,
		    wage,
		    ROUND(minutes / 60.0, 2) AS hours,
		    ROUND(wage * (minutes / 60.0), 2) AS gross,
		    ROUND(wage * (minutes / 60.0) * (fed_rate / 100.0), 2) AS fed_tax,
		    ROUND(wage * (minutes / 60.0) * (state_rate / 100.0), 2) AS state_tax,
		    ROUND(wage * (minutes / 60.0) * (other_rate / 100.0), 2) AS other_tax,
		    'Hourly' as instructor_type
		FROM
		    (SELECT 
		        pp.user_id,
		            pp.name,
		            hh.hourly_wage AS wage,
		            INSTRUCTOR_MINUTES_BY_TIME_INTERVAL(pp.user_id, interval_begin, interval_end) AS minutes
		    FROM
		        person pp, hourly_instructor hh
		    WHERE
		        pp.user_id = hh.user_id) foo
		UNION
		SELECT
			pp.user_id,
			pp.name,
			si.salary AS wage,
			NULL AS hours,
			--DATE_PART('day', interval_begin - interval_end) AS days,
			ROUND(CAST(salary * (DATE_PART('day', interval_end - interval_begin) / 365.0) AS NUMERIC), 2) AS gross,
			ROUND(CAST(salary * (DATE_PART('day', interval_end - interval_begin) / 365.0) AS NUMERIC) * (fed_rate / 100.0), 2) AS fed_tax,
			ROUND(CAST(salary * (DATE_PART('day', interval_end - interval_begin) / 365.0) AS NUMERIC) * (state_rate / 100.0), 2) AS state_tax,
			ROUND(CAST(salary * (DATE_PART('day', interval_end - interval_begin) / 365.0) AS NUMERIC) * (other_rate / 100.0), 2) AS other_tax,
			'Salaried' AS instructor_type 
		FROM
			person pp,
			salaried_instructor si 
		WHERE
			pp.user_id = si.user_id
		ORDER BY name;
	RETURN ref;             -- Return the cursor to the caller
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION SPOTS_REMAINING (INTEGER)
RETURNS INTEGER AS $$
declare
	IN_CLASS_ID ALIAS FOR $1;
	remaining INTEGER;
	capacity INTEGER;
	taken INTEGER;
BEGIN
	SELECT 
    	COUNT(r.user_id) into taken
	FROM
    	class c,
    	register r
	WHERE
    	c.class_id = r.class_id
        	AND c.class_id = IN_CLASS_ID;
    SELECT 
    	r.capacity INTO capacity
    FROM
    	class c,
    	room r
	WHERE
    	c.room_id = r.room_id
        	AND c.class_id = IN_CLASS_ID; 
	remaining :=  capacity - taken;  	
	RETURN remaining;
END;
$$ LANGUAGE plpgsql;

-- demo of how to dynamically uppdate a field during insert/update
-- a slight de-normalization allows the above OVERLAPS queries to potentially use an index 
CREATE OR REPLACE FUNCTION update_end_date() RETURNS trigger AS $$
    BEGIN
		NEW.END_DATETIME := NEW.START_DATETIME + NEW.DURATION * INTERVAL '1 minutes';
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION check_class_for_conflicts() RETURNS trigger AS $$
declare
	class_id INTEGER;
BEGIN
	-- Instructor check
	class_id := null;
    SELECT c.class_id into class_id FROM class c
    	WHERE ((c.START_DATETIME, c.END_DATETIME) OVERLAPS
   			(NEW.START_DATETIME, NEW.DURATION * INTERVAL '1 minutes'))
   		AND NEW.INSTRUCTOR_ID = c.INSTRUCTOR_ID
   		LIMIT 1;
    IF class_id is not null THEN
        RAISE EXCEPTION 'Instructor overlaps existing class';
    END IF;
    -- Room check, assumes only one class at a time in each room
    SELECT c.class_id into class_id FROM class c
    	WHERE ((c.START_DATETIME, c.END_DATETIME) OVERLAPS
   			(NEW.START_DATETIME, NEW.DURATION * INTERVAL '1 minutes'))
   		AND NEW.ROOM_ID = c.ROOM_ID
   		LIMIT 1;
	IF found THEN
        RAISE EXCEPTION 'Room overlaps existing class';
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- In postgres multilple triggers on the same table are executed in lexigraphical order
-- thus you can enforce a specific order by embedding a sequence in the trigger name
CREATE TRIGGER class_020_check_conflicts BEFORE INSERT OR UPDATE ON class 
	FOR EACH ROW EXECUTE PROCEDURE check_class_for_conflicts();

CREATE TRIGGER trigger_class_010_update_end_date BEFORE INSERT OR UPDATE ON class
	FOR EACH ROW EXECUTE PROCEDURE update_end_date();

-- This is a dummy table
-- delete before submission and 
-- turn off hibernate schema validation
CREATE TABLE INSTRUCTOR_PAYROLL (
	ID               INTEGER,
	NAME             VARCHAR(512),
	WAGE             DECIMAL,
	HOURS            DECIMAL,
	GROSS            DECIMAL,
	FED_TAX          DECIMAL,
	STATE_TAX        DECIMAL,
	OTHER_TAX        DECIMAL,
	INSTRUCTOR_TYPE  VARCHAR(512)
);


-- these carrots are necessary for spring boot data loading
^^^
