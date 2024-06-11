-- # Рефакторинг №2
--  CREATE DATABASE rating_log_college WITH ENCODING='UTF8' LC_CTYPE='en_US.UTF-8' LC_COLLATE='en_US.UTF-8' OWNER=postgres TEMPLATE=template0;
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
       ('natalya', '$2y$10$HmRmb6BDrg5hqzaxCPZQgOoTijc0qCsWv3U8LFt77Jg8.EBVJf5gi', 'teacher'),
       ('admin', '$2a$12$DwBjDJ0PobYbo/1MuM6wLO5WwKatjiNIAcz9HwEk9xQs3Vi2e6jh2', 'admin'),
       ('ivan', '$2y$10$NSNU6awbi0lNEluE9Emeuu6smnzSzLjbl1als5uB1ZPdFsm9xKdJa', 'teacher'),
       ('ekaterina', '$2y$10$yLt5ttUBArNV3rJBY4btOuN1EkBbggIyrKFlR8ZZVIG3dKUbt/aKu', 'teacher'),
       ('alexey', '$2y$10$9N8V40UC709m0EXaA2rTLOZWhQ.hp6hOx0EilOEShDpiAumF3HnMy', 'teacher'),
       ('olga', '$2y$10$7n9c6l3rMbYL6Gp4ZBeVke63FNyA0uSOAKQ6cBSTL1V.cUqY53HAu', 'teacher'),
       ('marinaa', '$2y$10$vK1XsXNzAWI2/HtQaSypWO1WSy0LeIgdikmadSIpHhC6me8KT.Znm', 'teacher'),
       ('dmitry', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'teacher'),
       ('ivann', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('ekaterinass', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('mikhail', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('dmitryyy', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'teacher'),
       ('anna', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('maria', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('pavel', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('olgaa', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('artem', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('anastasia', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('sergey', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('viktoria', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student'),
       ('nikita', '$2y$10$bE.UIife7hujw8kXY88IbO42SPdJqNoO7cdbrMSuVlSGNrwGFZfbK', 'student');

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
VALUES (3, 'Денис', 'Попов', 'Валентинович', '1993.04.22', '2010.04.10'),
       (4, 'Марина', 'Галимовна', 'Игоревна', '1980.07.23', '2012.07.16'),
       (5, 'Наталья', 'Ивановна', 'Павлова', '1992.12.08', '2016.08.25'),
       (7, 'Иван', 'Петрович', 'Иванов', '1990.02.15', '2010.09.20'),
       (8, 'Екатерина', 'Александровна', 'Смирнова', '1985.11.07', '2012.03.14'),
       (9, 'Алексей', 'Сергеевич', 'Козлов', '1978.08.30', '2005.06.28'),
       (10, 'Ольга', 'Дмитриевна', 'Никитина', '1995.04.12', '2018.10.10'),
       (11, 'Марина', 'Галимовна', 'Игоревна', '1980.07.23', '2015.05.22'),
       (12, 'Дмитрий', 'Андреевич', 'Соколов', '1987.09.05', '2011.12.18');

create table curator
(
    id         serial primary key,
    id_teacher int references teachers (id) not null
);
insert into curator (id_teacher)
VALUES (3),
       (4),
       (5),
       (7),
       (8);

create table specialization
(
    id   serial primary key,
    name varchar(255) not null
);

insert into specialization (name)
VALUES ('Вычислительная техника и программное обеспечение'),
       ('Техник-дизайнер'),
       ('Экономист-бухгалтер');

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
VALUES (1, 1, 'П-23-51б', 2023, 1),
       (1, 1, 'П-23-54к', 2023, 1),
       (1, 1, 'П-23-56рб', 2023, 1),
       (2, 2, 'Ди-23-51К', 2023, 1),
       (3, 3, 'Э-23-67рб', 2023, 1);

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
        'Этот тест предназначен для оценки различных аспектов личности и самопознания. Он включает несколько секций, каждая из которых направлена на анализ различных характеристик личности и личных предпочтений.',
        'Самопознание');

insert into home_work (id_groups, id_teacher, start_date, end_date, name, status, description, name_subject)
VALUES (1, 4, '2024-05-16', '2024-05-28', 'Прочитать книгу', 'Назначено',
        'Это задание направлено на всестороннее изучение выбранной книги. Участникам предстоит не только прочитать книгу, но и провести её анализ, подготовив отчет или эссе по заданным параметрам.',
        'Самопознание');

insert into home_work (id_groups, id_teacher, start_date, end_date, name, status, description, name_subject)
VALUES (1, 4, '2024-03-16', '2024-03-28', 'Сделать презентацию', 'Назначено',
        'Это задание направлено на развитие навыков создания и проведения презентаций. Участникам предстоит выбрать тему, провести исследование, создать слайды и подготовить устное выступление.',
        'Самопознание');

insert into home_work (id_groups, id_teacher, start_date, end_date, name, status, description, name_subject)
VALUES (1, 3, '2024-05-25', '2024-05-30', 'Сделать сайт', 'Назначено',
        'Это задание направлено на развитие навыков веб-разработки, включая планирование, дизайн, программирование и тестирование веб-сайта. Участникам предстоит пройти весь цикл разработки от концепции до реализации.',
        'Веб-программирование');

create table file_home_task
(
    id           serial primary key,
    id_home_task int references home_work (id),
    name         varchar(255) not null,
    date_create  date         not null

);

insert into file_home_task (id_home_task, name, date_create)
VALUES (4, '1.pdf', current_date);
insert into file_home_task (id_home_task, name, date_create)
VALUES (4, '2.pdf', current_date);
insert into file_home_task (id_home_task, name, date_create)
VALUES (2, '3.pdf', current_date);
insert into file_home_task (id_home_task, name, date_create)
VALUES (2, '4.pdf', current_date);

create table courses
(
    id        serial primary key,
    id_groups int references groups (id),
    course    int not null,
    year      int not null
);

insert into courses (id_groups, course, year)
VALUES (1, 1, 2023),
       (2, 1, 2023),
       (3, 1, 2023),
       (4, 1, 2023),
       (5, 1, 2023);

create table files_group
(
    id           serial primary key,
    id_courses   int references courses (id),
    name         varchar(255) not null,
    date_create  date         not null,
    subject_name varchar(255) not null,
    description  varchar(255) not null
);

insert into files_group (id_courses, name, date_create, subject_name, description)
VALUES (1, '5.pdf', '2024-05-13', 'Веб-программирование', 'Книга по веб'),
       (1, '6.pdf', '2024-03-10', 'АИП', 'Книга по алгоритмам'),
       (1, '7.pdf', '2024-02-20', 'База данных', 'Презентация по бд'),
       (1, '8.pdf', '2024-01-15', 'ТРПО', 'Материал');

-- # Первая часть
create table students
(
    id            int references users (id) not null primary key,
    id_group      int references groups (id),
    first_name    varchar(255)              not null,
    second_name   varchar(255)              not null,
    middle_name   varchar(255),
    born_date     date                      not null,
    subgroup_name varchar(255)
);

INSERT INTO students (id, id_group, first_name, second_name, middle_name, born_date, subgroup_name)
VALUES
    (2, 1, 'Максим', 'Кораблев', 'Игоревич', '2004.03.17', 'Б'),
    (1, 1, 'Алексей', 'Зарубин', 'Алексеевич', '2005.12.10', 'Б'),
    (13, 1, 'Иван', 'Петров', 'Иванович', '2004.09.03', 'Б'),
    (14, 1, 'Екатерина', 'Смирнова', 'Петровна', '2003.06.26', 'Б'),
    (15, 1, 'Михаил', 'Иванов', 'Михайлович', '2004.01.14', 'Б'),
    (16, 2, 'Дмитрий', 'Федоров', 'Дмитриевич', '2005.08.07', 'Б'),
    (17, 2, 'Анна', 'Сергеева', 'Андреевна', '2003.12.23', 'Б'),
    (18, 2, 'Мария', 'Козлова', 'Игоревна', '2004.05.18', 'Б'),
    (19, 2, 'Павел', 'Морозов', 'Павлович', '2003.07.30', 'Б'),
    (20, 2, 'Ольга', 'Новикова', 'Олеговна', '2005.02.09', 'Б'),
    (21, 3, 'Артем', 'Соколов', 'Артемович', '2004.11.04', 'Б'),
    (22, 3, 'Анастасия', 'Кузнецова', 'Александровна', '2003.09.21', 'Б'),
    (23, 3, 'Сергей', 'Васильев', 'Сергеевич', '2005.03.15', 'Б'),
    (24, 3, 'Виктория', 'Павлова', 'Викторовна', '2004.06.12', 'Б'),
    (25, 3, 'Никита', 'Семенов', 'Никитич', '2004.05.02', 'Б');



create table files_student
(
    id          serial primary key,
    id_students int references students (id),
    name        varchar(255) not null,
    date_create date         not null,
    type_file   varchar(255) not null
);

insert into files_student (id_students, name, date_create, type_file)
VALUES (2, '9.pdf', current_date, 'дз');
VALUES (2, '10.pdf', current_date, 'дз');

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
       (1, 1),
       (13, 1),
       (14, 1),
       (15, 1),
       (16, 1),
       (17, 1),
       (18, 1),
       (19, 1),
       (20, 1),
       (21, 1),
       (22, 1),
       (23, 1),
       (24, 1),
       (25, 1);

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
VALUES (1, 'Веб-программирование', current_timestamp, ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Основа право', current_timestamp, ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Самопознание', current_timestamp, ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'АИП', current_timestamp, ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', current_timestamp, ROUND(RANDOM() * 100)::INT, 'Петрович Иван');


INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Веб-программирование', '2023-09-01', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2023-09-05', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2023-09-10', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2023-09-15', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2023-09-20', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2023-09-25', ROUND(RANDOM() * 100)::INT, 'Попов Денис');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Основа право', '2023-09-03', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2023-09-04', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2023-09-14', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2023-09-16', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2023-09-21', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2023-09-27', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Самопознание', '2023-09-02', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2023-09-06', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2023-09-17', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2023-09-18', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2023-09-23', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2023-09-29', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'АИП', '2023-09-17', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2023-09-10', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2023-09-19', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2023-09-22', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2023-09-23', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2023-09-25', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2023-09-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2023-09-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2023-09-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');


INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Веб-программирование', '2024-01-01', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-01-05', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-01-10', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-01-15', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-01-20', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-01-25', ROUND(RANDOM() * 100)::INT, 'Попов Денис');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Основа право', '2024-01-03', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-01-04', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-01-14', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-01-16', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-01-21', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-01-27', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Самопознание', '2024-01-02', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-01-06', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-01-17', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-01-18', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-01-23', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-01-29', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'АИП', '2024-01-17', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-01-10', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-01-19', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-01-22', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-01-23', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-01-25', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-01-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-01-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-01-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');

INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Веб-программирование', '2024-02-01', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-02-05', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-02-10', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-02-15', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-02-20', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-02-25', ROUND(RANDOM() * 100)::INT, 'Попов Денис');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Основа право', '2024-02-03', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-02-04', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-02-14', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-02-16', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-02-21', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-02-27', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Самопознание', '2024-02-02', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-02-06', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-02-17', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-02-18', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-02-23', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-02-29', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'АИП', '2024-02-17', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-02-10', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-02-19', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-02-22', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-02-23', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-02-25', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-02-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-02-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-02-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');


INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Веб-программирование', '2024-03-01', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-03-05', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-03-10', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-03-15', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-03-20', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-03-25', ROUND(RANDOM() * 100)::INT, 'Попов Денис');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Основа право', '2024-03-03', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-03-04', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-03-14', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-03-16', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-03-21', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-03-27', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Самопознание', '2024-03-02', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-03-06', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-03-17', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-03-18', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-03-23', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-03-29', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'АИП', '2024-03-17', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-03-10', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-03-19', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-03-22', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-03-23', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-03-25', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-03-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-03-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-03-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');


INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Веб-программирование', '2024-04-01', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-04-05', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-04-10', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-04-15', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-04-20', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-04-25', ROUND(RANDOM() * 100)::INT, 'Попов Денис');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Основа право', '2024-04-03', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-04-04', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-04-14', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-04-16', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-04-21', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-04-27', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Самопознание', '2024-04-02', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-04-06', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-04-17', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-04-18', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-04-23', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-04-29', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'АИП', '2024-04-17', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-04-10', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-04-19', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-04-22', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-04-23', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-04-25', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-04-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-04-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-04-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');


INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Веб-программирование', '2024-05-01', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-05-05', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-05-10', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-05-15', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-05-20', ROUND(RANDOM() * 100)::INT, 'Попов Денис'),
       (1, 'Веб-программирование', '2024-05-25', ROUND(RANDOM() * 100)::INT, 'Попов Денис');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Основа право', '2024-05-03', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-05-04', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-05-14', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-05-16', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-05-21', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина'),
       (1, 'Основа право', '2024-05-27', ROUND(RANDOM() * 100)::INT, 'Александровна Екатерина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'Самопознание', '2024-05-02', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-05-06', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-05-17', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-05-18', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-05-23', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина'),
       (1, 'Самопознание', '2024-05-29', ROUND(RANDOM() * 100)::INT, 'Галимовна Марина');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'АИП', '2024-05-17', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-05-10', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-05-19', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-05-22', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-05-23', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'АИП', '2024-05-25', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-05-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');
