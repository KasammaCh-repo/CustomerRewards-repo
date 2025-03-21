create table CUSTOMER (id VARCHAR, name varchar2(200));
create table Transactions (id Number,customerid varchar2(100), amount DOUBLE,sysdate DATE);

insert into CUSTOMER values('10001','Kasi');
insert into CUSTOMER values('10002','Kasamma');

insert into Transactions values (1,'10001',1000, '2025-02-21');
insert into Transactions values (2,'10001',500, '2025-03-21');
insert into Transactions values (3,'10001',700, '2025-04-21');
insert into Transactions values (4,'10001',200, '2025-04-15');
