-- This is where the ddl will go


--DROP SEQUENCE hibernate_sequence IF EXISTS;

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1; 
CREATE TABLE TITLES (
    TITLE_ID       INTEGER ,
    TITLE          VARCHAR(32)   NOT NULL,
    PRIMARY KEY (TITLE_ID)
);

CREATE TABLE ADDRESSES (
    ADDRESS_ID       INTEGER ,
    ADDRESS1         VARCHAR(512),
    ADDRESS2         VARCHAR(512),
    CITY            VARCHAR(512),
    COUNTY          VARCHAR(512),
    STATE           VARCHAR(512),
    POSTAL_CODE     VARCHAR(32),
    PRIMARY KEY (ADDRESS_ID)
);


CREATE TABLE PERSONS (
    PERSON_ID       INTEGER ,
    TITLE_ID        INTEGER,
    FIRST_NAME      VARCHAR(512)  NOT NULL,
    MIDDLE_INITIAL  VARCHAR(512),
    LAST_NAME       VARCHAR(512)  NOT NULL,
    GENDER          VARCHAR(8)    NOT NULL,
    SSN             VARCHAR(32)   NOT NULL,
    HOME_PHONE      VARCHAR(64),
    MOBILE_PHONE    VARCHAR(64),
    EMAIL           VARCHAR(512),    
    DATE_OF_BIRTH   TIMESTAMP,
    ADDRESS_ID      INTEGER,

    PRIMARY KEY (PERSON_ID)
);

ALTER TABLE PERSONS
    ADD CONSTRAINT FK_PERSONS_ADDRESS_ID 
    FOREIGN KEY (ADDRESS_ID)
    REFERENCES ADDRESSES (ADDRESS_ID);



ALTER TABLE PERSONS 
    ADD CONSTRAINT UK_PERSON_SSN UNIQUE (SSN);
ALTER TABLE PERSONS
    ADD CONSTRAINT FK_PERSONS_TITLE_ID 
    FOREIGN KEY (TITLE_ID)
    REFERENCES TITLES (TITLE_ID);

--- Security tables (see edu.njit.cs631.medical.entity.security)
CREATE TABLE USER (
	USER_ID             INTEGER NOT NULL,
	PERSON_ID           INTEGER NOT NULL,
	ENABLED             BOOLEAN NOT NULL,
	TOKEN_EXPIRED       BOOLEAN NOT NULL,
	EMAIL                VARCHAR(512) NOT NULL,
	PASSWORD_HASH        VARCHAR(512) NOT NULL,
	PRIMARY KEY (USER_ID)
);
ALTER TABLE USER
    ADD CONSTRAINT FK_USER_PERSON_ID
    FOREIGN KEY (PERSON_ID)
    REFERENCES PERSONS (PERSON_ID);

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
    REFERENCES USER (USER_ID);
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

CREATE TABLE PERSONNEL_TYPE (
    PERSONNEL_TYPE_ID       INTEGER ,
    TYPE_NAME               VARCHAR(64)   NOT NULL,
    PRIMARY KEY (PERSONNEL_TYPE_ID)
);

CREATE TABLE PERSONNEL (
	PERSONNEL_ID        INTEGER NOT NULL,
	PERSON_ID           INTEGER NOT NULL,
	PERSONNEL_TYPE_ID   INTEGER NOT NULL,
	ANNUAL_SALARY       INTEGER,
	EMPLOYMENT_NUMBER   VARCHAR(128) NOT NULL,
	PRIMARY KEY (PERSONNEL_ID)
);

ALTER TABLE PERSONNEL
ADD CONSTRAINT UK_PERSONNEL_EMP_NO UNIQUE (EMPLOYMENT_NUMBER);

ALTER TABLE PERSONNEL
    ADD CONSTRAINT FK_PERSONNEL_PERSONNEL_TYPE 
    FOREIGN KEY (PERSONNEL_TYPE_ID)
    REFERENCES PERSONNEL_TYPE (PERSONNEL_TYPE_ID);

ALTER TABLE PERSONNEL
    ADD CONSTRAINT FK_PERSONNEL_PERSON_ID 
    FOREIGN KEY (PERSON_ID)
    REFERENCES PERSONS (PERSON_ID);

CREATE TABLE NURSES (
	PERSONNEL_ID        INTEGER NOT NULL,
	SURGERY_TYPE_ID     INTEGER,
	GRADE               VARCHAR(64),
	YEARS_OF_EXPERIENCE INTEGER,
	PRIMARY KEY (PERSONNEL_ID)
);