INSERT INTO evaluations (id_course, name_object, date_evaluation, ball, name_teacher)
VALUES (1, 'ООП', '2024-05-13', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-07', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-12', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-16', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-14', ROUND(RANDOM() * 100)::INT, 'Петрович Иван'),
       (1, 'ООП', '2024-05-24', ROUND(RANDOM() * 100)::INT, 'Петрович Иван');



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
VALUES (1, '2023-09-01', 'Отсутствует', 'Веб-программирование', 3, 9),
       (1, '2023-09-01', 'Отсутствует', 'Основа право', 4, 9),
       (1, '2023-09-01', 'С опозданием', 'Основа право', 4, 9),
       (1, '2023-09-01', 'С справкой', 'Основа право', 4, 9),
       (1, '2023-09-01', 'Без опозданий', 'Основа право', 5, 9),
       (1, '2023-09-02', 'Отсутствует', 'Веб-программирование', 3, 9),
       (1, '2023-09-03', 'Отсутствует', 'Основа право', 4, 9),
       (1, '2023-09-04', 'С опозданием', 'Основа право', 4, 9),
       (1, '2023-09-05', 'С справкой', 'Основа право', 4, 9),
       (1, '2023-09-06', 'Без опозданий', 'Основа право', 5, 9),
       (1, '2023-09-07', 'Отсутствует', 'Самопознание', 2, 9),
       (1, '2023-09-08', 'Отсутствует', 'АИП', 3, 9),
       (1, '2023-09-09', 'Отсутствует', 'ООП', 4, 9),
       (1, '2023-09-10', 'Отсутствует', 'База данных', 5, 9),
       (1, '2023-09-11', 'Отсутствует', 'ТРПО', 6, 9),
       (1, '2023-09-12', 'Отсутствует', 'Веб-программирование', 1, 9),
       (1, '2023-09-13', 'Отсутствует', 'Основа право', 2, 9),
       (1, '2023-09-14', 'С опозданием', 'Основа право', 2, 9),
       (1, '2023-09-15', 'С справкой', 'Основа право', 2, 9),
       (1, '2023-09-16', 'Без опозданий', 'Основа право', 3, 9),
       (1, '2023-09-17', 'Отсутствует', 'Самопознание', 1, 9),
       (1, '2023-09-18', 'Отсутствует', 'АИП', 2, 9),
       (1, '2023-09-19', 'Отсутствует', 'ООП', 3, 9),
       (1, '2023-09-20', 'Отсутствует', 'База данных', 4, 9),
       (1, '2023-09-21', 'Отсутствует', 'ТРПО', 5, 9),
       (1, '2023-09-22', 'Отсутствует', 'Веб-программирование', 3, 9),
       (1, '2023-09-23', 'Отсутствует', 'Основа право', 4, 9),
       (1, '2023-09-24', 'С опозданием', 'Основа право', 4, 9),
       (1, '2023-09-25', 'С справкой', 'Основа право', 4, 9),
       (1, '2023-09-26', 'Без опозданий', 'Основа право', 5, 9),
       (1, '2023-09-27', 'Отсутствует', 'Самопознание', 2, 9),
       (1, '2023-09-28', 'Отсутствует', 'АИП', 3, 9),
       (1, '2023-09-29', 'Отсутствует', 'ООП', 4, 9),
       (1, '2023-09-30', 'Отсутствует', 'База данных', 5, 9);


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
VALUES (1, 1, 1, '2023-09-02', '2023-10-10'),
       (1, 2, 1, '2023-11-02', '2024-06-30');

