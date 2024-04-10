import React, {useEffect, useState} from 'react';
import './HomeworkPageStudent.scss';

const fileImg = require('../../assets/images/PDF.png');

interface ICoursesBlock{
    id: number,
    name: string,
    date: string,
    active?: boolean
}
const HomeworkPageStudent: React.FC = () => {

    let[courses, setCourses] = useState<ICoursesBlock[]>([
        {
            id: 1,
            name: '1 Курс',
            date: 'Год: 2020',
            active: true
        },
        {
            id: 2,
            name: '2 Курс',
            date: 'Год: 2022',
            active: false
        },
        {
            id: 3,
            name: '3 Курс',
            date: 'Год: 2023',
            active: false
        },
        {
            id: 4,
            name: '4 Курс',
            date: 'Год: 2024',
            active: false
        },
    ])
    let[currentCourse, setCurrentCourse] = useState<ICoursesBlock>(
        {
            id: 1,
            name: '1 Курс',
            date: 'Год: 2020'
        }
    )
    function setActiveCourse(id: number){

    }


    return (
        <div className={'main-page main-page-courses'}>
            <div className={'block-left block-left-courses'}>
                <div className="block-left-header">
                    <div className="block-left-main">
                        <p className={'block-left__text block-left__text-c'}>
                            Задания
                        </p>
                    </div>
                    {[...courses].map((el, index) => (
                        <button onClick={(e)=>{
                            setActiveCourse(el.id)
                        }} key={index} className={`courses-block ${el.active ? `courses-block-active` : ''}`}>
                            <p className={`courses-block__title`}>{el.name}</p>
                            <p className={`courses-block__text`}>{el.date}</p>
                        </button>
                    ))}


                </div>


            </div>
            <div className={'block-middle block-middle-full block-middle-full-t-0'}>
                <p className={'block-middle__text'}>
                    <div className={`courses-block courses-block-l`}>
                        <p className={`courses-block__title courses-block__title-l`}>{currentCourse.name}</p>
                        <p className={`courses-block__text`}>{currentCourse.date}</p>
                    </div>
                </p>

                <div className="file-box">
                    {/*{fileItems.map((el, index)=> (*/}
                    {/*    <div className="file-item" key={el.id}>*/}
                    {/*        <img src={el.img} alt={el.text}/>*/}
                    {/*        <div className="file-item-block">*/}
                    {/*            <p className={`courses-block__title courses-block__title-l file-item-block__title`}>{el.text}</p>*/}
                    {/*            <p className={`courses-block__text file-item-block__text`}>{el.date}</p>*/}
                    {/*        </div>*/}
                    {/*    </div>*/}
                    {/*))}*/}
                </div>

            </div>
        </div>
    );
};

export default HomeworkPageStudent;