--only apply for 1 season for each competition
ALTER TABLE COACH DROP CONSTRAINT coach_pkey;
ALTER TABLE COACH ADD PRIMARY KEY(id, team_id);

