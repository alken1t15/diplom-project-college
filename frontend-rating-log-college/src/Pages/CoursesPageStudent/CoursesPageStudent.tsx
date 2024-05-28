import React, {useEffect, useState} from 'react';
import './CoursesPageStudent.scss';
import CoursesItem, {CourseItem} from "../../Components/CoursesItem/CoursesItem";
import FileItem, {IFileItem} from "../../Components/FileItem/FileItem";
import {getCoursesItems} from "../../Http/Courses";

const fileImg = require('../../assets/images/PDF.png');


const CoursesPageStudent: React.FC = () => {

    let[courses, setCourses] = useState<CourseItem[]>([]);
    let[currentCourse, setCurrentCourse] = useState<CourseItem>();
    let[fileItems, setFileItems] = useState<IFileItem[]>([]);
    let[currentCourseName, setCurrentCourseName] = useState('');
    let[currentCourseYear, setCurrentCourseYear] = useState('');

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

            setCurCourse(id)

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

            setCurCourse(id)

        }

    }

    function updateFiles(data: any){
        let newArr = data.map((el: any, index: any)=>{

            let newObj = {
                id: index,
                text: el.name,
                date: el.date,
                img: el.file
            }
            return newObj
        })
        setFileItems(newArr)

    }

    function setCurCourse(id: number){



        getCoursesItems(id).then((response: any)=>{

            updateFiles(response.data.files);
            setCurrentCourseName(`${response.data.currentCourse} Курс`)
            setCurrentCourseYear(`${response.data.currentYear}`)


        }).catch((error)=>{

        })
    }



    useEffect(()=>{
        getCoursesItems(currentCourse && currentCourse.id ? currentCourse.id : 1).then((response: any)=>{
            let newCourses = response.data.courses.map((el: any)=>{
                let newObj = {
                    id: el.course,
                    name: `${el.course} Курс`,
                    date: `Год: ${el.year}`,
                    active: response.data.currentCourse === el.course
                }
                if(response.data.currentCourse === el.course){
                    setCurCourse(1)
                }
                return newObj;
            })
            setCourses(newCourses)
            setCurCourse(1)


        }).catch((error)=>{

        })
    },[])


    return (
        <div className={'main-page main-page-courses'}>
            <div className={'block-left block-left-courses'}>
                <div className="block-left-header">
                    <div className="block-left-main">
                        <p className={'block-left__text block-left__text-c'}>
                            Курсы
                        </p>
                    </div>
                    {courses.length > 0 ? [...courses].map((el, index) => (
                        <CoursesItem item={el} key={index} onClick={setActiveCourse}/>
                    )) : ''}

                </div>


            </div>
            <div className={'block-middle block-middle-full block-middle-full-t-0'}>
                <div className={'block-middle__text'}>
                    <div className={`courses-block courses-block-l`}>
                        <p className={`courses-block__title courses-block__title-l`}>{currentCourseName}</p>
                        <p className={`courses-block__text`}>Год: {currentCourseYear}</p>
                    </div>
                </div>

                <div className="file-box">
                    {fileItems.map((el, index)=> (
                      <FileItem item={el} key={el.id}/>
                    ))}
                </div>

            </div>
        </div>
    );
};

export default CoursesPageStudent;