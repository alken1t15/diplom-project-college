import React, {useEffect, useState} from 'react';
import './HomeworkPageStudent.scss';
import HomeworkBlock, {HomeworkItem} from "../../Components/HomeworkBlock/HomeworkBlock";
import TeachersBlock from "../../Components/TeachersBlock/TeachersBlock";

const fileImg = require('../../assets/images/PDF.png');


const HomeworkPageStudent: React.FC = () => {

    let[homeWork, setHomeWork] = useState<HomeworkItem[]>([
        {
            id: 1,
            active: true,
            name: 'Наименования задания',
            date: '15 Сен',
            expiresAt: '9 сен - 15 сен',
            teacher: 'Денис Валентинович Попов',
            subject: 'Веб-программирования'
        },
        {
            id: 2,
            active: false,
            name: 'Наименования задания',
            date: '10 Сен',
            expiresAt: '9 сен - 10 сен',
            teacher: 'Марина Галимовна',
            subject: 'Философия'
        },

    ])
    let[currentCourse, setCurrentCourse] = useState<HomeworkItem>(homeWork[0])
    let[currentHomework, setCurrentHomework] = useState({
        id: 1,
        name: 'Наименования задания',
        expires: '9 сентября - 15 сентября',
        teacher: {
            id: 1,
            name: 'Денис Валентинович',
            subject: 'Веб-программирования'
        }

    })
    function setActiveHomeWork(id: number){
        let curArr = [...homeWork];
        let newArr = curArr.map((el, index)=>{
            el.active = el.id === id;
            return el;
        })
        setHomeWork(newArr)
    }


    return (
        <div className={'main-page main-page-courses'}>
            <div className={'block-left block-left-courses block-left-homework'}>
                <div className="block-left-header block-left-header-p">
                    <div className="block-left-main block-left-header-homework">
                        <p className={'block-left__text block-left__text-c block-left__text-c-hw'}>
                            Задания
                        </p>
                    </div>
                    <div className="homeworks-block">
                        {[...homeWork].map((el, index) => (
                            <HomeworkBlock item={el} onClick={setActiveHomeWork} key={el.id}/>
                        ))}
                    </div>


                </div>


            </div>
            <div className={'block-middle block-middle-full block-middle-full-t-0 block-middle-full-t-hw'}>
                <div className={'block-middle__text'}>
                    <div className={`courses-block courses-block-l`}>
                        <p className={`courses-block__title courses-block__title-l block-left__text-hw`}>{currentCourse.name}</p>
                    </div>
                </div>
                <div className="block-middle-info">
                    {/*<p className="block-middle-info__expires"><span>Срок задание: </span>{el.name}</p>*/}
                    <TeachersBlock item={currentHomework.teacher}/>
                </div>

                <div className="file-box">

                </div>

            </div>
        </div>
    );
};

export default HomeworkPageStudent;