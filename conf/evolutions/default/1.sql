# --- First database schema

# --- !Ups
create table OFFICE (
  id                        INTEGER not null AUTO_INCREMENT,
  name                      varchar(255),
  zipcode                   INTEGER,
  constraint pk_office primary key (id)
);

create table SHIPMENT (
  id                        INTEGER not null AUTO_INCREMENT,
  office_id                 INTEGER,
  type                      SMALLINT,
  status                    SMALLINT,
  weight                    SMALLINT,
  constraint pk_shipment primary key (id)
);

alter table SHIPMENT add constraint fk_relation_office_1 foreign key (office_id) references office (id) on delete restrict on update restrict;

# --- !Downs

drop table if exists OFFICE;

drop table if exists SHIPMENT;