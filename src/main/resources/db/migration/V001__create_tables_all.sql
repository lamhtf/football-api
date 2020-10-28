CREATE TABLE AREA (
	id int8,
	name VARCHAR (255),
	countryCode VARCHAR (10),
	ensignUrl VARCHAR (255),
	parentAreaId int8, --dont link the relationship
	parentArea VARCHAR(20),
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id)
);

--only apply for 1 seaon for each competition
CREATE TABLE SEASON (
	id int8,
	startDate VARCHAR (10),
	endDate VARCHAR (10),
	currentMatchday int,
	winner VARCHAR(255),
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE TEAM (
	id int8,
--area
	areaId int8,
	name VARCHAR (255),
	shortName VARCHAR (255),
	tla VARCHAR (10),
	crestUrl VARCHAR (255),
	address VARCHAR (255),
	phone VARCHAR (255),
	website VARCHAR (255),
	email VARCHAR (255),
	founded int,
	clubColors VARCHAR (255),
	venue VARCHAR (255),
	lastUpdated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (areaId)
      REFERENCES AREA (id)
);

--only apply for 5-8 competitions
CREATE TABLE COMPETITION (
	id int8,
--area
	areaId int8,
	name VARCHAR (255),
	code VARCHAR (10),
	emblemUrl VARCHAR (255),
	plan VARCHAR (20),
--season
	currentSeasonId int8,
	numberOfAvailableSeasons int,
	lastUpdated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (areaId)
      REFERENCES AREA (id),
	FOREIGN KEY (currentSeasonId)
      REFERENCES SEASON (id)
);

CREATE TABLE STANDINGS (
	id int8,
	competitionId int8,
	stage VARCHAR (20),
	type VARCHAR (10),
	"group" VARCHAR (20),
	PRIMARY KEY (id),
	FOREIGN KEY (competitionId)
	  REFERENCES COMPETITION (id)
);

CREATE TABLE STANDING (
	id int8,
	standingsId int8,
	position int,
	teamId int8, --dont link the relationship
	teamName VARCHAR (255),
	teamCrestUrl VARCHAR (255),
	playedGames int,
	form VARCHAR (255),
	won int,
	draw int,
	lost int,
	points int,
	goalsFor int,
	goalsAgainst int,
	goalDifference int,
	FOREIGN KEY (standingsId)
	  REFERENCES STANDINGS (id)
);


CREATE TABLE COMPETITION_TEAM (
	id int8,
	competitionId int8,
	teamId int8,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (competitionId)
	  REFERENCES COMPETITION (id),
	FOREIGN KEY (teamId)
	  REFERENCES TEAM (id)
);

CREATE TABLE REFEREE (
	id int8,
	name VARCHAR (255),
	nationality VARCHAR (255),
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE MATCH (
	id int8,
	utcDate TIMESTAMP,
	status VARCHAR (20),
	venue VARCHAR (255),
	matchday int,
	stage VARCHAR (20),
	"group" VARCHAR (20),
	lastUpdated TIMESTAMP,
--head2head
	numberOfMatches VARCHAR (255),
	totalGoals int,
	homeTeamId int8,
	homeTeamName VARCHAR (255),
	homeTeamWins int,
	homeTeamDraws int,
	homeTeamLosses int,
	awayTeamId int8,
	awayTeamName VARCHAR (255),
	awayTeamWins int,
	awayTeamDraws int,
	awayTeamLosses int,
--score
	winner VARCHAR (20),
	duration VARCHAR (20),
	fullTimeHomeTeam int,
	fullTimeAwayTeam int,
	halfTimeHomeTeam int,
	halfTimeAwayTeam int,
	extraTimeHomeTeam int,
	extraTimeAwayTeam int,
	penaltiesHomeTeam int,
	penaltiesAwayTeam int,
--referees (main)
	refereeId int8,
	refereeName VARCHAR(255),
--competition
	competitionId int8,
--season
	seasonId int8,
--default
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE PLAYER (
	id int8,
	name VARCHAR (255),
	firstName VARCHAR (255),
	lastName VARCHAR (255),
	dateOfBirth VARCHAR (10),
	countryOfBirth VARCHAR (255),
	nationality VARCHAR (255),
	position VARCHAR (20),
	shirtNumber int,
	teamId int8,
	lastUpdated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (teamId)
	  REFERENCES TEAM (id)
);

CREATE TABLE COACH (
	id int8,
	name VARCHAR (255),
	firstName VARCHAR (255),
	lastName VARCHAR (255),
	dateOfBirth VARCHAR (10),
	countryOfBirth VARCHAR (255),
	nationality VARCHAR (255),
	position VARCHAR (20),
	shirtNumber int,
	teamId int8,
	lastUpdated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (teamId)
	  REFERENCES TEAM (id)
);