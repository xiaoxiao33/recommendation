create table mealdate.userInfo(
	u_id serial primary key,
	username varchar(30) not null,
	passwd varchar(20) not null
);

create table mealdate.userProfile(
	u_id serial primary key,
	username varchar(30),
	gender integer,
	major varchar(30),
	u_age integer,
	u_year varchar(30),
	foreign key (u_id) references mealdate.userInfo(u_id)
);
