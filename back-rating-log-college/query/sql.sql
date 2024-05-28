-- # Рефакторинг №2
-- create database rating_log_college;
create table users
(
    id       serial primary key,
    login    varchar(255) not null unique,
    password varchar(255) not null,
    role     varchar(255) not null,
    jwt      text
);

insert into users(login, password, role)
values ('alex', '$2a$12$NYaQWMAhfFbKNafq6YwhTuZyruktcWnsvHf7KrDc6TB9h0IwIx7E.', 'student'),
       ('maxim', '$2a$12$7ydeDHsnSCl.c820UFgBtOf04BeLyDSc1eDzZZrVTV33Xioy2PCtO', 'student'),
       ('popov', '$2a$12$7PLgfxHX34OgvqKmbeGfrua48kwTqwVFAgkZj/4x9b7MKHCicbthC', 'teacher'),
       ('marina', '$2a$12$fALbcsaeUCMZtWTPrr8wqO5pbkrM2BW2PXI.kZS6gFL54tganpPA.', 'teacher'),
       ('denis', '$2a$12$fALbcsaeUCMZtWTPrr8wqO5pbkrM2BW2PXI.kZS6gFL54tganpPA.', 'teacher'),
       ('admin', '$2a$12$DwBjDJ0PobYbo/1MuM6wLO5WwKatjiNIAcz9HwEk9xQs3Vi2e6jh2', 'admin');

create table teachers
(
    id          int references users (id) not null primary key,
    first_name  varchar(255)              not null,
    second_name varchar(255)              not null,
    middle_name varchar(255),
    born_date   date                      not null,
    start_work  date                      not null
);
insert into teachers (id, first_name, second_name, middle_name, born_date, start_work)
VALUES (3, 'Денис', 'Попов', 'Валентинович', '1993.04.22', current_date),
       (4, 'Марина', 'Галимовна', 'Игоревна', '1980.07.23', current_date),
       (5, 'Марина', 'Галимовна', 'Игоревна', '1980.07.23', current_date);

create table curator
(
    id         serial primary key,
    id_teacher int references teachers (id) not null
);
insert into curator (id_teacher)
VALUES (3);

create table specialization
(
    id   serial primary key,
    name varchar(255) not null
);

insert into specialization (name)
VALUES ('Вычислительная техника и программное обеспечение');

create table groups
(
    id                serial primary key,
    id_curator        int references curator (id)        not null,
    id_specialization int references specialization (id) not null,
    name              varchar(255)                       not null,
    year              int                                not null,
    current_course    int                                not null
);

insert into groups (id_curator, id_specialization, name, year, current_course)
VALUES (1, 1, 'П-20-51б', 2023, 1),
       (1, 1, 'П-20-66К', 2024, 1);

create table home_work
(
    id           serial primary key,
    id_groups    int references groups (id),
    id_teacher   int references teachers (id),
    start_date   date         not null,
    end_date     date         not null,
    name         varchar(255) not null,
    status       varchar(255) not null,
    description  text,
    name_subject varchar(255) not null
);
insert into home_work (id_groups, id_teacher, start_date, end_date, name, status, description, name_subject)
VALUES (1, 4, '2024-04-16', '2024-04-28', 'Тест', 'Назначено',
        'ыавщлывалывалдлдывалдыавлдыавьлыавьлываьлывьлыавьлдывьл', 'Самопознание');

insert into home_work (id_groups, id_teacher, start_date, end_date, name, status, description, name_subject)
VALUES (1, 3, '2024-05-16', '2024-05-28', 'Прочитать книгу', 'Назначено',
        'ыавщлывалывалдлдывалдыавлдыавьлыавьлываьлывьлыавьлдывьл', 'Веб-программирование');

insert into home_work (id_groups, id_teacher, start_date, end_date, name, status, description, name_subject)
VALUES (1, 4, '2024-03-16', '2024-03-28', 'Сделать презентацию', 'Назначено',
        'ыавщлывалывалдлдывалдыавлдыавьлыавьлываьлывьлыавьлдывьл', 'Самопознание');

insert into home_work (id_groups, id_teacher, start_date, end_date, name, status, description, name_subject)
VALUES (1, 3, '2024-05-25', '2024-05-30', 'Сделать сайт', 'Назначено',
        'Разработать сайт', 'Веб-программирование');

create table file_home_task
(
    id           serial primary key,
    id_home_task int references home_work (id),
    name         varchar(255) not null,
    date_create  date         not null

);

insert into file_home_task (id_home_task, name, date_create)
VALUES (4, '3.pdf', current_date);

create table courses
(
    id        serial primary key,
    id_groups int references groups (id),
    course    int not null,
    year      int not null
);

insert into courses (id_groups, course, year)
VALUES (1, 1, 2023);
insert into courses (id_groups, course, year)
VALUES (1, 2, 2024);

create table files_group
(
    id          serial primary key,
    id_courses  int references courses (id),
    name        varchar(255) not null,
    date_create date         not null
);

-- insert into files_group (id_courses, name,  date_create)
-- VALUES (1, 'История', '2024-04-13');

