import React, {useEffect, useState} from 'react';
import './MainPageTeacher.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import {mainPageTeacherData, mainPageTeacherUpdateData, mainPageTeacherUpdateOmission} from "../../Http/MainPage";
import doneImg from '../../assets/images/Check.svg';
import HomeworkBlock, {HomeworkItem} from "../../Components/HomeworkBlock/HomeworkBlock";
import TimeBlock from "../../Components/TimeBlock/TimeBlock";
import ScheduleTeacherBlock from "../../Components/ScheduleTeacherBlock/ScheduleTeacherBlock";
import {parse, addMinutes, isWithinInterval} from "date-fns";
import Slider from "../../Components/Slider/Slider";
import StudentWithoutCertificate, {
    IStudentWithoutCertificateItem
} from "../../Components/StudentWithoutCertificate/StudentWithoutCertificate";
import Spinner from "../../Components/Spinner/Spinner";
import {notify, Toasty} from "../../Components/Toasty/Toasty";
import {useTranslation} from "react-i18next";


const infoImg = require('../../assets/images/InformationImgg.png');

export interface ISchedule{
    groupName: string;
    time: string;
    subject: string;
    active?: boolean;
}

const MainPageTeacher: React.FC = () => {

    let[homeWorks, setHomeWorks] = useState<HomeworkItem[]>([]);
    let[schedule, setSchedule] = useState<ISchedule[]>([]);
    let[time, setTime] = useState("");
    let[presence, setPresence] = useState('');
    let[userName, setUserName] = useState('');
    let[userYEar, setUserYear] = useState('');
    const { t } = useTranslation();
    let[students, setStudents] = useState<IStudentWithoutCertificateItem[]>([]);
    let[slider, setSlider] =useState([
        {
          id: 1,
          name: `${t('check')}`,
          active: true,
        },
        {
            id: 2,
            name: `${t('enquiry')}`,
            active: false,
        },
    ]);
    let[curSlider, setCurSlider] = useState(1);
    let[activeGroup, setActiveGroup] = useState(0);
    let[curSubject, setCurSubject] = useState<string>('');
    let[curCouple, setCurCouple] = useState<number>(0);
    let[loading, setLoading] = useState(true)

    useEffect(()=>{
    },[curSlider])

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

    const getTime = () => {
        const currentDate = new Date();
        const hours = String(currentDate.getHours()).padStart(2, '0');
        const minutes = String(currentDate.getMinutes()).padStart(2, '0');
        const currentTime = `${hours}:${minutes}`;
        setTime(currentTime);
    }

    useEffect(()=>{
        if(curCouple){
            setCurrentStudent()
        }

    },[curSlider, activeGroup])

    function getActiveGroup(graphGroupsForStudy: any[]) {
        const currentTime = new Date();

        for (const group of graphGroupsForStudy) {
            const timeStart = new Date();
            const [hours, minutes, seconds] = group.timeStart.split(':').map(Number);
            timeStart.setHours(hours, minutes, seconds);

            const timeEnd = new Date(timeStart);
            timeEnd.setMinutes(timeEnd.getMinutes() + 90);

            if (currentTime >= timeStart && currentTime <= timeEnd) {
                return group;
            }
        }

        return null;
    }

    function setCurrentStudent(){

        mainPageTeacherUpdateData(activeGroup, curSlider !== 1)
            .then(response=>{

                let newArr = response.data.currentOmissionStudents.map((el: any)=>{
                    let newObj={
                        name: el.name,
                        count: el.count,
                        status: false,
                        idCertificate: el.idCertificate
                    }
                    return newObj

                })


                const activeGroup = getActiveGroup(response.data.graphGroupsForStudy);
                setActiveGroup(activeGroup)
                setStudents(newArr)



            })
            .catch(error=>{

            })


    }



    useEffect(() => {

        mainPageTeacherData()
            .then(response=>{
                setUserName(response.data.teacher.name)
                setUserYear(response.data.teacher.yearWork)
                let newSchedule = response.data.graphGroupsForStudy.map((el: any, index: any)=>{
                    let newObj = {
                        groupName: el.name,
                        time: el.timeStart.slice(0, 5),
                        subject: el.nameSubject,
                    }
                    let time = parse(el.timeStart.slice(0, 5), 'HH:mm', new Date());
                    let currentTime = new Date();
                    let endTime = addMinutes(time, 90);
                    console.log(el)
                    if(isWithinInterval(currentTime, { start: time, end: endTime })){
                        setActiveGroup(el.idGroup)
                        setPresence(el.name)
                        setCurSubject(el.nameSubject)
                        setCurCouple(index)
                        setCurrentStudent()
                    }



                    return newObj;
                })
                setSchedule(newSchedule)
                setTimeout(() => setLoading(false), 700);

            })
            .catch(error=>{

            })

        getTime()

        const timeStudentInterval = setInterval(() => {
            if(curCouple){
                setCurrentStudent()
            }
        }, 10000);

        const timeInterval = setInterval(() => {
            getTime()
        }, 10000);

        return () => {
            clearInterval(timeInterval)
            clearInterval(timeStudentInterval)
        };

    }, []);

    function setSliderItems(id: number){
        let newArr = slider.map((el1: any)=>{
            el1.active = el1.id === id
            return el1;
        })
        setSlider(newArr)
        setCurSlider(id)
    }

    const updateStudentOmissions = (id: number, value: boolean) => {
        let newArr = students.map((el:any)=>{
            if(el.id === id){
                el.active = value;
            }

            return el;
        })
        setStudents(newArr)
        mainPageTeacherUpdateOmission(activeGroup, id, curSubject, value, curCouple).then((response)=>{
            mainPageTeacherUpdateData(activeGroup, curSlider !== 1)
                .then(response=>{

                    let newArr = response.data.currentOmissionStudents.map((el: any)=>{
                        let newObj={
                            name: el.name,
                            count: el.count,
                            status: false,
                            idCertificate: el.idCertificate
                        }
                        return newObj

                    })


                    const activeGroup = getActiveGroup(response.data.graphGroupsForStudy);
                    setActiveGroup(activeGroup)
                    setStudents(newArr)



                })
                .catch(error=>{

                })
            notify(`${t('tardinessSuccess')}`, 'success')
        }).catch((error)=>{
            notify(`${t('tardinessError')}`, 'error')
        })

    }

    return (
        <div className={'main-page main-page-t'}>
            <div className={'block-left block-left-t'}>
                <div className="block-left-header">
                    <p className={'block-left__text'}>
                        <img src={infoImg} alt="Info img"/>
                        {t('personInfo')}
                    </p>
                    <div className="block-left-header-personal">
                        <div className="block-left-header-personal-l">
                            <InitialsImage initials={(userName ? userName.split(' ')[0][0]: '') + (userName ? userName.split(' ')[1][0] : '')}  width={70} height={70} fontSize={30} textColor="#fff" backgroundColor="#d9d9d9" />
                        </div>
                        <div className="block-left-header-personal-r">
                            <p className="block-left-header-personal-r__header">{userName}</p>
                            <p className="block-left-header-personal-r__column" style={{marginLeft: 0}}>
                                {t('yearWork')}:
                                <span className="block-left-header-personal-r__text">{userYEar}</span>
                            </p>


                        </div>


                    </div>
                    <div className="block-left-done-task">
                        <div className="block-left-done-task-top">
                            <img className="block-left-done-task-top__img" src={doneImg} alt="Done tasks"/>
                            <p className="block-left-done-task-top__text">{t('completedTasks')}</p>
                        </div>
                        <div className="block-left-done-task-box">
                            {homeWorks.map((el, index)=>(
                                <HomeworkBlock item={el} key={index} forTeacher={true}/>
                            ))}

                        </div>
                    </div>
                </div>


            </div>
            <div className={'block-middle block-middle-t'}>
                <p className={'block-middle__text block-middle__text-t'}>
                    {t('schedule')}
                </p>
                <TimeBlock time={time}/>
               <div className="schedule-box">
                   {schedule.length > 0 ? schedule.map((el, index)=>
                       <ScheduleTeacherBlock groupName={el.groupName} time={el.time} subject={el.subject} curTime={time}
                                             key={index}
                                             nextItem={index !== schedule.length-1 ? schedule[index+1] : schedule[index]}
                                             prevItem={index !== 0 ? schedule[index-1] : schedule[index] }
                                             active={el.active}
                                             last={index === schedule.length - 1}
                       />
                   ) : ''}
               </div>

            </div>
            <div className={'block-right block-right-t'}>
                <p className={'block-right__text'}>
                    {presence ? presence : `${t('noActiveLesson')}`}
                </p>
                <div>
                    <Slider items={slider} onChange={setSliderItems}/>
                    <div style={{marginTop: 25}}>
                        <p className="total-student">{t('totalStudents')}: <span>{students.length}</span></p>
                        {students.length > 0 ? students.map((el: any, index: any)=>(
                            <StudentWithoutCertificate onChange={updateStudentOmissions} items={el} key={index}/>
                        )) : ''}
                    </div>
                </div>

            </div>
            <Spinner loading={loading} />
            <Toasty/>
        </div>
    );
};

export default MainPageTeacher;