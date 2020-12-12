--only apply for 1 season for each competition
CREATE TABLE SCORER (
	id int8, --playerId
	competition_id int8,
	season_id int8,	
	player_id int8,
	team_id int8,
	number_of_goals int,
	FOREIGN KEY (competition_id)
	  REFERENCES COMPETITION (id),
	FOREIGN KEY (season_id)
	  REFERENCES SEASON (id),
	FOREIGN KEY (player_id, team_id)
	  REFERENCES PLAYER (id, team_id),
	PRIMARY KEY(id, competition_id)
);
