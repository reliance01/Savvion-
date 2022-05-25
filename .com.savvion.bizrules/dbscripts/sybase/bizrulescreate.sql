
CREATE TABLE BR_PROJECT (
    PROJECT_ID          NUMERIC IDENTITY NOT NULL PRIMARY KEY,
    PROJECT_NAME        VARCHAR(64) NOT NULL,
    PROJECT_VERSION     NUMERIC(3) NOT NULL,
    DESCRIPTION         VARCHAR(255) NULL,
    PROJECT_STATUS      VARCHAR(12) NOT NULL,
    PROJECT_CATEGORY    VARCHAR(64) NULL,    
    CREATED_BY          VARCHAR(128) NULL,
    CREATED_DATE        DATE NULL,
    MODIFIED_BY         VARCHAR(128) NULL,
    MODIFIED_DATE       DATETIME NULL
)LOCK DATAROWS;

CREATE UNIQUE INDEX IX_BR_PROJECT_1 ON BR_PROJECT (
    PROJECT_NAME, 
    PROJECT_VERSION
);


CREATE TABLE BR_RULESET (
    RULESET_ID          NUMERIC IDENTITY NOT NULL PRIMARY KEY,
    RULESET_NAME        VARCHAR(64) NOT NULL,
    DESCRIPTION         VARCHAR(255) NULL,
    RULE_TYPE           VARCHAR(4) NULL,
    RULE_CONTENT        IMAGE NULL,
    PROJECT_ID          INTEGER NOT NULL,
    RULESET_STATUS      VARCHAR(12) NOT NULL,
    RULESET_CATEGORY    VARCHAR(64) NULL,
    PROPERTIES          IMAGE NULL,
    CREATED_BY          VARCHAR(128) NULL,
    CREATED_DATE        DATETIME NULL,
    MODIFIED_BY         VARCHAR(128) NULL,
    MODIFIED_DATE       DATETIME NULL
)LOCK DATAROWS;

CREATE UNIQUE INDEX IX_BR_RULESET_1 ON BR_RULESET(
    PROJECT_ID, 
    RULESET_NAME
);

CREATE INDEX IX_BR_RULESET_PROJECT_ID ON BR_RULESET(
    PROJECT_ID
);



CREATE TABLE BR_EXEC_SET (
    EXEC_SET_ID         NUMERIC IDENTITY NOT NULL PRIMARY KEY,
    URI                 VARCHAR(255) NOT NULL,
    DESCRIPTION         VARCHAR(255) NULL,
    CREATED_BY          VARCHAR(128) NULL,
    CREATED_DATE        DATETIME NULL,
    MODIFIED_BY         VARCHAR(128) NULL,
    MODIFIED_DATE       DATETIME NULL
)LOCK DATAROWS;

CREATE UNIQUE INDEX IX_BR_EXEC_SET_1 ON BR_EXEC_SET (
    URI
);


CREATE TABLE BR_EXEC_SET_RULESET_REL (
    EXEC_SET_ID         INT NOT NULL,
    RULESET_ID        	INT NOT NULL,
	CONSTRAINT BR_ES_RS_UNIQUE UNIQUE (EXEC_SET_ID, RULESET_ID)
)LOCK DATAROWS;

CREATE INDEX IX_ES_RS_REL_ESID
 ON BR_EXEC_SET_RULESET_REL (EXEC_SET_ID);

CREATE INDEX IX_ES_RS_REL_RSID
 ON BR_EXEC_SET_RULESET_REL (RULESET_ID);




