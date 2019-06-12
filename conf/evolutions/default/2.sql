# --- Sample dataset

# --- !Ups

insert into OFFICE (id, name, zipcode) values (  1, 'Apple Inc.', 94028);
insert into OFFICE (id, name, zipcode) values (  2, 'Google.',    94017);
insert into OFFICE (id, name, zipcode) values (  3, 'Amazon.',    94026);
insert into OFFICE (id, name, zipcode) values (  4, 'Netflix',    98026);
insert into OFFICE (id, name, zipcode) values (  5, 'Oracle',     92013);
insert into OFFICE (id, name, zipcode) values (  6, 'Microsoft',  98056);


insert into SHIPMENT (id, office_id, type, status, weight) values (  1, 1, 0, 0, 0);
insert into SHIPMENT (id, office_id, type, status, weight) values (  2, 3, 1, 1, 1);
insert into SHIPMENT (id, office_id, type, status, weight) values (  3, 2, 0, 2, 2);
insert into SHIPMENT (id, office_id, type, status, weight) values (  4, 2, 1, 0, 0);