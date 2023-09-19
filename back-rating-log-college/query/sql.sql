create table teachers(
    id serial primary key,
    first_name varchar(255) not null,
    second_name varchar(255) not null,
    middle_name varchar(255),
    login varchar(255) not null,
    password varchar(255) not null,
    born_date date not null
);
create table objects (
  id serial primary key,
  name varchar(255) not null
);

insert into objects (name) value ('АиП'),('ООП'),('Веб программирование'),('Стандартизации'),('Основы философии'),('Методы защиты информации'),('Физкультура')
,('Моделирование');

create table week(
    id serial primary key ,
    name varchar(255) not null
);

insert into week(name)value ('Понедельник'),('Вторник'),('Среда'),('Четверг'),('Пятница'),('Суббота');

create table groups(
  id serial primary key,
  name varchar(255) not null
);

insert into groups (name) value ('П-20-51б');

create table group_chart(
  id serial primary key,
  id_group int references groups(id),
  curse int not null,
  name varchar(255) not null,
  date_start date not null,
  date_end date not null
);
insert into group_chart (id_group, curse, name, date_start, date_end) values (1,3,'Учеба','1.09.2023','25.10.2023');


create table schedule(
  id serial primary key,
  id_object int references objects(id),
  id_group_chart int references group_chart(id),
  id_week int references  week(id),
  couple int not null
);

insert into schedule (id_object, id_group_chart, id_week, couple) values (1,1,1,2),(2,1,1,3),(1,1,1,3);

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

insert into students (id_group, first_name, second_name, middle_name, login, password, born_date) values (1,'Максим','Кораблев','Игорович',
                                                                                                          'kramzos222','maxim22','2.04.2004');
create table evaluations(
  id serial primary key,
  id_student int references students(id),
  id_object int references objects(id),
  id_week int references week(id),
  grade int not null,
  date_grade date not null
);