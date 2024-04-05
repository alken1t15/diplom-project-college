-- # Рефакторинг №2
-- create database rating_log_college;
create table users
(
    id       serial primary key,
    login    varchar(255) not null unique ,
    password varchar(255) not null,
    jwt      text
);

insert into users(login, password)
values ('fdsfew',
        'fsdfsdfsdf'),
       ('alex', '$2y$10$QsmdOGpTOpn4qWLD3jSIY.8C8bwMIR8PFXuIkpf5aejT2xCpO0pji'),
       ('maxim','fsdfsdfsdfsdfsd');

create table teachers
(
    id          int references users (id) not null primary key,
    first_name  varchar(255)              not null,
    second_name varchar(255)              not null,
    middle_name varchar(255),
    born_date   date                      not null
);
insert into teachers (id, first_name, second_name, middle_name, born_date)
VALUES (3, 'Денис', 'Попов',
        'Валентинович',
        '1993.04.22');

create table curator
(
    id         serial primary key,
    id_teacher int references teachers (id) not null
);
insert into curator (id_teacher)
VALUES (3);

create table groups
(
    id                  serial primary key,
    id_curator          int references curator (id) not null,
    name                varchar(255)                not null,
    specialization_name varchar(255)                not null,
    year                int                         not null,
    course int not null
);

insert into groups (id_curator, name, specialization_name, year,course)
VALUES (1, 'П-20-51б', 'Вычислительная техника и программное обеспечение', 2020,1);

-- create table teachers_group
-- (
--     id          serial primary key,
--     id_teacher  int references teachers (id),
--     id_group    int references groups (id),
--     name_object varchar(255) not null,
--     course      int          not null,
--     semester    int          not null
-- );

-- # Первая часть
create table students
(
    id            int references users (id) not null primary key,
    id_group      int references groups (id),
    first_name    varchar(255)              not null,
    second_name   varchar(255)              not null,
    middle_name   varchar(255),
    login         varchar(255)              not null,
    password      varchar(255)              not null,
    born_date     date                      not null,
    subgroup_name varchar(255)              not null
);

insert into students (id, id_group, first_name, second_name, middle_name, login, password, born_date, subgroup_name)
VALUES (1, 1, 'fdsf', 'fsdfsd', 'fsdfsd',
        'fdsf', 'fsdfsd',
        '12.08.2004', 'Б');

create table students_course(
    id serial primary key,
    id_students int references students (id),
    course int not null
);

insert into students_course(id_students,course) values (1,1);

-- insert into students (id_group, first_name, second_name, middle_name, login, password, born_date)
-- VALUES (1, 'Максим',
--         'Кораблев',
--         'Игоревич',
--         'fdsfsd',
--         'fsdfsdf',
--         '2004.04.02');

create table evaluations
(
    id              serial primary key,
    id_course      int references students_course (id),
    name_object     varchar(255) not null,
    date_evaluation date         not null,
    ball            int          not null,
    name_teacher    varchar(255) not null
);
create table evaluations_between
(
    id          serial primary key,
    id_course  int references students_course (id),
    name_object varchar(255) not null,
    course      int          not null,
    semester    int          not null,
    month       varchar(255),
    final_total int
);
create table evaluations_total
(
    id              serial primary key,
    id_course      int references students_course (id),
    name_object     varchar(255) not null,
    course          int          not null,
    first_semester  int,
    second_semester int,
    final_total     int
);

create table omissions
(
    id             serial primary key,
    id_course     int references students_course (id),
    date_omissions date         not null,
    status         varchar(255) not null,
    name_object    varchar(255) not null,
    number_couple  int          not null,
    number_month   int          not null
);


INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-01', 'Пропущено', 'Математика', 3, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-09-01', 'Пропущено', 'Физика', 4, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-09-01', 'Без опозданий', 'Основа право', 5, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-09-01', 'Отсутвует', 'Комп сети', 2, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-03', 'Пропущено', 'Физика', 5, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-05', 'Пропущено', 'История', 2, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-07', 'Пропущено', 'Биология', 4, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-09', 'Пропущено', 'География', 1, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-11', 'Пропущено', 'Химия', 3, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-13', 'Пропущено', 'Литература', 5, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-15', 'Пропущено', 'Иностранный язык', 2, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-17', 'Пропущено', 'Искусство', 4, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-19', 'Пропущено', 'Спорт', 1, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-21', 'Пропущено', 'Экономика', 3, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-23', 'Пропущено', 'Психология', 5, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-25', 'Пропущено', 'Политология', 2, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-27', 'Пропущено', 'Религия', 4, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-04-29', 'Пропущено', 'Философия', 1, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-05-01', 'Пропущено', 'Астрономия', 3, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-05-03', 'Пропущено', 'Анатомия', 5, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-05-05', 'Пропущено', 'Ботаника', 2, 9);

INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2024-05-07', 'Пропущено', 'Зоология', 4, 9);
-- # Вторая часть

create table study_process
(
    id         serial primary key,
    id_group   int references groups (id),
    semester   int  not null,
    course     int  not null,
    date_start date not null,
    date_end   date not null
);
insert into study_process (id_group, semester, course, date_start, date_end)
VALUES (1, 1, 1, '02.09.2023', '10.10.2023');
insert into study_process (id_group, semester, course, date_start, date_end)
VALUES (1, 2, 1, '02.11.2023', '10.10.2024');
-- Изменения было
-- create table week_study
-- (
--     id          serial primary key,
--     id_graph    int references graph (id),
--     name        varchar(255) not null,
--     first_para  varchar(255),
--     second_para varchar(255),
--     third_para  varchar(255),
--     fourth_para varchar(255),
--     fifth_para  varchar(255),
--     sixth_para  varchar(255)
-- );
--
-- create table practice_study
-- (
--     id         serial primary key,
--     id_graph    int references graph (id),
--     name       varchar(255) not null,
--     date_start date         not null,
--     date_end   date         not null
-- );
-- Стало
create table type_study
(
    id               serial primary key,
    id_study_process int references study_process (id),
    name             varchar(255) not null, --Прописываем: учеба, практика, учебная практика
    date_start       date         not null,
    date_end         date         not null
);

insert into type_study (id_study_process, name, date_start, date_end) VALUES (1,'учеба','02.09.2023', '10.10.2023');
insert into type_study (id_study_process, name, date_start, date_end) VALUES (2,'учеба','02.11.2023', '10.10.2024');
-- create table time_study
-- (
--     id            serial primary key,
--     id_type_study int references type_study (id),
--     name          varchar(255) not null, --День недели
--     first_para    varchar(255),
--     second_para   varchar(255),
--     third_para    varchar(255),
--     fourth_para   varchar(255),
--     fifth_para    varchar(255),
--     sixth_para    varchar(255)
-- );
create table time_study
(
    id           serial primary key,
    start_lesson time not null,
    end_lesson   time not null
);

insert into time_study(start_lesson, end_lesson)
values ('8:30', '10:00'),
       ('10:10', '11:40'),
       ('12:10', '13:40'),
       ('13:50', '15:20'),
       ('15:40', '17:10'),
       ('17:20', '18:50');

create table auditorium
(
    id      serial primary key,
    block   int not null,
    floor   int not null,
    cabinet int not null

);

insert into auditorium (block, floor, cabinet) VALUES (1,2,1045);
insert into auditorium (block, floor, cabinet) VALUES (3,5,1545);
insert into auditorium (block, floor, cabinet) VALUES (4,5,1645);
create table subject_study
(
    id   serial primary key,
    name varchar(255) not null
);
insert into subject_study(name) values ('Веб-программирование');
insert into subject_study(name) values ('Основа право');
insert into subject_study(name) values ('Комп сети');

create table week
(
    id   serial primary key,
    name varchar(255) not null
);

insert into week(name) values ('Понедельник');
insert into week(name) values ('Вторник');
insert into week(name) values ('Среда');
insert into week(name) values ('Четверг');
insert into week(name) values ('Пятница');
insert into week(name) values ('Суббота');

create table plan_study
(
    id               serial primary key,
    id_type_study    int references type_study (id),
    id_time_study    int references time_study (id),
    id_subject_study int references subject_study (id),
    id_teacher       int references teachers (id),
    id_auditorium    int references auditorium (id),
    id_week   int references week (id),
    number_of_couple int
);

insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week, number_of_couple) VALUES
                                                                                                                                  (2,1,1,3,1,5,1),
                                                                                                                                  (2,2,2,3,2,5,2),
                                                                                                                                  (2,3,3,3,3,5,3)
-- Конец
