CREATE OR REPLACE FUNCTION create_avail() RETURNS TRIGGER AS $create_avail_trigger$
	declare id integer;
	declare tablename varchar;
    BEGIN  
	   select count(*) into id from userInfo;
	   tablename := 'mealdate.avail_'||id;
	   execute 'create table '|| tablename || ' (start_time varchar(30) not null, end_time varchar(30) );';
	   return null;
    END;  
$create_avail_trigger$ LANGUAGE plpgsql;

CREATE TRIGGER create_avail_trigger AFTER INSERT ON userinfo  
FOR EACH ROW EXECUTE PROCEDURE create_avail();
