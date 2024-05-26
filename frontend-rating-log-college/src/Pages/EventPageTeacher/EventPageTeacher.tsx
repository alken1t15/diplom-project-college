import React, {useEffect, useState} from 'react';
import './EventPageTeacher.scss';
import Calendar from "../../Components/Calendar/Calendar";
import EventItem from '../../Components/EventItem/EventItem';
import ToggleBtns, {IToggleBtnsItems} from "../../Components/ToggleBtns/ToggleBtns";
import TimeBlock from "../../Components/TimeBlock/TimeBlock";
import EventStudentItem, {IEventStudentItem} from "../../Components/EventStudentItem/EventStudentItem";
const calendarImg = require('../../assets/images/calendar.png');

export interface IEventItem{
    id: number;
    name: string;
    dateStart: string;
    dateEnd: string;
    group: string;
}


const EventPageTeacher: React.FC = () => {

    let[date, setDate] = useState<Date>()
    let[events, setEvents] = useState<IEventItem[]>([])
    let[activeGroup, setActiveGroup] = useState(1)
    let[group, setGroup] = useState<IToggleBtnsItems[]>([])
    let[activeSubject, setActiveSubject] = useState(1)
    let[subject, setSubject] = useState<IToggleBtnsItems[]>([])
    let[time, setTime] = useState("")
    let[currentEvent, setCurrentEvent] = useState('')
    let[curCount, setCurCount] = useState<number>()
    let[students, setStudent] = useState<IEventStudentItem[]>([])

    const getTime = () => {
        const currentDate = new Date();
        const hours = String(currentDate.getHours()).padStart(2, '0');
        const minutes = String(currentDate.getMinutes()).padStart(2, '0');
        const currentTime = `${hours}:${minutes}`;
        setTime(currentTime);
    }

    useEffect(()=>{
       let newArr = [
           {
               id: 1,
               name: 'Практика по Vue',
               dateStart: '15 сен',
               dateEnd: '24 сен',
               group: 'П-20-51б',
           },
           {
               id: 2,
               name: 'Практика по Vue',
               dateStart: '15 сен',
               dateEnd: '24 сен',
               group: 'П-20-51б',
           },
       ]
       setEvents(newArr)
        let newGroup = [
            {
                id: 1,
                name: 'П-19-49к',
                active: true
            },
            {
                id: 2,
                name: 'П-20-50б',
                active: false
            },
            {
                id: 3,
                name: 'П-21-51гб',
                active: false
            },
        ]
        setGroup(newGroup)
        let newSubject = [
            {
                id: 1,
                name: 'Веб-программирование',
                active: true
            },
            {
                id: 2,
                name: 'Веб дизайн',
                active: false
            },
        ]
        setSubject(newSubject)
        let newArrStud= [
            {
                id: 1,
                count: 2,
                name: 'Кораблев Максим Игоревич',
                grade: null,
            },
            {
                id: 2,
                count: 0,
                name: 'Кораблев Максим Игоревич',
                grade: '55',
            },
            {
                id: 3,
                count: 1,
                name: 'Кораблев Максим Игоревич',
                grade: '65',
            },
            {
                id: 4,
                count: 15,
                name: 'Кораблев Максим Игоревич',
                grade: '95',
            },
        ]
        setStudent(newArrStud)


        //http

        setCurrentEvent('Стандартный урок')
        setCurCount(2)
        ///

        getTime()
        const timeInterval = setInterval(() => {
            getTime()
        }, 10000);
        return () => {
            clearInterval(timeInterval)
        };
    },[])

    const updateDate = (value: Date) => {
        setDate(value);
    }

    const updateCurrentGroup = (id: number) => {
        let idActiveGroup = 0;
        let newArr = group.map((el: any)=>{
            el.active = el.id === id
            if(el.id === id){
                idActiveGroup= el.id
            }
            return el
        })
        setActiveGroup(idActiveGroup)
        setGroup(newArr)
    }

    const updateCurrentSubject = (id: number) => {
        let idActiveSubject = 0;
        let newArr = subject.map((el: any)=>{
            el.active = el.id === id
            if(el.id === id){
                idActiveSubject= el.id
            }
            return el
        })
        setActiveSubject(idActiveSubject)
        setSubject(newArr)
    }

    const updateCurrentStudents = (id: number, value: string) => {
        let newArr = students.map((el: any)=>{
            if(el.id === id){
                el.grade = value
            }
            return el
        })
        setStudent(newArr)
    }

    return (
        <div className={'main-page main-page-t'}>
            <div className={'block-left block-left-t block-left-t-e'}>
                <div className="block-left-header">
                    <p className={'block-left__text block-left__text-e'}>
                        <img src={calendarImg} alt="Info img" style={{marginRight: 11, width: 23, height: 26}}/>
                        Даты выставление оценек
                    </p>
                    <div className="block-left-header-personal">
                        <Calendar onChange={updateDate}/>
                    </div>
                    <p className={'block-left__text block-left__text-e'} style={{marginTop: 50}}>
                        События на текущий месяц
                    </p>
                    <div>
                        {events.length > 0 ? events.map((el: any, index: any)=>(
                            <EventItem id={el.id} name={el.name} group={el.group} dateStart={el.dateStart} dateEnd={el.dateEnd} key={index}/>
                        )) : ''}
                    </div>
                </div>


            </div>
            <div className={'block-middle block-middle-t block-middle-t-e'}>
                <p className={'block-middle__text block-middle__text-t block-middle__text-t-e'}>
                    Выставление оценок
                </p>

                {group.length > 0 ?  <ToggleBtns onClick={updateCurrentGroup} items={group}/> : ''}
                {subject.length > 0 ?  <ToggleBtns onClick={updateCurrentSubject} items={subject} style={{marginTop: 15}}/> : ''}
                <div className={`block-middle-t-e-bottom`}>
                    <TimeBlock time={time} style={{marginTop: 33}}/>
                    <p className="block-middle-t-e-bottom-event-text">События: <span>{currentEvent}</span></p>
                    <p className="block-middle-t-e-bottom-event-text" style={{marginTop: 17}}>Кол-во учеников: <span>{curCount}</span></p>

                </div>
                <div className="event-student-box">
                    {students.map((el: any, index: any)=>(
                        <EventStudentItem onChange={updateCurrentStudents} items={el} key={index}/>
                    ))}
                </div>

            </div>
        </div>
    );
};

export default EventPageTeacher;