create table mealdate.userInfo(
	u_id serial primary key,
	username varchar(30) unique,
	passwd varchar(20) not null,
	email varchar(30) unique
);

create table mealdate.userProfile(
	u_id serial primary key,
	username varchar(30) unique,
	gender integer,
	major varchar(30),
	u_age integer,
	u_year varchar(30),
	availability char,
	email varchar(30) unique,
	foreign key (u_id) references mealdate.userInfo(u_id),
	foreign key (email) references mealdate.userInfo(email),
	foreign key (username) references mealdate.userInfo(username)
);
