-- This is where the ddl will go

DROP TABLE TITLES IF EXISTS;
DROP TABLE PERSONS IF EXISTS;
DROP TABLE PERSONNEL_TYPE IF EXISTS;


DROP SEQUENCE hibernate_sequence IF EXISTS;

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1; 
CREATE TABLE TITLES (
    TITLE_ID       BIGINT GENERATED BY DEFAULT AS IDENTITY,
    TITLE          VARCHAR(32)   NOT NULL,
    PRIMARY KEY (TITLE_ID)
);


CREATE TABLE PERSONS (
    PERSON_ID       BIGINT GENERATED BY DEFAULT AS IDENTITY,
    TITLE_ID        BIGINT,
    FIRST_NAME      VARCHAR(512)  NOT NULL,
    MIDDLE_INITIAL  VARCHAR(512),
    LAST_NAME       VARCHAR(512)  NOT NULL,
    GENDER          VARCHAR(2)    NOT NULL,
    
    SSN             VARCHAR(32)   NOT NULL,
    HOME_PHONE      VARCHAR(64),
    MOBILE_PHONE    VARCHAR(64),
    EMAIL           VARCHAR(512),
    
    
    ADDRESS         VARCHAR(512),
    CITY            VARCHAR(512),
    COUNTY          VARCHAR(512),
    STATE           VARCHAR(512),
    POSTAL_CODE     VARCHAR(32),
    
    PRIMARY KEY (PERSON_ID)
);

ALTER TABLE PERSONS 
    ADD CONSTRAINT UK_PERSON_SSN UNIQUE (SSN);
ALTER TABLE PERSONS
    ADD CONSTRAINT FK_PERSONS_TITLE_ID 
    FOREIGN KEY (TITLE_ID)
    REFERENCES TITLES (TITLE_ID);

CREATE TABLE PERSONNEL_TYPE (
    PERSONNEL_TYPE_ID       BIGINT GENERATED BY DEFAULT AS IDENTITY,
    TYPE_NAME               VARCHAR(64)   NOT NULL,
    PRIMARY KEY (PERSONNEL_TYPE_ID)
);

CREATE TABLE PERSONNEL (
	PERSON_ID           BIGINT NOT NULL,
	PERSONNEL_TYPE_ID   BIGINT NOT NULL,
	ANNUAL_SALARY       INTEGER,
	EMPLOYMENT_NUMBER   VARCHAR(128) NOT NULL,
	PRIMARY KEY (PERSON_ID)
);

ALTER TABLE PERSONNEL
ADD CONSTRAINT UC_PERSONNEL_EMP_NO UNIQUE (EMPLOYMENT_NUMBER);

CREATE TABLE NURSES (
	PERSON_ID           BIGINT NOT NULL,
	SURGERY_TYPE_ID     BIGINT,
	PRIMARY KEY (PERSON_ID)
);

CREATE TABLE SURGERY_TYPES (
	SURGERY_TYPE_ID           BIGINT NOT NULL,
	TYPE_NAME                 VARCHAR(64)   NOT NULL,
	PRIMARY KEY (SURGERY_TYPE_ID)
);

CREATE TABLE SURGERY_SKILLS (
	SURGERY_SKILL_ID         BIGINT NOT NULL,
	SKILL_NAME               VARCHAR(64)   NOT NULL,
	PRIMARY KEY (SURGERY_SKILL_ID)
);

CREATE TABLE NURSES_TO_SKILLS (
	PERSON_ID                BIGINT NOT NULL,
	SURGERY_SKILL_ID         BIGINT NOT NULL,
	PRIMARY KEY (PERSON_ID, SURGERY_SKILL_ID)
);

ALTER TABLE NURSES
    ADD CONSTRAINT FK_PERSON_ID 
    FOREIGN KEY (PERSON_ID)
    REFERENCES PERSONS (PERSON_ID);

ALTER TABLE NURSES
    ADD CONSTRAINT FK_SURGERY_TYPE_ID 
    FOREIGN KEY (SURGERY_TYPE_ID)
    REFERENCES SURGERY_TYPES (SURGERY_TYPE_ID);
    
ALTER TABLE NURSES_TO_SKILLS
    ADD CONSTRAINT FK_NURSES_TO_SKILLS_PERSON_ID 
    FOREIGN KEY (PERSON_ID)
    REFERENCES NURSES (PERSON_ID);

ALTER TABLE NURSES_TO_SKILLS
    ADD CONSTRAINT FK_NURSES_TO_SKILLS_SKILL_ID 
    FOREIGN KEY (SURGERY_SKILL_ID)
    REFERENCES SURGERY_SKILLS (SURGERY_SKILL_ID);

CREATE TABLE SURGERY_TYPES_TO_SKILLS (
	SURGERY_TYPE_ID            BIGINT NOT NULL,
	SURGERY_SKILL_ID           BIGINT NOT NULL,
	PRIMARY KEY (SURGERY_TYPE_ID, SURGERY_SKILL_ID)
);