insert into study_process (id_group, semester, course, date_start, date_end)
VALUES (2, 1, 1, '2023-09-02', '2023-10-10'),
       (2, 2, 1, '2023-11-02', '2024-06-30');

insert into study_process (id_group, semester, course, date_start, date_end)
VALUES (3, 1, 1, '2023-09-02', '2023-10-10'),
       (3, 2, 1, '2023-11-02', '2024-06-30');

insert into study_process (id_group, semester, course, date_start, date_end)
VALUES (4, 1, 1, '2023-09-02', '2023-10-10'),
       (4, 2, 1, '2023-11-02', '2024-06-30');

insert into study_process (id_group, semester, course, date_start, date_end)
VALUES (5, 1, 1, '2023-09-02', '2023-10-10'),
       (5, 2, 1, '2023-11-02', '2024-06-30');

create table type_study
(
    id               serial primary key,
    id_study_process int references study_process (id),
    name             varchar(255) not null, --Прописываем: учеба, практика, учебная практика
    date_start       date         not null,
    date_end         date         not null
);

insert into type_study (id_study_process, name, date_start, date_end)
VALUES (1, 'учеба', '2023-09-02', '2023-10-10'),
       (2, 'учеба', '2023-11-02', '2024-06-30');

insert into type_study (id_study_process, name, date_start, date_end)
VALUES (3, 'учеба', '2023-09-02', '2023-10-10'),
       (4, 'учеба', '2023-11-02', '2024-06-30');

