-- # create table teachers(
-- #     id serial primary key,
-- #     first_name varchar(255) not null,
-- #     second_name varchar(255) not null,
-- #     middle_name varchar(255),
-- #     login varchar(255) not null,
-- #     password varchar(255) not null,
-- #     born_date date not null
-- # );
-- # create table objects (
-- #   id serial primary key,
-- #   name varchar(255) not null
-- # );
-- #
-- # insert into objects (name) value ('АиП'),('ООП'),('Веб программирование'),('Стандартизации'),('Основы философии'),('Методы защиты информации'),('Физкультура')
-- # ,('Моделирование');
--
-- # Удалить
-- # create table week(
-- #     id serial primary key ,
-- #     name varchar(255) not null
-- # );
-- #
-- # insert into week(name)value ('Понедельник'),('Вторник'),('Среда'),('Четверг'),('Пятница'),('Суббота');
--
-- # create table groups(
-- #   id serial primary key,
-- #   id_teacher int references teachers(id) not null ,
-- #   name varchar(255) not null
-- # );
-- #
-- # insert into groups (name) value ('П-20-51б');
--
-- # create table group_chart(
-- #   id serial primary key,
-- #   id_group int references groups(id),
-- #   curse int not null,
-- #   name varchar(255) not null,
-- #   date_start date not null,
-- #   date_end date not null
-- # );
-- # insert into group_chart (id_group, curse, name, date_start, date_end) values (1,3,'Учеба','1.09.2023','25.10.2023');
-- #
-- #
-- # create table schedule(
-- #   id serial primary key,
-- #   id_object int references objects(id),
-- #   id_group_chart int references group_chart(id),
-- #  # id_week int references  week(id),
-- #   couple int not null
-- # );
-- #
-- # #insert into schedule (id_object, id_group_chart, id_week, couple) values (1,1,1,2),(2,1,1,3),(1,1,1,3);
-- # insert into schedule (id_object, id_group_chart,  couple) values (1,1,2),(2,1,3),(1,1,3);
-- #
-- # create table students(
-- #   id serial primary key,
-- #   id_group int references groups (id),
-- #   first_name varchar(255) not null,
-- #   second_name varchar(255) not null,
-- #   middle_name varchar(255),
-- #   login varchar(255) not null,
-- #   password varchar(255) not null,
-- #   born_date date not null
-- # );
-- #
-- # create table pass (
-- -- #   id serial primary key,
-- -- #   id_student int references students (id),
-- -- #   name_file varchar(255),
-- -- #   date_start date,
-- -- #   date_end date
-- # );
-- #
-- # insert into students (id_group, first_name, second_name, middle_name, login, password, born_date) values (1,'Максим','Кораблев','Игорович',
-- #                                                                                                           'kramzos222','maxim22','2.04.2004');
-- # create table evaluations(
-- #   id serial primary key,
-- #   id_student int references students(id),
-- #   id_object int references objects(id),
-- #  # id_week int references week(id),
-- #   grade int not null,
-- #   date_grade date not null
-- # );

-- # Рефакторинг №2
create table teachers
(
    id          serial primary key,
    first_name  varchar(255) not null,
    second_name varchar(255) not null,
    middle_name varchar(255),
    login       varchar(255) not null,
    password    varchar(255) not null,
    born_date   date         not null
);
insert into teachers (first_name, second_name, middle_name, login, password, born_date) VALUES ('Денис', 'Попов',
                                                                                               'Валентинович', 'fdsfew',
                                                                                               'fsdfsdfsdf',
                                                                                               '1993.04.22');

create table groups
(
    id         serial primary key,
    id_teacher int references teachers (id) not null,
    name       varchar(255)                 not null
);

insert into groups (id_teacher, name) VALUES (1, 'П-20-51б');

create table teachers_group
(
    id          serial primary key,
    id_teacher  int references teachers (id),
    id_group    int references groups (id),
    name_object varchar(255) not null,
    course      int          not null,
    semester    int          not null
);

-- # Первая часть
create table students
(
    id          serial primary key,
    id_group    int references groups (id),
    first_name  varchar(255) not null,
    second_name varchar(255) not null,
    middle_name varchar(255),
    login       varchar(255) not null,
    password    varchar(255) not null,
    born_date   date         not null
);

insert into students (id_group, first_name, second_name, middle_name, login, password, born_date) VALUES (1, 'Максим',
                                                                                                         'Кораблев',
                                                                                                         'Игоревич',
                                                                                                         'fdsfsd',
                                                                                                         'fsdfsdf',
                                                                                                         '2004.04.02');

create table evaluations
(
    id              serial primary key,
    id_student      int references students (id),
    name_object     varchar(255) not null,
    date_evaluation date         not null,
    ball            int          not null
);
create table evaluations_practice
(
    id              serial primary key,
    id_student      int references students (id),
    name_practice   varchar(255) not null,
    date_evaluation date         not null,
    ball            int          not null
);

create table omissions
(
    id             serial primary key,
    id_student     int references students (id),
    date_omissions date         not null,
    status         varchar(255) not null
);
-- # Вторая часть

create table graph
(
    id         serial primary key,
    id_group   int references groups (id),
    semester   int  not null,
    date_start date not null,
    date_end   date not null
);
insert into graph (id_group, semester, date_start, date_end) VALUES (1, 1, '02.09.2023', '10.10.2023');

create table week_study
(
    id          serial primary key,
    id_graph    int references graph (id),
    name        varchar(255) not null,
    first_para  varchar(255),
    second_para varchar(255),
    third_para  varchar(255),
    fourth_para varchar(255),
    fifth_para  varchar(255),
    sixth_para  varchar(255)
);

create table practice_study
(
    id         serial primary key,
    id_graph    int references graph (id),
    name       varchar(255) not null,
    date_start date         not null,
    date_end   date         not null
);
create table object
(
    id   serial primary key,
    name varchar(255)
);