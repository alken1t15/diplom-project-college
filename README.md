Дипломная работа
Backend часть:
Для построения Backend приложения я буду использовать язык программирования Java, а также framework для него Spring version 3.1.6.
Дополнительные библиотеки: (sl4j для логгирования, lombok для уменьшения кода)
СУБД будет использоваться: PostgreSQL
Таблицы для БД:
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
create table objects
(
    id   serial primary key,
    name varchar(255)
);
objects – это предметы, week – недели, groups – группа, group_chart – семестры и практика, schedule – расписание, students – студент, evaluations – оценки студентов.

Controller для работы с данными
STUTENDS
1)	Для получения одного студента нужно обратиться по адресу 
Localhost:8080/student/{id} (Нужно передать параметр id методом GET)
Статусы:
403- объект не найден и возвращается пустой объект
200 – ok и возвращает объект со значением
2) Для добавления студента нужно обратиться по адресу 
Localhost:8080/student/add (Нужно передать объект student{
Поле groupId = id группы
Поле firstName =имя
Поле secondName = фамилия
Поле middleName = отчество (если его нету отправить пустую строку)
Поле login = логин для аккаунта 
Поле password = пароль для аккаунта 
Поле bornDate = дата рождения
}
 методом POST)
Статусы:
400 – ошибка неправильно введенные данные
200 – ok студент добавился

GROUPS
1)	Для получения одной группы нужно обратиться по адресу 
http://localhost:8080/group/1 (Нужно передать параметр id методом GET)
Статусы:
403- не найдено и возвращается пустой объект
200 – ok и возвращает объект со значением
Возвращаемый объект:
{
    "id": 1, id группы
    "name": "П-20-51б", название группы
    "teachers": {
        "id": 1, id учителя
        "firstName": "Денис" , имя учителя
        "secondName": "Попов", фамилия учителя
        "middleName": "Валентинович", Отчество учителя
        "login": "fdsfew", Логин учителя
        "password": "fsdfsdfsdf", пароль учителя
        "bornDate": "1993-04-22"  дата рождения
    },
    "students": [
        {
            "groupId": 1, id группы
            "firstName": "Максим", имя студента
            "secondName": "Кораблев", фамилия студента
            "middleName": "Игоревич", отчество студента
            "login": "fdsfsd", логин студента
            "password": "fsdfsdf", пароль студента
            "bornDate": "2004-04-02", дата рождения
            "evaluations": [
                {
                    "nameObject": "fdsfsd", название предмета
                    "dateEvaluation": "2012-04-21", дата когда поставили 
                    "ball": 44 баллы 
                }
            ]
        }
    ]
}

2)	Для создания группы нужно обратиться по адресу 
http://localhost:8080/group/add (Нужно передать параметры:
 {
    "id":1, id учителя
    "name":"ди-20-51б" название группы
}
методом POST)
Статусы:
400 - ошибка (не правильно были переданы значения)
403 – такого учителя нету
409 – ошибка такая группа уже существует
200	– ok группа добавилась

3)	Для получения всех групп нужно обратиться по адресу 
http://localhost:8080/group/all (методом GET)
Статусы:
200	– ok удалось получить группы
4)	Для добавления студента в группу нужно обратиться по адресу 
http://localhost:8080/group/add/student (методом POST)
Статусы:
400 – ошибка (не правильно было отправлены значения)
409 – ошибка такая группа уже существует
200 – ok удалось добавить студента


OBJECTS
1)	Для получения одного предмета нужно обратиться по адресу 
http://localhost:8080/object/1 (Нужно передать параметр id методом GET)
Статусы:
403- не найдено и возвращается пустой объект
200 – ok и возвращает объект со значением
Возвращаемый объект:
Поле id = id предмета
Поле name = Название предмета
2)	Для получения всех предметов нужно обратиться по адресу 
http://localhost:8080/object/all (методом GET)
Статусы:
200 – ok и возвращает объект со значением
Возвращаемый объект:
Поле id = индикатор группы
Поле name = Название предмета
3)	Для добавления предмета нужно обратиться по адресу 
http://localhost:8080/object/add?name=Математика (Передать параметры name = название предмета методом GET)
Статусы:
400 – ошибка (Не правильно переданы параметры)
409 – конфликт (Такой предмет уже есть в бд) 
200 – ok и предмет добавлен


Evaluations

1)	Для добавление оценки нужно обратиться по адресу 
http://localhost:8080/evaluations/add (Нужно передать параметры:
{
    "idStudent":"1",  id студента
    "nameObject":"МЗИ", название предмета
    "dateEvaluation":"2012-04-21", дата
    "ball":44 баллы
}

  методом POST)
Статусы:
403- не найден студент с переданным id
200 – ok и оценка добавилась в бд
400 – ошибка не правильно были переданы параметры:
Пустые значения.

Teacher

1)	Для добавление учителя нужно обратиться по адресу 
http://localhost:8080/teacher/add (Нужно передать параметры:
{
    "firstName":"Геннадий", имя
    "secondName":"Загребов", фамилия
    "middleName":"Васильевич", отчество (Не обязательно) 
    "login":"efdsfsdf", логин
    "password":"43242342", пароль
    "bornDate":"1968-08-22" дата рождения
}


  методом POST)
Статусы:
200 – ok и преподаватель добавился в бд
400 – ошибка неправильно были переданы параметры:
Пустые значения или не все поля были переданы.

TeachersGroup

1)	Для добавления преподавателя, который ведет предмет нужно обратиться по адресу 
http://localhost:8080/teachers/group/add (Нужно передать параметры:
{
    "idTeacher":1, id учителя
    "idGroup":1, id учителя
    "name":"МЗИ", название предмета
    "course":1, курс
    "semester":2  семестер
}


  методом POST)
Статусы:
200 – ok и значения добавились в бд
400 – ошибка неправильно были переданы параметры:
Пустые значения или не все поля были переданы.
