alter table history
add column owner_id int references owners(id);