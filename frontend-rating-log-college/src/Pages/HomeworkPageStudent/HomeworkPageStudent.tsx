import React, {useEffect, useState} from 'react';
import './HomeworkPageStudent.scss';
import HomeworkBlock, {HomeworkItem} from "../../Components/HomeworkBlock/HomeworkBlock";
import TeachersBlock from "../../Components/TeachersBlock/TeachersBlock";
import FileItem from "../../Components/FileItem/FileItem";
import FileUploader from "../../Components/FileUploader/FileUploader";
import {getCoursesItems} from "../../Http/HomeWorks";

const fileImg = require('../../assets/images/PDF.png');


const HomeworkPageStudent: React.FC = () => {

    let[homeWork, setHomeWork] = useState<HomeworkItem[]>([])
    let[currentCourse, setCurrentCourse] = useState<HomeworkItem>()
    let[currentHomework, setCurrentHomework] = useState(
        {
        // id: 1,
        // name: 'Наименования задания',
        // expires: '9 сентября - 15 сентября',
        // status: 'Выполняется',
        // workStatus: 'Назначенно',
        // teacher: {
        //     id: 1,
        //     name: 'Денис Валентинович',
        //     subject: 'Веб-программирования'
        // },
        // files: [
        //     {
        //
        //         id: 1,
        //         text: 'Учебник истории',
        //         date: '3 сентября',
        //         img: fileImg,
        //         size: '5.3 мб'
        //     },
        //     {
        //         id: 2,
        //         text: 'Учебник истории 2',
        //         date: '4 сентября',
        //         img: fileImg,
        //         size: '5.3 мб'
        //     },
        //     {
        //         id: 3,
        //         text: 'Учебник истории 3',
        //         date: '5 сентября',
        //         img: fileImg,
        //         size: '5.3 мб'
        //     },
        //
        // ],
        // homeWorkFiles: [
        //     {
        //         name: 'File_name.pdf',
        //         size: '5.3 мб',
        //         url: fileImg
        //     }
        // ]
    })
    function setActiveHomeWork(id: number){
        let curArr = [...homeWork];
        let newArr = curArr.map((el, index)=>{
            el.active = el.id === id;
            return el;
        })
        setHomeWork(newArr)
    }

    useEffect(()=>{
        getCoursesItems('').then((response)=>{
            console.log(response.data.homeWork)
            console.log(response.data.homeWorks)

            let newHwk = response.data.homeWorks.map((el: any)=>{
                let newObj =
                {
                    id: el.id,
                    active: el.id === response.data.homeWork.id,
                    name: el.name,
                    date: '10 Сен',
                    expiresAt: `${el.startDate} - ${el.endDate}`,
                    teacher: el.teacherName,
                    subject: el.subjectName
                }
                if(el.id === response.data.homeWork.id){
                    // setCurrentHomework(newObj)
                }
                return newObj;
            })
            setHomeWork(newHwk)


        }).catch((error)=>{

        })
    },[])


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
                        {homeWork.length > 0 ? [...homeWork].map((el, index) => (
                            <HomeworkBlock item={el} onClick={setActiveHomeWork} key={el.id}/>
                        )) : ''}
                    </div>


                </div>


            </div>

            <div className={'block-middle block-middle-full block-middle-full-t-0 block-middle-full-t-hw'}>
                {currentCourse ? <>
                    <div className={'block-middle__text'}>
                        <div className={`courses-block courses-block-l`}>
                            <p className={`courses-block__title courses-block__title-l block-left__text-hw`}>{currentCourse.name}</p>
                        </div>
                    </div>
                    <div className="block-middle-info">
                        <p className="block-middle-info__expires"><span>Срок задание: </span>{currentHomework.expires}</p>
                        <TeachersBlock item={currentHomework.teacher} styles={{marginTop: 20}}/>
                        <p className="status">
                            Статус:&nbsp;
                            <span
                                className={`status-color ${currentHomework.status == "Выполняется" ? "status-color__purple" :
                                    currentHomework.status == "Назначенно" ? "status-color__green" :
                                        currentHomework.status == "Просрочено" ? "status-color__red" : ''
                                }`}>
                         {currentHomework.status}
                    </span></p>
                        <p className="block-middle-info__text">
                            Дневник-отчёт Вы должны распечатать, в местах где согласно инструкции должны проставить печати с
                            предприятия ОБЯЗАТЕЛЬНО должны поставить. Потом Вы сканируете дневник-отчёт и в pdf формате
                            прикрепляете в GoogleClassRoom, также туда прикрепляете Отчёт и Презентацию. Срок сдачи
                            ПОНЕДЕЛЬНИК 20 ИЮНЯ ДО 17:30!!!
                        </p>
                    </div>

                    <div className="block-middle-info__files-block">
                        <p className="block-middle-info__files-block__text">Приклепленные файлы</p>
                        <div className="file-box block-middle-info__files-file-box">
                            {currentHomework.files.map((el, index) => (
                                <FileItem item={el} key={index}/>
                            ))}
                        </div>
                    </div>
                </> : ''}

            </div>

            <div className={'block-right block-right-hw'}>
                <div className={`image-block image-block-active image-block-hw`}>
                    <div className="image-block-top">
                        <p className={'image-block-top__text'}>
                            Мои задания
                            <span
                                className={`status-color ${currentHomework.workStatus == "Сдано" ? "status-color__purple" :
                                    currentHomework.workStatus == "Назначенно" ? "status-color__green" :
                                        currentHomework.workStatus == "Просрочено" ? "status-color__red" : ' '
                                }`}>
                    {currentHomework.workStatus}</span>
                        </p>


                        <FileUploader status={currentHomework.workStatus}/>


                    </div>
                </div>
            </div>
        </div>
    );
};

export default HomeworkPageStudent;