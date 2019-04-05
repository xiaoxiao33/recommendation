create table mealdate.userlocation(
	u_id integer primary key,
	latitude double precision,
	longitude double precision,
	update_time varchar(20),
	foreign key (u_id) references mealdate.userInfo(u_id)
);