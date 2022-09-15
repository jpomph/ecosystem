create table species
(
name char(16) not null,
type char(1) not null,
mature_age integer,
senile_age integer,
infant_survival decimal(3,2),
senile_survival decimal(3,2),
offspring integer,
gestation integer,
reproduction_period integer,
PRIMARY KEY (name)
);

