import React, {useEffect, useState} from 'react';
import { ru } from 'date-fns/locale';
import {registerLocale} from "react-datepicker";
import './EventPageTeacher.scss';
import Calendar from "../../Components/Calendar/Calendar";
import EventItem from '../../Components/EventItem/EventItem';
import ToggleBtns, {IToggleBtnsItems} from "../../Components/ToggleBtns/ToggleBtns";
import TimeBlock from "../../Components/TimeBlock/TimeBlock";
import EventStudentItem, {IEventStudentItem} from "../../Components/EventStudentItem/EventStudentItem";
import {addGrade, getAllAboutGroupAndSubjects, getStudents} from "../../Http/HomeWorks";
const calendarImg = require('../../assets/images/calendar.png');


registerLocale('ru', ru);
export interface IEventItem{
    id: number;
    name: string;
    dateStart: string;
    dateEnd: string;
    group: string;
}

const EventPageTeacher: React.FC = () => {

    let[date, setDate] = useState<string>('')
    let[events, setEvents] = useState<IEventItem[]>([])
    let[activeGroup, setActiveGroup] = useState<number>()
    let[group, setGroup] = useState<IToggleBtnsItems[]>([])
    let[activeSubject, setActiveSubject] = useState<number>()
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

        getAllAboutGroupAndSubjects().then((response) => {
            let newArr = response.data.groupsStudyDTOS.map((el: any) => {
                let newObj = {
                    active: false,
                    id: el.id,
                    name: el.name
                }
                return newObj;
            });
            setGroup(newArr);

            let newSubjectArr = response.data.subjectStudyDTOS.map((el: any) => {
                let newObj = {
                    active: false,
                    id: el.id,
                    name: el.name
                }
                return newObj;
            });
            setSubject(newSubjectArr);
        }).catch((error) => { });

        getTime()
        const timeInterval = setInterval(() => {
            getTime()
        }, 10000);
        return () => {
            clearInterval(timeInterval)
        };
    },[])

    useEffect(()=>{
        if(activeGroup && activeSubject){
            getStudents(activeSubject, activeGroup).then((response)=>{
                setStudent(response.data.students)
                setCurCount(response.data.count)
            }).catch((error)=>{})
        }
    }, [activeGroup, activeSubject])

    const updateDate = (value: Date) => {
        setDate(value.toLocaleDateString());
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
                el.bull = value
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
                    {/*<p className={'block-left__text block-left__text-e'} style={{marginTop: 50}}>*/}
                    {/*    События на текущий месяц*/}
                    {/*</p>*/}
                    {/*<div>*/}
                    {/*    {events.length > 0 ? events.map((el: any, index: any)=>(*/}
                    {/*        <EventItem id={el.id} name={el.name} group={el.group} dateStart={el.dateStart} dateEnd={el.dateEnd} key={index}/>*/}
                    {/*    )) : ''}*/}
                    {/*</div>*/}
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
                    {/*<p className="block-middle-t-e-bottom-event-text">События: <span>{currentEvent}</span></p>*/}
                    <p className="block-middle-t-e-bottom-event-text" style={{marginTop: 17}}>Кол-во учеников: <span>{curCount}</span></p>

                </div>
                <div className="event-student-box">
                    {students.map((el: any, index: any)=>(
                        <EventStudentItem onChange={updateCurrentStudents} items={el} key={index}/>
                    ))}
                </div>
                <button className={'upl-bn upl-bn-hw'} onClick={(e)=>{

                    if (activeSubject && activeGroup && date) {
                        addGrade(activeSubject, date, students)
                            .then((response) => {
                                console.log('fff');
                            })
                            .catch((error) => {
                                console.error('Error:', error);
                            });
                    }

                }}>Выставить </button>
            </div>
        </div>
    );
};

export default EventPageTeacher;