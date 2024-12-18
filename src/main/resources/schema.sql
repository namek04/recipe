CREATE TABLE IF NOT EXISTS recipe(
  id serial,
  category varchar(2),
  title varchar(50) NOT NULL,
  content text,
  date date NOT NULL,
  update_datetime timestamp,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS category_code(
  id serial,
  group_cd varchar(2),
  cd varchar(2),
  name varchar(20),
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users(
  id serial,
  user_id varchar(10) NOT NULL,
  password varchar(60) NOT NULL,
  username varchar(50),
  PRIMARY KEY(id)
);
