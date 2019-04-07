create table mealdate.userInfo(
	u_id serial primary key,
	passwd varchar(100) not null,
	email varchar(30) unique
);

create table mealdate.userProfile(
	u_id serial primary key,
	username varchar(30),
	gender varchar(20),
	major varchar(30),
	u_age integer,
	u_year varchar(30),
	college varchar(20),
	description varchar(80),
	email varchar(30) unique,
	shared_gps varchar(10),
	availability char,
	foreign key (u_id) references mealdate.userInfo(u_id)
);