ALTER TABLE SURGERY_TYPES_TO_SKILLS
    ADD CONSTRAINT FK_SURGERY_TYPES_TO_SKILLS_SURGERY_TYPE_ID
    FOREIGN KEY (SURGERY_TYPE_ID)
    REFERENCES SURGERY_TYPES (SURGERY_TYPE_ID);

ALTER TABLE SURGERY_TYPES_TO_SKILLS
    ADD CONSTRAINT FK_SURGERY_TYPES_TO_SKILLS_SKILL_ID 
    FOREIGN KEY (SURGERY_SKILL_ID)
    REFERENCES SURGERY_SKILLS (SURGERY_SKILL_ID);

CREATE TABLE PHYSICIANS (
	PERSON_ID           BIGINT NOT NULL,
	PRIMARY KEY (PERSON_ID)
);

ALTER TABLE PHYSICIANS
    ADD CONSTRAINT FK_PHYSICIANS_PERSON_ID 
    FOREIGN KEY (PERSON_ID)
    REFERENCES PERSONS (PERSON_ID);
    
CREATE TABLE PATIENTS (
	PERSON_ID             BIGINT NOT NULL,
	PRIMARY_PHYSICIAN_ID  BIGINT,
	PRIMARY KEY (PERSON_ID)
);

ALTER TABLE PATIENTS
    ADD CONSTRAINT FK_PRIMARY_PHYSICIAN_ID
    FOREIGN KEY (PRIMARY_PHYSICIAN_ID)
    REFERENCES PHYSICIANS (PERSON_ID);


CREATE TABLE ILLNESSES (
	ILLNESS_ID           BIGINT        NOT NULL,
	ILLNESS_NAME         VARCHAR(256)  NOT NULL,
	PRIMARY KEY (ILLNESS_ID)
);

CREATE TABLE PATIENTS_TO_ILLNESSES (
	PERSON_ID            BIGINT NOT NULL,
	ILLNESS_ID           BIGINT NOT NULL,
	PRIMARY KEY (PERSON_ID, ILLNESS_ID)
);

ALTER TABLE PATIENTS_TO_ILLNESSES
    ADD CONSTRAINT FK_PATIENTS_TO_ILLNESSES_PERSON_ID
    FOREIGN KEY (PERSON_ID)
    REFERENCES PATIENTS (PERSON_ID);

ALTER TABLE PATIENTS_TO_ILLNESSES
    ADD CONSTRAINT FK_PATIENTS_TO_ILLNESSES_ILLNESS_ID 
    FOREIGN KEY (ILLNESS_ID)
    REFERENCES ILLNESSES (ILLNESS_ID);
 
CREATE TABLE PRESCRIPTIONS (
	PRESCRIPTION_ID    BIGINT    NOT NULL,
	PATIENT_ID         BIGINT,
	MEDICATION_ID      BIGINT,
	PHYSICIAN_ID       BIGINT,
	DOSAGE             VARCHAR(256),
	FREQUENCY          VARCHAR(256),
	PRIMARY KEY (PRESCRIPTION_ID)
);

CREATE TABLE MEDICATIONS (
	MEDICATION_ID      BIGINT    NOT NULL,
	MEDICATION_NAME    VARCHAR(256),
	PRIMARY KEY (MEDICATION_ID)
);

ALTER TABLE PRESCRIPTIONS
    ADD CONSTRAINT FK_PRESCRIPTIONS_PATIENT_ID 
    FOREIGN KEY (PATIENT_ID)
    REFERENCES PATIENTS (PERSON_ID);

ALTER TABLE PRESCRIPTIONS
    ADD CONSTRAINT FK_PRESCRIPTIONS_MEDICATION_ID 
    FOREIGN KEY (MEDICATION_ID)
    REFERENCES MEDICATIONS (MEDICATION_ID);
    
ALTER TABLE PRESCRIPTIONS
    ADD CONSTRAINT FK_PRESCRIPTIONS_PHYSICIAN_ID 
    FOREIGN KEY (PHYSICIAN_ID)
    REFERENCES PHYSICIANS (PERSON_ID);
    
CREATE TABLE INTERACTIONS (
	INTERACTION_ID      BIGINT        NOT NULL,
	SEVERITY            VARCHAR(1)    NOT NULL,
	PRIMARY KEY (INTERACTION_ID)
);

CREATE TABLE INTERACTIONS_TO_MEDICATIONS (
	INTERACTION_ID      BIGINT        NOT NULL,
	MEDICATION_ID       BIGINT        NOT NULL,
	PRIMARY KEY (INTERACTION_ID, MEDICATION_ID)
);

ALTER TABLE INTERACTIONS_TO_MEDICATIONS
    ADD CONSTRAINT FK_INTERACTIONS_TO_MEDICATIONS_INTERACTION_ID 
    FOREIGN KEY (INTERACTION_ID)
    REFERENCES INTERACTIONS (INTERACTION_ID);

ALTER TABLE INTERACTIONS_TO_MEDICATIONS
    ADD CONSTRAINT FK_INTERACTIONS_TO_MEDICATIONS_MEDICATION_ID 
    FOREIGN KEY (MEDICATION_ID)
    REFERENCES MEDICATIONS (MEDICATION_ID);
