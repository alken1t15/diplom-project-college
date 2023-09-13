create table objects (
  id serial primary key,
  name varchar(255) not null
);

create table week(
    id serial primary key ,
    name varchar(255) not null
);

create table groups(
  id serial primary key,
  name varchar(255) not null
);

create table group_chart(
  id serial primary key,
  id_group int references groups(id),
  curse int not null,
  name varchar(255) not null,
  date_start date not null,
  date_end date not null
);


create table schedule(
  id serial primary key,
  id_object int references objects(id),
  id_group_chart int references group_chart(id),
  id_week int references  week(id),
  couple int not null
);

create table students(
  id serial primary key,
  id_group int references groups (id),
  first_name varchar(255) not null,
  second_name varchar(255) not null,
  middle_name varchar(255),
  login varchar(255) not null,
  password varchar(255) not null,
  born_date date not null
);

create table evaluations(
  id serial primary key,
  id_student int references students(id),
  id_object int references objects(id),
  id_week int references week(id),
  grade int not null,
  date_grade date not null
);