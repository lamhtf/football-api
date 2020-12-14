--only apply for 1 season for each competition
CREATE TABLE SCORER (
	id int8, --equal to playerId
	competition_id int8,
	season_id int8,	
	player_id int8,
	team_id int8,
	number_of_goals int,
	FOREIGN KEY (competition_id)
	  REFERENCES COMPETITION (id),
	FOREIGN KEY (season_id)
	  REFERENCES SEASON (id),
	FOREIGN KEY (team_id)
	  REFERENCES TEAM (id),
	PRIMARY KEY(id, competition_id, season_id, team_id)
);
