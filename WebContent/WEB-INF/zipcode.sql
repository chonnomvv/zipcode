drop table zipcode_tbl;
create table zipcode2_tbl(
	zipcode varchar2(100) not null,
	sido varchar2(100) not null,
	gugun varchar2(100) not null,
	dong varchar2(100) not null,
	ri varchar2(100),
	bldg varchar2(100),
	bunji varchar2(100)
	seq varchar2(5) primary key,
);

create sequence zipcode_seq start with 1;

select count(*) from ZIPCODE_TBL;
delete from zipcode_tbl;

select * from ZIPCODE_TBL where dong like '%����%';