CREATE TABLE SURGERY_TYPES (
	SURGERY_TYPE_ID           INTEGER NOT NULL,
	TYPE_NAME                 VARCHAR(64)   NOT NULL,
	SURGERY_CODE              VARCHAR(64)   NOT NULL,
	LOCATION_ID               INTEGER,
	SPECIAL_NEEDS             VARCHAR(1024),
	CATEGORY                  VARCHAR(64),
	PRIMARY KEY (SURGERY_TYPE_ID)
);

ALTER TABLE SURGERY_TYPES
ADD CONSTRAINT UK_SURGERY_TYPES_SURGERY_CODE UNIQUE (SURGERY_CODE);


CREATE TABLE SURGERY_SKILLS (
	SURGERY_SKILL_ID         INTEGER NOT NULL,
	SKILL_NAME               VARCHAR(64)   NOT NULL,
	SKILL_CODE               VARCHAR(64)   NOT NULL,
	PRIMARY KEY (SURGERY_SKILL_ID)
);

ALTER TABLE SURGERY_SKILLS
ADD CONSTRAINT UK_SURGERY_SKILLS_SKILL_CODE UNIQUE (SKILL_CODE);

ALTER TABLE NURSES
    ADD CONSTRAINT FK_PERSONNEL_ID 
    FOREIGN KEY (PERSONNEL_ID)
    REFERENCES PERSONNEL (PERSONNEL_ID);

ALTER TABLE NURSES
    ADD CONSTRAINT FK_SURGERY_TYPE_ID 
    FOREIGN KEY (SURGERY_TYPE_ID)
    REFERENCES SURGERY_TYPES (SURGERY_TYPE_ID);

CREATE TABLE NURSES_TO_SKILLS (
	PERSONNEL_ID             INTEGER NOT NULL,
	SURGERY_SKILL_ID         INTEGER NOT NULL,
	PRIMARY KEY (PERSONNEL_ID, SURGERY_SKILL_ID)
);

ALTER TABLE NURSES_TO_SKILLS
    ADD CONSTRAINT FK_NRSS_TO_SKLLS_PRSNNL_ID
    FOREIGN KEY (PERSONNEL_ID)
    REFERENCES NURSES (PERSONNEL_ID);

ALTER TABLE NURSES_TO_SKILLS
    ADD CONSTRAINT FK_NURSES_TO_SKILLS_SKILL_ID 
    FOREIGN KEY (SURGERY_SKILL_ID)
    REFERENCES SURGERY_SKILLS (SURGERY_SKILL_ID);

CREATE TABLE SURGERY_TYPES_TO_SKILLS (
	SURGERY_TYPE_ID            INTEGER NOT NULL,
	SURGERY_SKILL_ID           INTEGER NOT NULL,
	PRIMARY KEY (SURGERY_TYPE_ID, SURGERY_SKILL_ID)
);

ALTER TABLE SURGERY_TYPES_TO_SKILLS
    ADD CONSTRAINT FK_SGRY_TYPS_SKLS_SGRY_TYP_ID
    FOREIGN KEY (SURGERY_TYPE_ID)
    REFERENCES SURGERY_TYPES (SURGERY_TYPE_ID);

ALTER TABLE SURGERY_TYPES_TO_SKILLS
    ADD CONSTRAINT  FK_SGERY_TYPS_T_SKLS_SKL_ID
    FOREIGN KEY (SURGERY_SKILL_ID)
    REFERENCES SURGERY_SKILLS (SURGERY_SKILL_ID);

CREATE TABLE PHYSICIANS (
	PERSONNEL_ID          INTEGER NOT NULL,
	SPECIALTY_ID          INTEGER,
	OWNERSHIP_PERCENTAGE  NUMERIC(5,2),
	PRIMARY KEY (PERSONNEL_ID)
);

ALTER TABLE PHYSICIANS
    ADD CONSTRAINT FK_PHYSICIANS_PERSONNEL_ID 
    FOREIGN KEY (PERSONNEL_ID)
    REFERENCES PERSONNEL (PERSONNEL_ID);
    
CREATE TABLE PATIENTS (
    PATIENT_ID            INTEGER   NOT NULL,
	PERSON_ID             INTEGER   NOT NULL,
	PRIMARY_PHYSICIAN_ID  INTEGER,
	PATIENT_NUMBER        VARCHAR(64)  NOT NULL,
	PRIMARY KEY (PATIENT_ID)
);