-- # Первая часть
create table students
(
    id            int references users (id) not null primary key,
    id_group      int references groups (id),
    first_name    varchar(255)              not null,
    second_name   varchar(255)              not null,
    middle_name   varchar(255),
    born_date     date                      not null,
    subgroup_name varchar(255)              not null
);

insert into students (id, id_group, first_name, second_name, middle_name, born_date, subgroup_name)
VALUES (2, 1, 'Максим', 'Кораблев', 'Игоревич', '2.05.2004', 'Б'),
       (1, 1, 'Алексей', 'Зарубин', 'Алексеевич', '12.06.2004', 'Б');


create table files_student
(
    id          serial primary key,
    id_students int references students (id),
    name        varchar(255) not null,
    date_create date         not null,
    type_file   varchar(255) not null
);

insert into files_student (id_students, name, date_create, type_file)
VALUES (2, '4.pdf', current_date, 'дз');

create table task_students
(
    id             serial primary key,
    id_howe_work   int references home_work (id),
    id_students    int references students (id),
    status         varchar(255) not null,
    course         int,
    ball           int,
    date_ball      timestamp,
    time_completed timestamp
);

insert into task_students(id_howe_work, id_students, status)
values (1, 2, 'Не выполнено'),
       (2, 2, 'Не выполнено'),
       (3, 2, 'Не выполнено');

insert into task_students(id_howe_work, id_students, status, time_completed)
VALUES (4, 2, 'Сдано', current_date);


create table task_students_files
(
    id               serial primary key,
    id_task_students int references task_students (id),
    id_file_students int references files_student (id)
);

insert into task_students_files (id_task_students, id_file_students)
VALUES (4, 1);


create table students_course
(
    id          serial primary key,
    id_students int references students (id),
    course      int not null
);

insert into students_course(id_students, course)
values (2, 1),
       (1, 1);

create table evaluations
(
    id              serial primary key,
    id_course       int references students_course (id),
    name_object     varchar(255) not null,
    date_evaluation date         not null,
    ball            int          not null,
    name_teacher    varchar(255) not null
);

INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Математика', '2024-04-01', 85, 'Иванова'),
       (1, 'Физика', '2024-04-02', 78, 'Петров'),
       (1, 'Литература', '2024-04-03', 90, 'Сидорова'),
       (1, 'История', '2024-04-04', 92, 'Козлов'),
       (1, 'Биология', '2024-04-05', 87, 'Смирнов'),
       (1, 'Химия', '2024-04-06', 80, 'Васильева'),
       (1, 'География', '2024-04-07', 95, 'Николаев'),
       (1, 'Иностранный язык', '2024-04-08', 88, 'Морозов'),
       (1, 'Информатика', '2024-04-09', 91, 'Белов'),
       (1, 'Искусство', '2024-04-10', 82, 'Горбунова'),
       (1, 'Математика', '2024-04-11', 75, 'Иванова'),
       (1, 'Физика', '2024-04-11', 80, 'Петров'),
       (1, 'Литература', '2024-04-11', 85, 'Сидорова'),
       (1, 'История', '2024-04-11', 90, 'Козлов'),
       (1, 'Биология', '2024-04-11', 88, 'Смирнов'),
       (1, 'Химия', '2024-04-11', 79, 'Васильева'),
       (1, 'География', '2024-04-11', 92, 'Николаев'),
       (1, 'Иностранный язык', '2024-04-11', 89, 'Морозов'),
       (1, 'Информатика', '2024-04-11', 93, 'Белов'),
       (1, 'Искусство', '2024-04-11', 81, 'Горбунова'),
       (1, 'Информатика', current_date, 85, 'Морозов'),
       (1, 'География', current_date, 69, 'Иванова'),
       (1, 'Биология', current_date, 40, 'Попов');


create table evaluations_between
(
    id          serial primary key,
    id_course   int references students_course (id),
    name_object varchar(255) not null,
    course      int          not null,
    semester    int          not null,
    month       varchar(255),
    final_total int
);
create table evaluations_total
(
    id              serial primary key,
    id_course       int references students_course (id),
    name_object     varchar(255) not null,
    course          int          not null,
    first_semester  int,
    second_semester int,
    final_total     int
);

create table omissions
(
    id               serial primary key,
    id_files_student int references files_student (id),
    id_course        int references students_course (id),
    date_omissions   date         not null,
    status           varchar(255) not null,
    name_object      varchar(255) not null,
    number_couple    int          not null,
    number_month     int          not null
);



