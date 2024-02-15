alter table auto_post
add column car_id int references cars(id) not null;