create table mealdate.InvitationVO(
	id serial primary key,
	sender integer not null,
	receiver integer not null,
	start_time varchar(32),
	end_time varchar(32),
	latitude double precision,
	longitude double precision,
	status varchar(10),
	foreign key (sender) references mealdate.userInfo(u_id),
	foreign key (receiver) references mealdate.userInfo(u_id)
);

create table mealdate.IntendVO(
	id serial primary key,
	u_id integer,
	start_time varchar(32),
	end_time varchar(32),
	foreign key (u_id) references mealdate.userInfo(u_id)
);

create table mealdate.BusyVO(
	id serial primary key,
	u_id integer,
	start_time varchar(32),
	end_time varchar(32),
	foreign key (u_id) references mealdate.userInfo(u_id)
);