insert into type_study (id_study_process, name, date_start, date_end)
VALUES (5, 'учеба', '2023-09-02', '2023-10-10'),
       (6, 'учеба', '2023-11-02', '2024-06-30');

insert into type_study (id_study_process, name, date_start, date_end)
VALUES (7, 'учеба', '2023-09-02', '2023-10-10'),
       (8, 'учеба', '2023-11-02', '2024-03-25'),
       (8, 'практика', '2024-03-26', '2024-04-25'),
       (8, 'учеба', '2024-04-26', '2024-06-30');

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
VALUES (1, 2, 1225),
       (3, 2, 3210),
       (2, 3, 2313),
       (2, 2, 2214),
       (3, 2, 3211);
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
       ('ООП'),
       ('База данных'),
       ('ТРПО'),
       ('Дизайн среды'),
       ('Дизайн интерьера'),
       ('Комплексная работа с территорией'),
       ('История и теория искусства и дизайна'),
       ('Концепция'),
       ('Мебель и оборудование'),
       ('Рабочие чертежи: потолки, электрика'),
       ('Статистика и теория вероятностей'),
       ('Исследование операций'),
       ('История экономических учений'),
       ('Основы микро- и макроэкономики');

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

-- insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
--                         number_of_couple)
-- VALUES (3, 1, 1, 3, 1, 1, 1),
--        (3, 2, 2, 4, 2, 1, 2),
--        (3, 3, 3, 4, 3, 1, 3),
--        (3, 1, 1, 3, 1, 2, 1),
--        (3, 2, 2, 4, 3, 2, 2),
--        (3, 4, 4, 3, 3, 3, 4),
--        (3, 5, 5, 3, 1, 3, 5),
--        (3, 6, 1, 3, 1, 3, 6),
--        (3, 2, 2, 4, 3, 4, 2),
--        (3, 3, 3, 4, 4, 4, 3),
--        (3, 4, 4, 3, 2, 4, 4),
--        (3, 1, 1, 3, 4, 5, 1),
--        (3, 2, 2, 4, 3, 5, 2);


insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (2, 1, 1, 3, 1, 1, 1),
       (4, 2, 1, 3, 1, 1, 2),
       (6, 3, 1, 3, 1, 1, 3),
       (2, 2, 1, 3, 1, 2, 2),
       (4, 3, 1, 3, 1, 2, 3),
       (6, 4, 1, 3, 1, 2, 4),
       (2, 2, 1, 3, 1, 3, 2),
       (4, 3, 1, 3, 1, 3, 3),
       (6, 4, 1, 3, 1, 3, 4),
       (2, 1, 1, 3, 1, 4, 1),
       (4, 2, 1, 3, 1, 4, 2),
       (6, 3, 1, 3, 1, 4, 3);

insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (2, 2, 4, 7, 2, 1, 2),
       (2, 1, 4, 7, 2, 2, 1),
       (2, 3, 4, 7, 2, 3, 3);


insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (2, 3, 5, 7, 2, 1, 3),
       (2, 3, 5, 7, 2, 2, 3),
       (2, 4, 5, 7, 2, 3, 4);

insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (2, 2, 3, 4, 3, 4, 2),
       (2, 3, 3, 4, 3, 5, 3);

insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (2, 3, 2, 8, 4, 4, 3),
       (1, 3, 2, 8, 4, 5, 1);

insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (1, 2, 6, 9, 4, 5, 2);

insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (1, 3, 7, 10, 2, 5, 3);



insert into plan_study (id_type_study, id_time_study, id_subject_study, id_teacher, id_auditorium, id_week,
                        number_of_couple)
VALUES (8, 2, 8, 11, 1, 1, 2),
       (8, 1, 9, 11, 2, 1, 1),
       (8, 4, 10, 12, 3, 1, 4),
       (8, 2, 11, 12, 1, 2, 2),
       (8, 3, 12, 11, 3, 2, 3),
       (8, 1, 13, 12, 3, 3, 1),
       (8, 2, 8, 11, 1, 3, 2),
       (8, 3, 9, 11, 1, 3, 3),
       (8, 1, 10, 12, 3, 4, 1),
       (8, 5, 11, 12, 4, 4, 5),
       (8, 6, 12, 11, 2, 4, 6),
       (8, 3, 13, 12, 4, 5, 3),
       (8, 4, 10, 11, 3, 5, 4);