ALTER TABLE PATIENTS 
    ADD CONSTRAINT UK_PATIENTS_NUMBER UNIQUE (PATIENT_NUMBER);

ALTER TABLE PATIENTS
    ADD CONSTRAINT FK_PATIENTS_PERSON_ID
    FOREIGN KEY (PERSON_ID)
    REFERENCES PERSONS (PERSON_ID);

    
ALTER TABLE PATIENTS
    ADD CONSTRAINT FK_PRIMARY_PHYSICIAN_ID
    FOREIGN KEY (PRIMARY_PHYSICIAN_ID)
    REFERENCES PHYSICIANS (PERSONNEL_ID);


CREATE TABLE ILLNESSES (
	ILLNESS_ID           INTEGER        NOT NULL,
	ILLNESS_NAME         VARCHAR(256)  NOT NULL,
	PRIMARY KEY (ILLNESS_ID)
);

CREATE TABLE PATIENTS_TO_ILLNESSES (
	PATIENT_ID           INTEGER NOT NULL,
	ILLNESS_ID           INTEGER NOT NULL,
	PRIMARY KEY (PATIENT_ID, ILLNESS_ID)
);



ALTER TABLE PATIENTS_TO_ILLNESSES
    ADD CONSTRAINT FK_PTNTS_T_LNSS_PTNT_ID
    FOREIGN KEY (PATIENT_ID)
    REFERENCES PATIENTS (PATIENT_ID);

ALTER TABLE PATIENTS_TO_ILLNESSES
    ADD CONSTRAINT  FK_PTIENTS_T_ILNSSES_ILNSS_ID
    FOREIGN KEY (ILLNESS_ID)
    REFERENCES ILLNESSES (ILLNESS_ID);
 
CREATE TABLE PRESCRIPTIONS (
	PRESCRIPTION_ID    INTEGER    NOT NULL,
	PATIENT_ID         INTEGER,
	MEDICATION_ID      INTEGER,
	PHYSICIAN_ID       INTEGER,
	DOSAGE             VARCHAR(256),
	FREQUENCY          VARCHAR(256),
	PRIMARY KEY (PRESCRIPTION_ID)
);

CREATE TABLE MEDICATIONS (
	MEDICATION_ID        INTEGER    NOT NULL,
	MEDICATION_NAME      VARCHAR(256),
	MEDICATION_CODE      VARCHAR(64)   NOT NULL,
	PRIMARY KEY (MEDICATION_ID)
);

ALTER TABLE PRESCRIPTIONS
    ADD CONSTRAINT FK_PRESCRIPTIONS_PATIENT_ID 
    FOREIGN KEY (PATIENT_ID)
    REFERENCES PATIENTS (PATIENT_ID);

ALTER TABLE PRESCRIPTIONS
    ADD CONSTRAINT FK_PRESCRIPTIONS_MEDICATION_ID 
    FOREIGN KEY (MEDICATION_ID)
    REFERENCES MEDICATIONS (MEDICATION_ID);
    
ALTER TABLE PRESCRIPTIONS
    ADD CONSTRAINT FK_PRESCRIPTIONS_PHYSICIAN_ID 
    FOREIGN KEY (PHYSICIAN_ID)
    REFERENCES PHYSICIANS (PERSONNEL_ID);
    
CREATE TABLE INTERACTIONS (
	INTERACTION_ID      INTEGER        NOT NULL,
	SEVERITY            VARCHAR(1)    NOT NULL,
	PRIMARY KEY (INTERACTION_ID)
);

CREATE TABLE INTERACTIONS_TO_MEDICATIONS (
	INTERACTION_ID      INTEGER        NOT NULL,
	MEDICATION_ID       INTEGER        NOT NULL,
	PRIMARY KEY (INTERACTION_ID, MEDICATION_ID)
);

ALTER TABLE INTERACTIONS_TO_MEDICATIONS
    ADD CONSTRAINT  FK_NTRCTNS_T_MDCTNS_NTRCTN_ID
    FOREIGN KEY (INTERACTION_ID)
    REFERENCES INTERACTIONS (INTERACTION_ID);

