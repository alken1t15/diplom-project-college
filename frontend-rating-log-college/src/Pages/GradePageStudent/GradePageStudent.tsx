import React, {useEffect, useState} from 'react';
import './GradePageStudent.scss';
import Pagination, {IDataArrayItem} from "../../Components/Pagination/Pagination";
import TextCarousel from "../../Components/TextCarousel/TextCarousel";
import TeachersBlock, {ITeachersItem} from "../../Components/TeachersBlock/TeachersBlock";
import {getTotalGrades, gradePageData} from "../../Http/GradePage";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');

interface ICourses {
    id: number;
    active: boolean;
    text: string;
}

interface ISem {
    id: number;
    active: boolean;
    name: string;
}

interface IGradeTable{
    items: string[]
}

const GradePageStudent: React.FC = () => {

    let[currentMonth, setCurrentMonth] = useState(9);
    let[currentCourse, setCurrentCourse] = useState(1);
    let[currentQuarter , setCurrentQuarter] = useState(1);
    let[dateArray, setDateArray] = useState<IDataArrayItem[]>([])
    let[courses, setCourses] = useState<ICourses[]>([]);
    let[quarters , setQuarters] = useState<ISem[]>([
        {
            id: 1,
            active: true,
            name: `1 семестр`,
        },
        {
            id: 2,
            active: false,
            name: `2 семестр`,
        },
        {
            id: 3,
            active: false,
            name: `Итоговые оценки`,
        },
    ]);
    let[currentTable, setCurrentTable] = useState<any[]>([])
    let[currentBody, setCurrentBody] = useState<string[]>([]);
    let[teachers, setTeacher] = useState<ITeachersItem[]>([])
    let[thead, setThead] = useState<string[]>([])

    function updateCurrentMonth(value: any){
        setCurrentMonth(value)
    }

    function updateActiveQuarter(id: any){
        let newArr = quarters.map((el, index)=>{
            el.active = el.id === id;
            return el;
        })
        setQuarters(newArr)
        setCurrentQuarter(id)
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

    function setNewTeacher(array: ITeachersItem[]){
        let newTeachArr = array.map((el: any)=>{
            let newObj = {
                name: el.teacherFirstName + " " + el.teacherSecondName,
                subject: el.subjectName
            }
            return newObj;
        })
        setTeacher(newTeachArr)
    }

    useEffect(() => {
        if(currentTable.length > 0){
            let newCurBody = [...currentTable].slice(1).map((el: any)=>{
                if(el === null){
                    return '\u00A0'
                }
                return el
            })
            setCurrentBody(newCurBody);

        }



    }, [currentTable]);

    useEffect(()=>{
        if(currentQuarter === 3){
            getTotalGrades().then((response: any)=>{
                setCurrentTable(response.data.resultArray)
                let newThead: string[] = []
                response.data.resultArray[0].forEach((el: any, index: any)=>{
                    if(el !== null){
                        newThead.push(el)
                    }
                })
                setThead(newThead)

                let newTeacher: any[] = []
                response.data.teacher.forEach((el: any)=>{
                    let newObj = {
                        name: `${el.teacherSecondName} ${el.teacherFirstName}`,
                        subject: el.subjectName
                    }
                    newTeacher.push(newObj)
                })
                setTeacher(newTeacher)
            }).catch((error: any)=>{

            })
        }
        else{
            gradePageData(currentCourse,currentQuarter,currentMonth).then((response)=>{

                let dataArr: IDataArrayItem[]  = [];
                response.data.months.forEach((el: any, index: any)=>{
                    let obj: IDataArrayItem = {
                        isActive: currentMonth === el.requestMonth,
                        date: el.name.split(' ')[1],
                        number: el.name.split(' ')[0],
                        requestMonth: el.requestMonth,
                    };
                    dataArr.push(obj)

                })
                setDateArray(dataArr)

                let arrCourses = [];
                for(let i = 1; i !== response.data.totalCourse+1; i++){
                    let obj = {
                        id: i,
                        active: i === 1,
                        text: `${i} курс`,
                    }
                    arrCourses.push(obj)

                }
                setCourses(arrCourses)
                setCurrentTable(response.data.evaluations)
                setNewTeacher(response.data.teachers)

            })
                .catch((error)=>{

                })
        }





    }, [currentMonth, currentQuarter, currentCourse])


    useEffect(()=>{
        gradePageData(currentCourse,currentQuarter,currentMonth).then((response)=>{

            let dataArr: IDataArrayItem[]  = [];
            response.data.months.forEach((el: any, index: any)=>{
                let obj: IDataArrayItem = {
                    isActive: index === 0,
                    date: el.name.split(' ')[1],
                    number: el.name.split(' ')[0],
                    requestMonth: el.requestMonth,
                };
                dataArr.push(obj)

            })
            setDateArray(dataArr)


            let arrCourses = [];
            for(let i = 1; i !== response.data.totalCourse+1; i++){
                let obj = {
                    id: i,
                    active: i === 1,
                    text: `${i} курс`,
                }
                arrCourses.push(obj)

            }

            setCourses(arrCourses)



            setCurrentTable(response.data.evaluations)
            setNewTeacher(response.data.teachers)
        })
            .catch((error)=>{

            })


    },[])





    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-grade'}>


                <div className="block-middle-wrapper">
                    {quarters.length > 0 ? <div className="block-middle-tabs">
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
                    </div> : ''}
                    <div className="block-middle-top">
                        {currentQuarter === 3 ? "" :

                           <TextCarousel items={courses} onChange={handleCarouselChange}/>
                        }

                    </div>
                    {currentQuarter === 3 ? "" :

                        <Pagination isGrades={true} items={dateArray} onChange={updateCurrentMonth}
                                    styles={{marginBottom: 15, marginTop: 23}}/>
                    }

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

                    {currentQuarter === 3 ?
                        <table className={`my-table ${currentQuarter === 3 ? 'my-table-itog' : ''}`}>
                            <thead>
                            <tr>
                                {thead ?  thead.map((el:any, index: any)=>(
                                    <th colSpan={index !== 0 ? 2 : 1} key={index} className={`${index === 0 ? 'first-column ' : 'column-text'}
                                 ${index === 1 ? 'break-item' : ''} `}
                                    style={{display: el === null ? 'hidden' : ''}}>
                                        {el}
                                    </th>
                                )) : ''}
                            </tr>
                            <tr>
                                {currentTable && currentTable[1] ?  currentTable[1].map((el:any, index: any)=>(
                                    <th key={index} className={`${index === 0 ? 'first-column ' : 'column-text'}
                                 ${index === 1 ? 'break-item' : ''} `}>
                                        {el}
                                    </th>
                                )) : ''}
                            </tr>
                            </thead>
                            <tbody>
                            {currentBody && currentBody.length > 0 ? currentBody.map((el: any, index)=> (
                                <tr key={index}>
                                    {el.map((childEl: any, chileIndex: any)=> (

                                        <td key={chileIndex} className={`${chileIndex === 0 ? 'first-column' : 'column-text'}
                                       ${
                                            Number(childEl) >= 90 ? 'table-item-green' :
                                                Number(childEl) > 70 && Number(childEl) < 90 ? 'table-item-dark-green' :
                                                    Number(childEl) > 40 && Number(childEl) < 70 ? 'table-item-yellow' :
                                                        Number(childEl) <= 40 && Number(childEl) !== 0 ? 'table-item-red' :
                                                            chileIndex == 1 ? '' : ''


                                        }
                                    `}>{childEl !== '-' ? childEl : ''}{chileIndex !== 0 && childEl !== '-' ? '%' : ''}</td>
                                    ))}

                                </tr>
                            )) : ''}


                            </tbody>
                        </table>

                    :

                        <table className={`my-table ${currentQuarter === 3 ? 'my-table-itog' : ''}`}>
                            <thead>
                            <tr>


                                {currentTable && currentTable[0] ?  currentTable[0].map((el:any, index: any)=>(
                                    <th key={index} className={`${index === 0 ? 'first-column ' : 'column-text'}
                                 ${index === 1 ? 'break-item' : ''} `}>{el.length === 3
                                        ? `${el[0]} ${el.slice(1)}`
                                        : el.length === 4
                                            ? `${el.slice(0, 2)} ${el.slice(2)}`
                                            : el}</th>
                                )) : ''}
                            </tr>
                            </thead>
                            <tbody>
                            {currentBody && currentBody.length > 0 ? currentBody.map((el: any, index)=> (
                                <tr key={index}>
                                    {el.map((childEl: any, chileIndex: any)=> (

                                        <td key={chileIndex} className={`${chileIndex === 0 ? 'first-column' : 'column-text'}
                                       ${
                                            Number(childEl) >= 90 ? 'table-item-green' :
                                                Number(childEl) > 70 && Number(childEl) < 90 ? 'table-item-dark-green' :
                                                    Number(childEl) > 40 && Number(childEl) < 70 ? 'table-item-yellow' :
                                                        Number(childEl) <= 40 && Number(childEl) !== 0 ? 'table-item-red' :
                                                            chileIndex == 1 ? '' : ''


                                        }
                                    `}>{childEl ? childEl : ''}{chileIndex !== 0 && childEl? '%' : ''}</td>
                                    ))}

                                </tr>
                            )) : ''}


                            </tbody>
                        </table>
                    }
                </div>


            </div>


            <div className={'block-right block-right-grade'}>
                <p className={'block-right__text'}>
                <img src={teachImg} alt="Info img"/>
                    Список учителей
                </p>
                {teachers.length > 0 ? teachers.map((el, index)=>(
                    <TeachersBlock item={el} key={index}/>
                )) : ''}

            </div>
        </div>
    );
};

export default GradePageStudent;