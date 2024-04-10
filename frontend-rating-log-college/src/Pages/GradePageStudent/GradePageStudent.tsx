import React, {useEffect, useState} from 'react';
import './GradePageStudent.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import GradeLine from "../../Components/GradeLine/GradeLine";
import Pagination, {IDataArrayItem} from "../../Components/Pagination/Pagination";
import LatenessItem, {ITardinessItem} from "../../Components/Lateness/LatenessItem";
import ScheduleItem from "../../Components/Schedule/ScheduleItem";
import FileUploader from "../../Components/FileUploader/FileUploader";
import TextCarousel from "../../Components/TextCarousel/TextCarousel";
import TeachersBlock, {ITeachersItem} from "../../Components/TeachersBlock/TeachersBlock";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');
const GradePageStudent: React.FC = () => {

    let[currentPage, setCurrentPage] = useState(7);
    let[currentCourse, setCurrentCourse] = useState(1);
    let[currentQuarter , setCurrentQuarter] = useState(1);
    let[dateArray, setDateArray] = useState<IDataArrayItem[]>([
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
    let[courses, setCourses] = useState([
        {
            id: 1,
            active: true,
            text: '1 курс',
        },
        {
            id: 2,
            active: false,
            text: '2 курс',
        },
        {
            id: 3,
            active: false,
            text: '3 курс',
        },
        {
            id: 4,
            active: false,
            text: '4 курс',
        },
    ]);
    let[quarters , setQuarters] = useState([
        {
            id: 1,
            active: true,
            name: '1 четверть'
        },
        {
            id: 2,
            active: false,
            name: '2 четверть'
        },
        {
            id: 3,
            active: false,
            name: 'Ит.оценки'
        },
    ]);
    let[currentTable, setCurrentTable] = useState([
        {
            id: 1,
            items: ['Предметы\\Даты', '01 Пн', '02 Вт', '03 Ср', '04 Чт', '05 Пт', '08 Пн', '09 Пн', '10 Вт', '11 Ср', '12 Чт', '15 Пт', '16 Пн', '17 Чт', '18 Пт', '19 Пн', '21 Пн', '22 Вт', '23 Ср', '24 Чт', '25 Пт']
        },
        {
            id: 2,
            items: ['Веб-программирование', '89', '92', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '95', '40', '65', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '\u00A0', '\u00A0']
        },
    ])
    let[currentBody, setCurrentBody] = useState([{
        id: 2,
        items: ['Веб-программирование', ' ', ' ', '0', '0', '20', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0']
    },]);
    let[teachers, setTeacher] = useState<ITeachersItem[]>([
        {
            id: 1,
            name: 'Денис Валентинович',
            subject: 'Веб-программирования'
        },
        {
            id: 2,
            name: 'Марина Галимовна',
            subject: 'Философия'
        },
    ])

    function updateCurrentPage(value: any){
        setCurrentPage(value)
    }

    function updateActiveQuarter(id: any){
        let newArr = quarters.map((el, index)=>{
            el.active = el.id === id;
            return el;
        })
        setQuarters(newArr)
    }

    const handleCarouselChange = (index: number) => {
        const newItems = courses.map((item, i) => {

                item.active = i === index
            if(i === index){
                setCurrentCourse(prevState => {
                    return courses[i].id;
                })
            }
                return item
        });
        setCourses(newItems);
    };

    useEffect(() => {
        let arrBody = [...currentTable].slice(1);
        setCurrentBody(arrBody);

    }, [currentTable]);

    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-grade'}>


                <div className="block-middle-wrapper">
                    <div className="block-middle-tabs">
                        {quarters.map((el, index) => (
                            <button
                                onClick={(e) => {
                                    updateActiveQuarter(el.id)
                                }}
                                className={`block-middle-tabs__text 
                                ${el.active ? `block-middle-tabs__text-active` : ''}
                                `} key={el.id}

                            >{el.name}</button>
                        ))}
                    </div>
                    <div className="block-middle-top">
                        <TextCarousel items={courses} onChange={handleCarouselChange}/>
                    </div>
                    <Pagination items={dateArray} onChange={updateCurrentPage}
                                styles={{marginBottom: 15, marginTop: 23}}/>

                    <div className="block-middle-grades">
                        <div className="block-middle-grades-item">
                            <div className="block-middle-grades-item__red"></div>
                            <p className="block-middle-grades-item__text">0-40</p>
                        </div>
                        <div className="block-middle-grades-item">
                            <div className="block-middle-grades-item__yellow"></div>
                            <p className="block-middle-grades-item__text">40-70</p>
                        </div>
                        <div className="block-middle-grades-item">
                            <div className="block-middle-grades-item__dark-green"></div>
                            <p className="block-middle-grades-item__text">70-90</p>
                        </div>
                        <div className="block-middle-grades-item">
                            <div className="block-middle-grades-item__green"></div>
                            <p className="block-middle-grades-item__text">90-100</p>
                        </div>
                    </div>

                    <table className="my-table">
                        <thead>
                        <tr>
                            {currentTable[0].items.map((el, index)=>(
                                <th key={index} className={`${index === 0 ? 'first-column ' : 'column-text'}
                                 ${index === 1 ? 'break-item' : ''} `}>{el}</th>
                            ))}
                        </tr>
                        </thead>
                        <tbody>
                        {currentBody.map((el, index)=> (
                            <tr key={index}>
                                {el.items.map((childEl, chileIndex)=> (
                                    <td key={chileIndex} className={`${chileIndex === 0 ? 'first-column' : 'column-text'} 
                                       ${
                                        Number(childEl) >= 90 ? 'table-item-green' :
                                            Number(childEl) > 70 && Number(childEl) < 90 ? 'table-item-dark-green' :
                                                Number(childEl) > 40 && Number(childEl) < 70 ? 'table-item-yellow' : 
                                                    Number(childEl) <= 40 && Number(childEl) !== 0 ? 'table-item-red' :
                                                            chileIndex == 1 ? '' : ''


                                    }
                                    `}>{childEl}{chileIndex !== 0 ? '%' : ''}</td>
                                ))}

                            </tr>
                        ))}


                        </tbody>
                    </table>
                </div>


            </div>


            <div className={'block-right block-right-grade'}>
                <p className={'block-right__text'}>
                <img src={teachImg} alt="Info img"/>
                    Список учителей
                </p>
                {teachers.map((el, index)=>(
                    <TeachersBlock item={el} />
                ))}

            </div>
        </div>
    );
};

export default GradePageStudent;