ALTER TABLE INTERACTIONS_TO_MEDICATIONS
    ADD CONSTRAINT FK_NTRCTNS_T_MDCTNS_MDCTN_ID 
    FOREIGN KEY (MEDICATION_ID)
    REFERENCES MEDICATIONS (MEDICATION_ID);

CREATE TABLE SPECIALITIES (
	SPECIALTY_ID      INTEGER    NOT NULL,
	SPECIALTY_NAME    VARCHAR(256),
	PRIMARY KEY (SPECIALTY_ID)
);

CREATE TABLE SURGEONS (
	PERSONNEL_ID        INTEGER NOT NULL,
	SPECIALTY_ID        INTEGER,
	CONTRACT_TYPE       VARCHAR(64),
	CONTRACT_LENGTH     INTEGER,
	PRIMARY KEY (PERSONNEL_ID)
);

ALTER TABLE SURGEONS
    ADD CONSTRAINT FK_SURGEONS_PERSONNEL_ID 
    FOREIGN KEY (PERSONNEL_ID)
    REFERENCES PERSONNEL (PERSONNEL_ID);

ALTER TABLE SURGEONS
    ADD CONSTRAINT FK_SURGEONS_SPECIALTY_ID 
    FOREIGN KEY (SPECIALTY_ID)
    REFERENCES SPECIALITIES (SPECIALTY_ID);

ALTER TABLE PHYSICIANS
    ADD CONSTRAINT FK_PHYSICIANS_SPECIALTY_ID 
    FOREIGN KEY (SPECIALTY_ID)
    REFERENCES SPECIALITIES (SPECIALTY_ID);
  
CREATE TABLE ANATOMICAL_LOCATIONS (
	LOCATION_ID      INTEGER    NOT NULL,
	LOCATION_NAME    VARCHAR(256),
	PRIMARY KEY (LOCATION_ID)
);

ALTER TABLE SURGERY_TYPES
    ADD CONSTRAINT FK_SURGERY_TYPE_LOCATION_ID 
    FOREIGN KEY (LOCATION_ID)
    REFERENCES ANATOMICAL_LOCATIONS (LOCATION_ID);

CREATE TABLE MEDICAL_PROFILES (
	PATIENT_ID            INTEGER    NOT NULL,
	BLOOD_TYPE            VARCHAR(16),
	LDL_BAD_CHOLESTEROL   INTEGER,
	HDL_GOOD_CHOLESTEROL  INTEGER,
	TRIGLYCERIDES         INTEGER,
	BLOOD_SUGAR           INTEGER,
	PRIMARY KEY (PATIENT_ID)
);

ALTER TABLE MEDICAL_PROFILES
    ADD CONSTRAINT FK_MEDICAL_PROFILES_PATIENT_ID 
    FOREIGN KEY (PATIENT_ID)
    REFERENCES PATIENTS (PATIENT_ID);

CREATE TABLE ALLERGIES (
	ALLERGY_ID           INTEGER    NOT NULL,
	ALLERGY_NAME         VARCHAR(128)   NOT NULL,
	ALLERGY_CODE         VARCHAR(64)   NOT NULL,
	PRIMARY KEY (ALLERGY_ID)
);

ALTER TABLE ALLERGIES 
    ADD CONSTRAINT UK_ALLERGIES_ALLERGY_CODE UNIQUE (ALLERGY_CODE);
    
CREATE TABLE MEDICAL_PROFILES_TO_ALLERGIES (
	PATIENT_ID               INTEGER NOT NULL,
	ALLERGY_ID               INTEGER NOT NULL,
	PRIMARY KEY (PATIENT_ID, ALLERGY_ID)
);

ALTER TABLE MEDICAL_PROFILES_TO_ALLERGIES
    ADD CONSTRAINT FK_MDCL_PRFLS_T_LRGS_PTNT_ID
    FOREIGN KEY (PATIENT_ID)
    REFERENCES MEDICAL_PROFILES (PATIENT_ID);

ALTER TABLE MEDICAL_PROFILES_TO_ALLERGIES
    ADD CONSTRAINT  FK_MDCL_PRFLS_T_LRGS_LRGY_ID
    FOREIGN KEY (ALLERGY_ID)
    REFERENCES ALLERGIES (ALLERGY_ID);

CREATE TABLE MEDICATION_INVENTORIES (
	MEDICATION_ID             INTEGER    NOT NULL,
	QUANTITY_ON_HAND          INTEGER,
	QUANTIFY_ON_ORDER         INTEGER,
	UNIT_COST                 NUMERIC(10,2),
	USAGE_YEAR_TO_DATE        INTEGER,
	PRIMARY KEY (MEDICATION_ID)
);

