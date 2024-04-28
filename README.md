Дипломная работа
Backend часть:
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
