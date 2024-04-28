import React, {useEffect, useState} from 'react';
import './MainPageTeacher.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import GradeLine from "../../Components/GradeLine/GradeLine";
import Pagination, {IDataArrayItem} from "../../Components/Pagination/Pagination";
import LatenessItem, {ITardinessItem} from "../../Components/Lateness/LatenessItem";
import ScheduleItem from "../../Components/Schedule/ScheduleItem";
import FileUploader from "../../Components/FileUploader/FileUploader";
import {mainPageData} from "../../Http/MainPage";
import doneImg from '../../assets/images/Check.svg';
import HomeworkBlock, {HomeworkItem} from "../../Components/HomeworkBlock/HomeworkBlock";
import TimeBlock, {ITimeBlock} from "../../Components/TimeBlock/TimeBlock";
import ScheduleTeacherBlock from "../../Components/ScheduleTeacherBlock/ScheduleTeacherBlock";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');

export interface ISchedule{
    groupName: string;
    time: string;
    subject: string;
    active?: boolean;
}

const MainPageTeacher: React.FC = () => {

    let[homeWorks, setHomeWorks] = useState<HomeworkItem[]>([
        {
            id: 1,
            active: false,
            name: 'Наименования задания',
            date: '15 Сен',
            expiresAt: '9 сен - 15 сен',
            teacher: 'Денис Валентинович Попов',
            subject: 'Веб-программирования',
            scholar: 'Беккожа Аян',
            dueDate: '10 сентября',
        },
        {
            id: 2,
            active: false,
            name: 'Наименования задания',
            date: '15 Сен',
            expiresAt: '9 сен - 15 сен',
            teacher: 'Денис Валентинович Попов',
            subject: 'Веб-программирования',
            scholar: 'Беккожа Аян',
            dueDate: '10 сентября',
        },

    ])
    let[dateAndTime, setDateAndTime] = useState({
        date: '11 Сен',
        nameOfTheDay: 'Вторник'
    })
    let[schedule, setSchedule] = useState<ISchedule[]>([
        {
            groupName: 'П-20-51б',
            time: '9:00',
            subject: 'Веб-программирование',
        },
        {
            groupName: 'ИС-12-23б',
            time: '10:30',
            subject: 'Веб-программирование',
        },
        {
            groupName: 'П-20-53к',
            time: '13:00',
            subject: 'Веб-программирование',
        },
    ])
    let[time, setTime] = useState("")
    let[presence, setPresence] = useState({
        group: 'П-20-23к',

    })

    useEffect(()=>{
        // mainPageData()
        //     .then(response=>{
        //         console.log(response.data)
        //     })
        //     .catch(error=>{
        //
        //     })
    }, [])

    function setActiveOnSchedule(){
        let curTimeSplit = time.split(':').map(Number);
        let curDateTime = new Date(0, 0, 0, curTimeSplit[0], curTimeSplit[1]);
        let newArr: ISchedule[] = [];
        let activeIndex = -1;

        for (let index = 0; index < schedule.length; index++) {
            let el = schedule[index];
            let itemTimeSplit = el.time.split(':').map(Number);
            let itemDateTime = new Date(0, 0, 0, itemTimeSplit[0], itemTimeSplit[1]);

            let lastItemTime = schedule[schedule.length - 1].time.split(':').map(Number);
            let lastItemDateTime = new Date(0, 0, 0, lastItemTime[0], lastItemTime[1]);
            lastItemDateTime.setHours(lastItemDateTime.getHours() + 1);
            lastItemDateTime.setMinutes(lastItemDateTime.getMinutes() + 30);

            if (index === schedule.length - 1 && curDateTime > lastItemDateTime) {
                activeIndex = -1;
                break;
            } else if (curDateTime >= itemDateTime) {
                activeIndex = index;
            } else {
                break;
            }
        }

        for (let index = 0; index < schedule.length; index++) {
            let el = schedule[index];
            el.active = index === activeIndex;
            newArr.push(el);
        }

        setSchedule(newArr);


    }



    useEffect(()=>{
        setActiveOnSchedule();
    }, [time])

    function getTime(){
        const currentDate = new Date();
        const hours = String(currentDate.getHours()).padStart(2, '0');
        const minutes = String(currentDate.getMinutes()).padStart(2, '0');
        const currentTime = `${hours}:${minutes}`;
        setTime(currentTime);
    }

    useEffect(() => {
        getTime()
        const timeInterval = setInterval(() => {
            getTime()
        }, 60000);

        return () => clearInterval(timeInterval);
    }, []);


    return (
        <div className={'main-page main-page-t'}>
            <div className={'block-left block-left-t'}>
                <div className="block-left-header">
                    <p className={'block-left__text'}>
                        <img src={infoImg} alt="Info img"/>
                        Личная информация
                    </p>
                    <div className="block-left-header-personal">
                        <div className="block-left-header-personal-l">
                            <InitialsImage initials="МК" width={70} height={70} fontSize={30} textColor="#fff" backgroundColor="#d9d9d9" />
                        </div>
                        <div className="block-left-header-personal-r">
                            <p className="block-left-header-personal-r__header">Кораблев Максим</p>
                            <p className="block-left-header-personal-r__column" style={{marginLeft: 0}}>
                                Год трудоустройства:
                                <span className="block-left-header-personal-r__text">2020</span>
                            </p>


                        </div>


                    </div>
                    <div className="block-left-done-task">
                        <div className="block-left-done-task-top">
                            <img className="block-left-done-task-top__img" src={doneImg} alt="Done tasks"/>
                            <p className="block-left-done-task-top__text">Выполненые задание</p>
                        </div>
                        <div className="block-left-done-task-box">
                            {homeWorks.map((el, index)=>(
                                <HomeworkBlock item={el} key={el.id} forTeacher={true}/>
                            ))}

                        </div>
                    </div>
                </div>


            </div>
            <div className={'block-middle block-middle-t'}>
                <p className={'block-middle__text block-middle__text-t'}>
                    Расписание
                </p>

                <TimeBlock date={dateAndTime.date} time={time} nameOfTheDay={dateAndTime.nameOfTheDay}/>

               <div className="schedule-box">
                   {schedule.map((el, index)=>
                       <ScheduleTeacherBlock groupName={el.groupName} time={el.time} subject={el.subject} curTime={time}
                                             key={index}
                                             nextItem={index !== schedule.length-1 ? schedule[index+1] : schedule[index]}
                                             prevItem={index !== 0 ? schedule[index-1] : schedule[index] }
                                             active={el.active}
                                             last={index === schedule.length - 1}
                       />
                   )}
               </div>

            </div>
            <div className={'block-right block-right-t'}>
                <p className={'block-right__text'}>
                    {presence.group}
                </p>


            </div>
        </div>
    );
};

export default MainPageTeacher;