ALTER TABLE MEDICATION_INVENTORIES
    ADD CONSTRAINT  FK_MDCTN_NVNTRS_MDCTN_ID
    FOREIGN KEY (MEDICATION_ID)
    REFERENCES MEDICATIONS (MEDICATION_ID);

CREATE TABLE SURGERIES (
	SURGERY_ID           INTEGER    NOT NULL,
	SURGEON_ID           INTEGER,
	PATIENT_ID           INTEGER,
	NURSE_ID             INTEGER,
	SURGERY_TYPE_ID      INTEGER,
	SCHEDULE             TIMESTAMP,
	PRIMARY KEY (SURGERY_ID)
);

ALTER TABLE SURGERIES
    ADD CONSTRAINT FK_SURGERIES_SURGEON_ID 
    FOREIGN KEY (SURGEON_ID)
    REFERENCES SURGEONS (PERSONNEL_ID);

ALTER TABLE SURGERIES
    ADD CONSTRAINT FK_SURGERIES_PATIENT_ID 
    FOREIGN KEY (PATIENT_ID)
    REFERENCES PATIENTS (PATIENT_ID);

ALTER TABLE SURGERIES
    ADD CONSTRAINT FK_SURGERIES_NURSE_ID 
    FOREIGN KEY (NURSE_ID)
    REFERENCES NURSES (PERSONNEL_ID);

ALTER TABLE SURGERIES
    ADD CONSTRAINT FK_SURGERIES_SURGERY_TYPE_ID 
    FOREIGN KEY (SURGERY_TYPE_ID)
    REFERENCES SURGERY_TYPES (SURGERY_TYPE_ID);
    

CREATE TABLE INPATIENTS (
	PATIENT_ID            INTEGER    NOT NULL,
	NURSE_ID              INTEGER,
	ADMISSION_DATE        TIMESTAMP,
	NURSING_UNIT          INTEGER,
	ROOM_NUMBER           VARCHAR(64),
	WING                  VARCHAR(64),
	BED_NUMBER            VARCHAR(8),
	PRIMARY KEY (PATIENT_ID)
);

ALTER TABLE INPATIENTS
    ADD CONSTRAINT FK_INPATIENTS_PATIENT_ID 
    FOREIGN KEY (PATIENT_ID)
    REFERENCES PATIENTS (PATIENT_ID);

ALTER TABLE INPATIENTS
    ADD CONSTRAINT FK_INPATIENTS_NURSE_ID 
    FOREIGN KEY (NURSE_ID)
    REFERENCES NURSES (PERSONNEL_ID);

    
CREATE TABLE CONSULTATIONS (
	CONSULTATION_ID       INTEGER    NOT NULL,
	PERSON_ID             INTEGER,
	PERSONNEL_ID          INTEGER,
	SCHEDULE              TIMESTAMP,
	PRIMARY KEY (CONSULTATION_ID)
);

ALTER TABLE CONSULTATIONS
    ADD CONSTRAINT FK_CONSULTATIONS_NURSE_ID 
    FOREIGN KEY (PERSON_ID)
    REFERENCES PERSONS (PERSON_ID);

ALTER TABLE CONSULTATIONS
    ADD CONSTRAINT FK_CONSULTATIONS_PERSONNEL_ID 
    FOREIGN KEY (PERSONNEL_ID)
    REFERENCES PHYSICIANS (PERSONNEL_ID);
    
CREATE TABLE CORPORATIONS (
	CORPORATION_ID        INTEGER    NOT NULL,
	CORPORATION_NAME      VARCHAR(128)  NOT NULL,
	HQ_NAME               VARCHAR(512),
	ADDRESS_ID            INTEGER,
	OWNERSHIP_PERCENTAGE  NUMERIC(5,2),
	PRIMARY KEY (CORPORATION_ID)
);

ALTER TABLE CORPORATIONS 
    ADD CONSTRAINT UK_CORPORATION_NAME UNIQUE (CORPORATION_NAME);
    
ALTER TABLE CORPORATIONS
    ADD CONSTRAINT FK_CORPORATIONS_ADDRESS_ID 
    FOREIGN KEY (ADDRESS_ID)
    REFERENCES ADDRESSES (ADDRESS_ID);

