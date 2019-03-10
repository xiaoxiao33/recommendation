create table mealdate.unPulledRequest(
	sender integer not null,
	receiver integer not null,
	start_time varchar(30) not null,
	end_time varchar(30) not null,
	foreign key (sender) references mealdate.userInfo(u_id),
	foreign key (receiver) references mealdate.userInfo(u_id),
	primary key(sender, receiver, start_time, end_time)
);

create table mealdate.InvitationTable(
	id serial,
	sender integer not null,
	receiver integer not null,
	start_time varchar(15),
	end_time varchar(15),
	latitude double precision,
);
create table mealdate.PulledRequest(
	sender integer not null,
	receiver integer not null,
	start_time varchar(30) not null,
	end_time varchar(30) not null,
	state char not null,
	foreign key (sender) references mealdate.userInfo(u_id),
	foreign key (receiver) references mealdate.userInfo(u_id),
	primary key (sender, receiver, start_time, end_time)
);