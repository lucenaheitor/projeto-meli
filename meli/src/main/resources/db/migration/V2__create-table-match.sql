CREATE TABLE matches (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  home_team_id BIGINT NOT NULL,
  away_team_id BIGINT NOT NULL,
  home_team_score INT,
  away_team_score INT,
  stadium VARCHAR(255) NOT NULL,
  matchDate TIMESTAMP NOT NULL,
  CONSTRAINT fk_home_team FOREIGN KEY (home_team_id) REFERENCES club (id),
  CONSTRAINT fk_away_team FOREIGN KEY (away_team_id) REFERENCES club (id)
);