INSERT INTO omissions (id_course, date_omissions, status, name_object, number_couple, number_month)
VALUES (1, '2023-09-01', 'Отсутвует', 'Математика', 3, 9),
       (1, '2023-09-01', 'Отсутвует', 'Физика', 4, 9),
       (1, '2023-09-01', 'С опозданием', 'Физика', 4, 9),
       (1, '2023-09-01', 'С справкой', 'Физика', 4, 9),
       (1, '2023-09-01', 'Без опозданий', 'Основа право', 5, 9),
       (1, '2023-09-01', 'Отсутвует', 'Комп сети', 2, 9),
       (1, '2023-09-03', 'Отсутвует', 'Физика', 5, 9),
       (1, '2023-09-05', 'Отсутвует', 'История', 2, 9),
       (1, '2023-09-07', 'Отсутвует', 'Биология', 4, 9),
       (1, '2023-09-09', 'Отсутвует', 'География', 1, 9),
       (1, '2023-09-11', 'Отсутвует', 'Химия', 3, 9),
       (1, '2023-09-13', 'Отсутвует', 'Литература', 5, 9),
       (1, '2023-09-15', 'Отсутвует', 'Иностранный язык', 2, 9),
       (1, '2023-09-17', 'Отсутвует', 'Искусство', 4, 9),
       (1, '2023-09-19', 'Отсутвует', 'Спорт', 1, 9),
       (1, '2023-09-21', 'Отсутвует', 'Экономика', 3, 9),
       (1, '2023-09-23', 'Отсутвует', 'Психология', 5, 9),
       (1, '2023-09-25', 'Отсутвует', 'Политология', 2, 9),
       (1, '2023-09-27', 'Отсутвует', 'Религия', 4, 9),
       (1, '2023-09-29', 'Отсутвует', 'Философия', 1, 9),
       (1, '2023-09-01', 'Отсутвует', 'Астрономия', 3, 9),
       (1, '2023-09-03', 'Отсутвует', 'Анатомия', 5, 9),
       (1, '2023-09-05', 'Отсутвует', 'Ботаника', 2, 9),
       (1, '2023-09-07', 'Отсутвует', 'Зоология', 4, 9);


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
VALUES (1, 1, 1, '02.09.2023', '10.10.2023'),
       (1, 2, 1, '02.11.2023', '30.06.2024');

insert into study_process (id_group, semester, course, date_start, date_end)
VALUES (2, 1, 1, '02.09.2023', '10.10.2023'),
       (2, 2, 1, '02.11.2023', '30.06.2024');

create table type_study
(
    id               serial primary key,
    id_study_process int references study_process (id),
    name             varchar(255) not null, --Прописываем: учеба, практика, учебная практика
    date_start       date         not null,
    date_end         date         not null
);

insert into type_study (id_study_process, name, date_start, date_end)
VALUES (1, 'учеба', '02.11.2023', '10.02.2024'),
       (2, 'учеба', '02.11.2023', '10.02.2024'),
       (2, 'практика', '11.02.2024', '10.06.2024'),
       (2, 'учебная практика', '11.06.2024', '10.10.2024');

insert into type_study (id_study_process, name, date_start, date_end)
VALUES (3, 'учеба', '02.11.2023', '10.02.2024');

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

insert into auditorium (block, floor, cabinet)
VALUES (1, 2, 1045),
       (3, 5, 1545),
       (4, 5, 1645),
       (2, 2, 1515);
create table subject_study
(
    id   serial primary key,
    name varchar(255) not null
);
insert into subject_study(name)
values ('Веб-программирование'),
       ('Основа право'),
       ('Самопознание'),
       ('АИП'),
       ('ООП');

create table week
(
    id   serial primary key,
    name varchar(255) not null
);

insert into week(name)
values ('Понедельник'),
       ('Вторник'),
       ('Среда'),
       ('Четверг'),
       ('Пятница'),
       ('Суббота');

create table plan_study
(
    id               serial primary key,
    id_type_study    int references type_study (id),
    id_time_study    int references time_study (id),
    id_subject_study int references subject_study (id),
    id_teacher       int references teachers (id),
    id_auditorium    int references auditorium (id),
    id_week          int references week (id),
    number_of_couple int
);

insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (3, 1, 1, 3, 1, 1, 1),
       (3, 2, 2, 4, 2, 1, 2),
       (3, 3, 3, 4, 3, 1, 3),
       (3, 1, 1, 3, 1, 2, 1),
       (3, 2, 2, 4, 3, 2, 2),
       (3, 4, 4, 3, 3, 3, 4),
       (3, 5, 5, 3, 1, 3, 5),
       (3, 6, 1, 3, 1, 3, 6),
       (3, 2, 2, 4, 3, 4, 2),
       (3, 3, 3, 4, 4, 4, 3),
       (3, 4, 4, 3, 2, 4, 4),
       (3, 1, 1, 3, 4, 5, 1),
       (3, 2, 2, 4, 3, 5, 2);


insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (5, 2, 2, 3, 1, 1, 2),
       (5, 1, 1, 3, 2, 1, 1),
       (5, 4, 3, 3, 3, 1, 4),
       (5, 2, 1, 3, 1, 2, 2),
       (5, 3, 2, 4, 3, 2, 3),
       (5, 1, 4, 4, 3, 3, 1),
       (5, 2, 5, 3, 1, 3, 2),
       (5, 3, 1, 3, 1, 3, 3),
       (5, 1, 2, 4, 3, 4, 1),
       (5, 5, 3, 4, 4, 4, 5),
       (5, 6, 4, 3, 2, 4, 6),
       (5, 3, 1, 3, 4, 5, 3),
       (5, 4, 2, 4, 3, 5, 4);

