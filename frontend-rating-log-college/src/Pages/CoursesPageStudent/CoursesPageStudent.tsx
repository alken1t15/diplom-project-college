import React, {useState} from 'react';
import './CoursesPageStudent.scss';
import CoursesItem, {CourseItem} from "../../Components/CoursesItem/CoursesItem";

const fileImg = require('../../assets/images/PDF.png');


const CoursesPageStudent: React.FC = () => {

    let[courses, setCourses] = useState<CourseItem[]>([
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
    let[currentCourse, setCurrentCourse] = useState<CourseItem>(courses[0])
    let[fileItems, setFileItems] = useState([
        {
            id: 1,
            text: 'Учебник истории',
            date: '3 сентября',
            img: fileImg
        },
        {
            id: 2,
            text: 'Учебник истории за 9 классы ОБ ЖБГ ИМНП',
            date: '15 сентября',
            img: fileImg
        },
        {
            id: 3,
            text: 'Учебник истории',
            date: '4 сентября',
            img: fileImg
        },
    ])
    function setActiveCourse(id: number){

        const currentIndex = courses.findIndex(el => el.active) + 1;

        if(id === currentIndex){

        }

        else if(currentIndex < id){

            for(let index = currentIndex; index !< courses.length; index++ ){

                setTimeout(() => {
                    setCourses(prevState => {
                        const updatedTabs = [...prevState];
                        updatedTabs[index].active = updatedTabs[index].id <= id;
                        return updatedTabs;
                    });
                }, index * 100);

            }

            courses.forEach((el, index) => {
                setTimeout(() => {
                    setCourses(prevState => {
                        const updatedTabs = [...prevState];
                        updatedTabs[index].active = updatedTabs[index].id === id;


                        return updatedTabs;
                    });
                }, index * 200);
            });


        }


        else{

            for(let index = currentIndex-1; index > id-1; index-- ){
                setTimeout(() => {
                    setCourses(prevState => {
                        const updatedTabs = [...prevState];
                        updatedTabs[index].active = updatedTabs[index].id >= id;
                        return updatedTabs;
                    });
                }, (courses.length - index - 1) * 100);

            }

            [...courses].reverse().forEach((el, i) => {
                setTimeout(() => {
                    setCourses(prevState => {
                        const updatedTabs = [...prevState];
                        updatedTabs[i].active = updatedTabs[i].id === id;

                        return updatedTabs;
                    });
                }, (courses.length - i - 1) * 200);
            });

        }

    }


    return (
        <div className={'main-page main-page-courses'}>
            <div className={'block-left block-left-courses'}>
                <div className="block-left-header">
                    <div className="block-left-main">
                        <p className={'block-left__text block-left__text-c'}>
                            Курсы
                        </p>
                    </div>
                    {[...courses].map((el, index) => (
                        <CoursesItem item={el} key={index} onClick={setActiveCourse}/>

                    ))}


                </div>


            </div>
            <div className={'block-middle block-middle-full block-middle-full-t-0'}>
                <div className={'block-middle__text'}>
                    <div className={`courses-block courses-block-l`}>
                        <p className={`courses-block__title courses-block__title-l`}>{currentCourse.name}</p>
                        <p className={`courses-block__text`}>{currentCourse.date}</p>
                    </div>
                </div>

                <div className="file-box">
                    {fileItems.map((el, index)=> (
                        <div className="file-item" key={el.id}>
                            <img src={el.img} alt={el.text}/>
                            <div className="file-item-block">
                                <p className={`courses-block__title courses-block__title-l file-item-block__title`}>{el.text}</p>
                                <p className={`courses-block__text file-item-block__text`}>{el.date}</p>
                            </div>
                        </div>
                    ))}
                </div>

            </div>
        </div>
    );
};

export default CoursesPageStudent;