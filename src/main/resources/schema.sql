create table species
(
name char not null,
type char not null,
food char not null,
requirement decimal,
mature_age integer,
senile_age integer,
mature_risk decimal(3,2),
senile_risk decimal(3,2),
offspring integer,
gestation integer,
reproduction_period integer
);