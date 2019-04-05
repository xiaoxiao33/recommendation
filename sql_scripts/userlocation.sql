create table mealdate.userlocation(
	u_id integer primary key,
	latitude double precision,
	longitude double precision,
	foreign key (u_id) references mealdate.userInfo(u_id)
);