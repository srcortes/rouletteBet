DROP TABLE IF EXISTS MANAGER.ROULETTE;
DROP TABLE IF EXISTS MANAGER.STATE;
DROP TABLE IF EXISTS MANAGER.BET_USER;
DROP TABLE IF EXISTS MANAGER.STATE_BET;
DROP TABLE IF EXISTS MANAGER.EMISSION_RESULT;


CREATE TABLE MANAGER.ROULETTE (
  ID_ROULETTE INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,  
  ID_STATE INT NOT NULL
);

CREATE TABLE MANAGER.STATE (
  ID_STATE INT AUTO_INCREMENT PRIMARY KEY NOT NULL,  
  DESCRIPTION VARCHAR(30) NOT NULL
);

CREATE TABLE MANAGER.BET_USER(
	ID_BET INT PRIMARY KEY NOT NULL,
	ID_ROULETTE INT NOT NULL,
	ID_STATEBET INT NOT NULL,
	ID_GAMBLES INT NOT NULL,
	BET VARCHAR(30) NOT NULL,
	AMOUNT DECIMAL(10,2) NOT NULL
);

CREATE TABLE MANAGER.STATE_BET(
	ID_STATEBET INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	DESCRIPTION VARCHAR(30) NOT NULL
);

CREATE TABLE MANAGER.EMISSION_RESULT(
	ID_RESULT INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	ID_BET INT NOT NULL,
	ID_GAMBLES INT NOT NULL,
	BET VARCHAR(30) NOT NULL,
	NUMBER_GENERATE INT NOT NULL,
	EARNED_VALUE DECIMAL(10,2) NOT NULL,
	DATE_EMISSION VARCHAR(50) NOT NULL
);

ALTER TABLE MANAGER.ROULETTE ADD CONSTRAINT FK_ID_STATE
FOREIGN KEY (ID_STATE) REFERENCES MANAGER.STATE(ID_STATE);

ALTER TABLE MANAGER.BET_USER ADD CONSTRAINT FK_ID_ROULETTE
FOREIGN KEY (ID_ROULETTE) REFERENCES MANAGER.ROULETTE(ID_ROULETTE);

ALTER TABLE MANAGER.BET_USER ADD CONSTRAINT FK_ID_STATE_BET
FOREIGN KEY (ID_STATEBET) REFERENCES MANAGER.STATE_BET(ID_STATEBET);

ALTER TABLE MANAGER.EMISSION_RESULT ADD CONSTRAINT FK_ID_BET
FOREIGN KEY (ID_BET) REFERENCES MANAGER.BET_USER(ID_BET);