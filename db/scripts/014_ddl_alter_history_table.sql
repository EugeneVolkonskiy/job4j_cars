alter table history
add column car_id int references cars(id);