create table mealdate.invitationvo(
	id serial primary key,
	sender integer not null,
	receiver integer not null,
	start_time varchar(15),
	end_time varchar(15),
	latitude double precision,
	longitude double precision,
	status varchar(10),
	foreign key (sender) references mealdate.userInfo(u_id),
	foreign key (receiver) references mealdate.userInfo(u_id)
);

create table mealdate.IntendedTable(
	u_id integer primary key,
	start_time varchar(15),
	end_time varchar(15),
	foreign key (u_id) references mealdate.userInfo(u_id)
);

create table mealdate.BusyTable(
	u_id integer primary key,
	start_time varchar(15),
	end_time varchar(15),
	foreign key (u_id) references mealdate.userInfo(u_id)
);