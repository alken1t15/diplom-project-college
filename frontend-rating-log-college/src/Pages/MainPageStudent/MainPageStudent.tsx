import React, {useState} from 'react';
import './MainPageStudent.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import GradeLine from "../../Components/GradeLine/GradeLine";
import Pagination, {IDataArrayItem} from "../../Components/Pagination/Pagination";
import LatenessItem, {ITardinessItem} from "../../Components/Lateness/LatenessItem";
import ScheduleItem from "../../Components/Schedule/ScheduleItem";
import FileUploader from "../../Components/FileUploader/FileUploader";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');
const MainPageStudent: React.FC = () => {

    let[currentPage, setCurrentPage] = useState(7);
    let [dateArray, setDateArray] = useState<IDataArrayItem[]>([
        {
            id: 1,
            isActive: true,
            date: 'Янв',
            number: "01"
        },
        {
            id: 2,
            isActive: false,
            date: 'Фев',
            number: "02"
        },
        {
            id: 3,
            isActive: false,
            date: 'Март',
            number: "03"
        },
        {
            id: 4,
            isActive: false,
            date: 'Апр',
            number: "04"
        },
        {
            id: 5,
            isActive: false,
            date: 'Май',
            number: "05"
        },
        {
            id: 6,
            isActive: false,
            date: 'Июн',
            number: "06"
        },
        {
            id: 9,
            isActive: false,
            date: 'Сен',
            number: "09"
        },
        {
            id: 10,
            isActive: false,
            date: 'Окт',
            number: "10"
        },
        {
            id: 11,
            isActive: false,
            date: 'Нояб',
            number: "11"
        },
        {
            id: 12,
            isActive: false,
            date: 'Дек',
            number: "12"
        },
    ])
    let[tardinessItem, setTardinessItem] = useState<ITardinessItem[]>([
            {
                date: "2 сентября",
                nameOfDay: "Вторник",
                tardiness: [ {
                    id: 1,
                    type: 'yellow',
                    text: 'С опозданием',
                    subject: 'Веб-программирование'
                },
                    {
                        id: 2,
                        type: 'green',
                        text: 'Без опозданий',
                        subject: 'Основа право'
                    },
                    {
                        id: 3,
                        type: 'red',
                        text: 'Отсутвует',
                        subject: 'Комп сети'
                    },
                    {
                        id: 4,
                        type: 'cyan',
                        text: 'С справкой',
                        subject: 'Комп сети'
                    }]
            } ,
            {
                date: "2 сентября",
                nameOfDay: "Среда",
                tardiness: [ {
                    id: 1,
                    type: 'yellow',
                    text: 'С опозданием',
                    subject: 'Веб-программирование'
                },
                    {
                        id: 2,
                        type: 'yellow',
                        text: 'Без опозданий',
                        subject: 'Основа право'
                    },
                    {
                        id: 3,
                        type: 'red',
                        text: 'Отсутвует',
                        subject: 'Комп сети'
                    },
                    {
                        id: 4,
                        type: 'cyan',
                        text: 'С справкой',
                        subject: 'Комп сети'
                    }]
            },
            {
                date: "2 сентября",
                nameOfDay: "Среда",
                tardiness: [ {
                    id: 1,
                    type: 'yellow',
                    text: 'С опозданием',
                    subject: 'Веб-программирование'
                },
                    {
                        id: 2,
                        type: 'yellow',
                        text: 'Без опозданий',
                        subject: 'Основа право'
                    },
                    {
                        id: 3,
                        type: 'red',
                        text: 'Отсутвует',
                        subject: 'Комп сети'
                    },
                    {
                        id: 4,
                        type: 'cyan',
                        text: 'С справкой',
                        subject: 'Комп сети'
                    }]
            }
        ])
    let[schedule, setSchedule] = useState({
        date: '3 сентября',
        nameOfDay: 'Среда',
        schedules: [
            {
                id: 1,
                time: '9:00 10:30',
                subject: 'Веб-программирование',

            },  {
                id: 2,
                time: '9:00 10:30',
                subject: 'Веб-программирование',

            },
        ]
    })
    let[active, setIsActive] = useState(false)
    let[gradeLine, setGradeLine] = useState([
        {
            teacherName: 'Денис Валентинович',
            subject: 'Веб-программирования',
            date: '3 сентября 2023',
            grade: 5
        },
        {
            teacherName: 'Денис Валентинович',
            subject: 'Веб-программирования',
            date: '3 сентября 2023',
            grade: 4
        },
        {
            teacherName: 'Денис Валентинович',
            subject: 'Веб-программирования',
            date: '3 сентября 2023',
            grade: 3
        },
        {
            teacherName: 'Денис Валентинович',
            subject: 'Веб-программирования',
            date: '3 сентября 2023',
            grade: 2
        },
        ]);

    function updateCurrentPage(value: any){
        setCurrentPage(value)
    }


    return (
        <div className={'main-page'}>
            <div className={'block-left'}>
                <div className="block-left-header">
                    <p className={'block-left__text'}>
                        <img src={infoImg} alt="Info img"/>
                        Личная информация
                    </p>
                    <div className="block-left-header-personal">
                        <div className="block-left-header-personal-l">
                            <InitialsImage initials="МК" width={100} height={100} fontSize={48} textColor="#fff" backgroundColor="#d9d9d9" />
                        </div>
                        <div className="block-left-header-personal-r">
                            <p className="block-left-header-personal-r__header">Кораблев Максим</p>
                            <p className="block-left-header-personal-r__column" style={{marginLeft: 0}}>
                                Год поступления:
                                <span className="block-left-header-personal-r__text">2020</span>
                            </p>
                            <p className="block-left-header-personal-r__column">
                                Группа:
                                <span className="block-left-header-personal-r__text">П-20-51б</span>
                            </p>
                        </div>
                    </div>
                    <div className="block-left-grades" >
                        <p className={'block-left__text'}>
                            <img src={gradeImg} alt="Info img"/>
                            Оценки полученные сегодня
                        </p>
                        {gradeLine.map((el, index)=>(
                            <GradeLine item={el} styles={{marginTop: 0}} teachersBlock={true}/>
                        ))}

                    </div>
                </div>


            </div>
            <div className={'block-middle'}>
                <p className={'block-middle__text'}>
                    <img src={houseImg} alt="Info img"/>
                    Посещяемость
                </p>
                <Pagination items={dateArray} onChange={updateCurrentPage} styles={{marginBottom: 15}}/>
                <div className="lateness-block">
                    {tardinessItem.map((el, index)=>(
                        <LatenessItem key={index} styles={{marginTop: 30}} tardiness={el.tardiness} date={el.date} nameOfDay={el.nameOfDay}/>
                    ))}
                </div>

            </div>
            <div className={'block-right'}>
                <p className={'block-right__text'}>
                    <img src={teachImg} alt="Info img"/>
                    Расписание на сегодня
                </p>
                <ScheduleItem date={schedule.date} nameOfDay={schedule.nameOfDay} schedules={schedule.schedules}/>
                <button className="block-right__button" onClick={
                    (e) => {
                        setIsActive(true)
                    }
                }>

                    <img src={infoImg} alt="Information img"/>
                    Сообщить об отсутвия занятие
                </button>
                <div className={`image-block ${active ? 'image-block-active' : ''} `}>
                    <div className="image-block-top">
                        <p className={'image-block-top__text'}>
                            <button onClick={(e) => {
                                setIsActive(false)
                            }} className="image-block__button-close">
                                <img src={backImg} alt="Back img" className={`back-img`}/>
                            </button>

                            <img src={infoImg} alt="Info img" className={`image-block-top__img`}/>
                            Сообщить об отсуствии
                        </p>



                    <FileUploader/>



                    </div>
                </div>
            </div>
        </div>
    );
};

export default MainPageStudent;