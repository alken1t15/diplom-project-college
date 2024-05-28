import React, {useEffect, useState} from 'react';
import './MainPageStudent.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import GradeLine from "../../Components/GradeLine/GradeLine";
import Pagination, {IDataArrayItem} from "../../Components/Pagination/Pagination";
import LatenessItem, {ITardinessItem} from "../../Components/Lateness/LatenessItem";
import ScheduleItem from "../../Components/Schedule/ScheduleItem";
import FileUploader from "../../Components/FileUploader/FileUploader";
import {addNewCertificate, mainPageData} from "../../Http/MainPage";
import {$api} from "../../Http";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');
const MainPageStudent: React.FC = () => {

    let[currentPage, setCurrentPage] = useState(7);
    let [dateArray, setDateArray] = useState<IDataArrayItem[]>([
        {
            isActive: true,
            date: 'Янв',
            number: "01",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Фев',
            number: "02",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Март',
            number: "03",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Апр',
            number: "04",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Май',
            number: "05",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Июн',
            number: "06",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Сен',
            number: "09",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Окт',
            number: "10",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Нояб',
            number: "11",
            requestMonth: 1
        },
        {
            isActive: false,
            date: 'Дек',
            number: "12",
            requestMonth: 1
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
    let[schedule, setSchedule] = useState({date: '',
        nameOfDay: '',
        items: []})
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
    let[user, setUser] = useState({
            name: ' ',
            lastName: ' ',
            groupName: ' ',
            yearGroup: ' ',
    })
    let[file, setFile] = useState<any[]>([])

    function updateCurrentPage(value: any){
        setCurrentPage(value)
        mainPageData(value)
            .then(response=>{
                updateOmissions(response.data.omissions)
            })
            .catch(error=>{

            })
    }

    function updateUser(name: string, lastName: string, groupName: string, yearGroup: string){
        let obj = {
            name: name,
            lastName: lastName,
            groupName: groupName,
            yearGroup: yearGroup,

        };
        setUser(obj)
    }

    function updateOmissions(tardiness: any){

        let newTardiness = tardiness.days.map((el: any, index: any)=>{

            let newTardinessItems = el.tardiness.map((el1: any, index1: any)=>{
                let obj = {
                    text: el1.status,
                    type: el1.status === 'Отсутвует' ? 'red' : el1.status === 'С опозданием' ? 'yellow' : el1.status === 'Без опозданий' ? 'green' : el1.status === 'С справкой' ? 'cyan' : '',
                    subject: el1.nameObject
                }
                return obj;
            })

            let newTardItem = {
                date: el.date,
                nameOfDay: el.nameOfDay,
                tardiness: newTardinessItems
            }
            return newTardItem;
        })

        setTardinessItem(newTardiness)
    }

    function formatDate(dateString: string): string {
        const monthNames = [
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
        ];
        const date = new Date(dateString);
        const day = date.getDate();
        const month = monthNames[date.getMonth()];
        const year = date.getFullYear();
        return `${day} ${month} ${year}`;
    };

    useEffect(()=>{
        mainPageData()
            .then(response=>{


                updateUser(response.data.name, response.data.lastName, response.data.groupName, response.data.yearGroup)

                let dataArr: IDataArrayItem[]  = [];
                response.data.monthsStudy.forEach((el: any, index: any)=>{
                    let todayMonth = (new Date()).getMonth() + 1;
                    let obj: IDataArrayItem = {
                        isActive: todayMonth === el.requestMonth,
                        date: el.name.split(' ')[1],
                        number: el.name.split(' ')[0],
                        requestMonth: el.requestMonth,
                    };
                    dataArr.push(obj)

                })
                setDateArray(dataArr)

                let newGradeLine = response.data.evaluations.map((el:any, index:any)=>{
                    let obj = {
                        teacherName: el.nameTeacher,
                        subject: el.nameObject,
                        date: formatDate(el.dateEvaluation),
                        grade: el.ball <= 39 ? 2 : el.ball >= 40 && el.ball < 70 ? 3 : el.ball < 90 && el.ball >= 70 ? 4 : el.ball >= 90 ? 5 : 0
                    }
                    return obj;
                })
                setGradeLine(newGradeLine)

                let scheduleObj = {
                    date: response.data.planStudy.date,
                    nameOfDay: response.data.planStudy.nameOfWeek,
                    items: response.data.planStudy.subjects.map((el: any)=>{
                            return {
                                time: String(el.startStudy.split(':')[0]) + ':' + String(el.startStudy.split(':')[1]) + ' ' + String(el.endStudy.split(':')[0]) + ':' + String(el.endStudy.split(':')[1]),
                                subject: el.name
                            }
                        })

                }
                setSchedule(scheduleObj)

                updateOmissions(response.data.omissions)

                console.log(response.data.file)
                let newFile = [
                    {
                        name: '1',
                        file: response.data.file.file,

                    }
                ]
                console.log(response.data.file)
                setFile(newFile)

            })
            .catch(error=>{

            })



    }, [])

    useEffect(()=>{
        let currentMonth;
        for(let i = 0; i < dateArray.length; i++){
            if(dateArray[i].isActive){
                currentMonth = dateArray[i].requestMonth;
                break;
            }
            else{
                currentMonth = 1
            }
        }

    }, [dateArray])

    const sendCertif = async (files: File[]) => {
        const formData = new FormData();

        files.forEach((file) => {
            formData.append('file', file, file.name);
        });

        try {
            const response = await addNewCertificate(formData).then((response)=>{
                mainPageData()
                    .then(response=>{

                        updateUser(response.data.name, response.data.lastName, response.data.groupName, response.data.yearGroup)

                        let dataArr: IDataArrayItem[]  = [];
                        response.data.monthsStudy.forEach((el: any, index: any)=>{
                            let todayMonth = (new Date()).getMonth() + 1;
                            let obj: IDataArrayItem = {
                                isActive: todayMonth === el.requestMonth,
                                date: el.name.split(' ')[1],
                                number: el.name.split(' ')[0],
                                requestMonth: el.requestMonth,
                            };
                            dataArr.push(obj)

                        })
                        setDateArray(dataArr)

                        let newGradeLine = response.data.evaluations.map((el:any, index:any)=>{
                            let obj = {
                                teacherName: el.nameTeacher,
                                subject: el.nameObject,
                                date: formatDate(el.dateEvaluation),
                                grade: el.ball <= 39 ? 2 : el.ball >= 40 && el.ball < 70 ? 3 : el.ball < 90 && el.ball >= 70 ? 4 : el.ball >= 90 ? 5 : 0
                            }
                            return obj;
                        })
                        setGradeLine(newGradeLine)

                        let scheduleObj = {
                            date: response.data.planStudy.date,
                            nameOfDay: response.data.planStudy.nameOfWeek,
                            items: response.data.planStudy.subjects.map((el: any)=>{
                                return {
                                    time: String(el.startStudy.split(':')[0]) + ':' + String(el.startStudy.split(':')[1]) + ' ' + String(el.endStudy.split(':')[0]) + ':' + String(el.endStudy.split(':')[1]),
                                    subject: el.name
                                }
                            })

                        }
                        setSchedule(scheduleObj)

                        updateOmissions(response.data.omissions)

                        let newFile = [
                            {
                                name: '1',
                                file: response.data.file.file,

                            }
                        ]
                        console.log(response.data.file)
                        setFile(newFile)

                    })
                    .catch(error=>{

                    })
            }).catch((error)=>{

            })

        } catch (error) {
        }
    };



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
                            <InitialsImage initials={`${user.name.split('')[0]}${user.lastName.split('')[0]}`} width={70} height={70} fontSize={30} textColor="#fff" backgroundColor="#d9d9d9" />
                        </div>
                        <div className="block-left-header-personal-r">
                            <p className="block-left-header-personal-r__header">{user.lastName} {user.name}</p>
                            <p className="block-left-header-personal-r__column" style={{marginLeft: 0}}>
                                Год поступления:
                                <span className="block-left-header-personal-r__text">{user.yearGroup}</span>
                            </p>
                            <p className="block-left-header-personal-r__column">
                                Группа:
                                <span className="block-left-header-personal-r__text">{user.groupName}</span>
                            </p>
                        </div>
                    </div>
                    <div className="block-left-grades" >
                        <p className={'block-left__text'}>
                            <img src={gradeImg} alt="Info img"/>
                            Оценки полученные сегодня
                        </p>
                        {gradeLine.map((el, index)=>(
                                <GradeLine item={el} styles={{marginTop: 0}} teachersBlock={true} key={index}/>
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
                <ScheduleItem date={schedule.date} nameOfDay={schedule.nameOfDay} schedules={schedule.items}/>
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



                    <FileUploader onClick={sendCertif} multipart={false} items={file}/>



                    </div>
                </div>
            </div>
        </div>
    );
};

export default MainPageStudent;