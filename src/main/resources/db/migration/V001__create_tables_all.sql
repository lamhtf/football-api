CREATE TABLE AREA (
	id int8,
	name VARCHAR (255),
	country_code VARCHAR (10),
	ensign_url VARCHAR (255),
	parent_area_id int8, --dont link the relationship
	parent_area VARCHAR(20),
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id)
);

--only apply for 1 season for each competition
CREATE TABLE SEASON (
	id int8,
	start_date VARCHAR (10),
	end_date VARCHAR (10),
	current_matchday int,
	winner_name VARCHAR(255),
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE TEAM (
	id int8,
--area
	area_id int8,
	name VARCHAR (255),
	short_name VARCHAR (255),
	tla VARCHAR (10),
	crest_url VARCHAR (255),
	address VARCHAR (255),
	phone VARCHAR (255),
	website VARCHAR (255),
	email VARCHAR (255),
	founded int,
	club_colors VARCHAR (255),
	venue VARCHAR (255),
	last_updated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (area_id)
      REFERENCES AREA (id)
);

--only apply for 5-8 competitions
CREATE TABLE COMPETITION (
	id int8,
--area
	area_id int8,
	name VARCHAR (255),
	code VARCHAR (10),
	emblem_url VARCHAR (255),
	plan VARCHAR (20),
--season
	current_season_id int8,
	number_of_available_seasons int,
	last_updated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (area_id)
      REFERENCES AREA (id),
	FOREIGN KEY (current_season_id)
      REFERENCES SEASON (id)
);

CREATE TABLE STANDINGS (
	id int8,
	competition_id int8,
	stage VARCHAR (50),
	type VARCHAR (10),
	"group" VARCHAR (50),
	PRIMARY KEY (id),
	FOREIGN KEY (competition_id)
	  REFERENCES COMPETITION (id)
);

CREATE TABLE STANDING (
	id int8,
	standings_id int8,
	position int,
	team_id int8, --dont link the relationship
	team_name VARCHAR (255),
	team_crest_url VARCHAR (255),
	played_games int,
	form VARCHAR (255),
	won int,
	draw int,
	lost int,
	points int,
	goals_for int,
	goals_against int,
	goal_difference int,
	FOREIGN KEY (standings_id)
	  REFERENCES STANDINGS (id)
);


CREATE TABLE COMPETITION_TEAM (
	id SERIAL,
	competition_id int8,
	team_id int8,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (competition_id)
	  REFERENCES COMPETITION (id),
	FOREIGN KEY (team_id)
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
	utc_date TIMESTAMP,
	status VARCHAR (20),
	venue VARCHAR (255),
	matchday int,
	stage VARCHAR (50),
	"group" VARCHAR (50),
	last_updated TIMESTAMP,
--head2head
	number_of_matches VARCHAR (255),
	total_goals int,
	home_team_id int8,
	home_team_name VARCHAR (255),
	home_team_wins int,
	home_team_draws int,
	home_team_losses int,
	away_team_id int8,
	away_team_name VARCHAR (255),
	away_team_wins int,
	away_team_draws int,
	away_team_losses int,
--score
	winner VARCHAR (20),
	duration VARCHAR (20),
	full_time_home_team int,
	full_time_away_team int,
	half_time_home_team int,
	half_time_away_team int,
	extra_time_home_team int,
	extra_time_away_team int,
	penalties_home_team int,
	penalties_away_team int,
--referees (main)
	referee_id int8,
	referee_name VARCHAR(255),
--competition
	competition_id int8,
--season
	season_id int8,
--default
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (competition_id)
	  REFERENCES COMPETITION (id),
	FOREIGN KEY (season_id)
	  REFERENCES SEASON (id)
);

CREATE TABLE PLAYER (
	id int8,
	name VARCHAR (255),
	first_name VARCHAR (255),
	last_name VARCHAR (255),
	date_of_birth VARCHAR (10),
	country_of_birth VARCHAR (255),
	nationality VARCHAR (255),
	position VARCHAR (20),
	shirt_number int,
	team_id int8,
	last_updated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (team_id)
	  REFERENCES TEAM (id)
);

CREATE TABLE COACH (
	id int8,
	name VARCHAR (255),
	first_name VARCHAR (255),
	last_name VARCHAR (255),
	date_of_birth VARCHAR (10),
	country_of_birth VARCHAR (255),
	nationality VARCHAR (255),
	position VARCHAR (20),
	shirt_number int,
	team_id int8,
	last_updated TIMESTAMP,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (team_id)
	  REFERENCES TEAM (id)
);