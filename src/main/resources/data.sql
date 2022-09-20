insert into species (name, type, mature_age, senile_age, infant_survival, senile_survival, offspring, gestation, reproduction_period) values ('wolf', 'c', 1, 12, 0.3, 0.3, 4, 1, 2);
insert into species (name, type, mature_age, senile_age, infant_survival, senile_survival, offspring, gestation, reproduction_period) values ('rabbit', 'h', 0, 1, 0.2, 0.2, 6, 1, 1);

insert into interaction (species1, species2, interaction_type, annual_amount) values ('wolf', 'rabbit', 'c', 356);
