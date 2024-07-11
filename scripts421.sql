alter table student
add constraint student_age_greater_than_sixteen check (age >= 16);

alter table student
add constraint student_name_unique unique (name);

alter table student
alter column name set not null;

alter table faculty
add constraint faculty_name_color_unique unique (name, color);

alter table student
alter column age set default 20;
