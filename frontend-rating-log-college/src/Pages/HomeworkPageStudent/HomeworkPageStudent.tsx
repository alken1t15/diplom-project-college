import React, {useEffect, useState} from 'react';
import './HomeworkPageStudent.scss';
import HomeworkBlock, {HomeworkItem} from "../../Components/HomeworkBlock/HomeworkBlock";
import TeachersBlock from "../../Components/TeachersBlock/TeachersBlock";
import FileItem, {IFileItem} from "../../Components/FileItem/FileItem";
import FileUploader from "../../Components/FileUploader/FileUploader";
import {getCoursesItems, sendHomeWorkFiles} from "../../Http/HomeWorks";
import {addNewCertificate} from "../../Http/MainPage";
import {addNewFiles} from "../../Http/Courses";

const fileImg = require('../../assets/images/PDF.png');

interface ICurrentHw{
    id: number;
    name: string;
    expires: string;
    status: string;
    teacher: {
        name: string;
        subject: string;
    },
    homeWorkFiles: [
        {
            name: string;
            size: string;
            url: any
        }
    ],
    files: [
        {
            id: number;
            text: string;
            date: string;
            img: any;
            size: string;
        }
    ]
}

interface ITeacher{
    name: string;
    subject: string;
}

const HomeworkPageStudent: React.FC = () => {

    let[homeWork, setHomeWork] = useState<HomeworkItem[]>([])

    let[curId, setCurId] = useState<number>();
    let[curName, setCurName] = useState('');
    let[expires, setExpires] = useState('');
    let[status, setStatus] = useState('');
    let[deskr, setDeskr] = useState('')
    let[teacherName, setTeacherName] = useState('');
    let[teacherSubject, setTeacherSubject] = useState('');
    let[homeWorkFiles, setHomeWorkFiles] = useState<IFileItem[]>([]);
    let[taskFiles, setTaskFiles] = useState<any[]>([]);
    let[teacherItem, setTeacherItem] = useState<ITeacher>()

    function setActiveHomeWork(id: number){
        let curArr = [...homeWork];
        let newArr = curArr.map((el, index)=>{
            el.active = el.id === id;
            return el;
        })
        setHomeWork(newArr)

        getCoursesItems(String(id)).then((response: any)=>{
            updateCurrentHomeWork(response.data.homeWork)
            console.log(response.data.homeWork)
        }).catch((error)=>{

        })

    }

    function updateCurrentHomeWork(obj: any){
        setCurId(obj.id)
        setCurName(obj.name)
        setExpires(`${obj.startDate} - ${obj.endDate}`)
        setStatus(obj.statusStudent)
        setTeacherName(obj.teacherName)
        setTeacherSubject(obj.subjectName)
        setDeskr(obj.description)
        setTeacherItem({name: obj.teacherName, subject: obj.subjectName})


        if(obj.fileHomeTask.length > 0){

            console.log(obj.fileHomeTask)
            let newArr = obj.fileHomeTask.map((el: any, index: any)=>{
                let newObj = {
                    name: el.name,
                    url: el.file
                }
                return newObj
            })
            console.log(newArr)
            // let newFiles = [
            //     {
            //         id: 1,
            //         text: 'Учебник истории',
            //         date: '3 сентября',
            //         img: fileImg,
            //         size: '5.3 мб'
            //     }
            // ]
            // setTaskFiles(newFiles)
        }





        if(obj.files.length > 0){
            let filArr =obj.files.map((el: any, index: any)=>{
                let newObj =  {
                    name: el.name,
                    url: el.file,
                }
                return newObj;
            })
            setTaskFiles(filArr)
        }


    }


    useEffect(()=>{
        getCoursesItems('').then((response)=>{

            let newHwk = response.data.homeWorks.map((el: any)=>{
                let newObj =
                {
                    id: el.id,
                    active: el.id === response.data.homeWork.id,
                    name: el.name,
                    date: `${el.startDate}`,
                    expiresAt: `${el.startDate} - ${el.endDate}`,
                    teacher: el.teacherName,
                    subject: el.subjectName
                }
                return newObj;
            })
            setHomeWork(newHwk)
            updateCurrentHomeWork(response.data.homeWork)




        }).catch((error)=>{

        })
    },[])



    const sendCertif = async (files: File[]) => {
        const formData = new FormData();

        files.forEach((file) => {
            formData.append('files', file, file.name);
        });

        try {
            const response = await sendHomeWorkFiles(curId,formData);
        } catch (error) {
        }
    };



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
                {curId ? <>
                    <div className={'block-middle__text'}>
                        <div className={`courses-block courses-block-l`}>
                            <p className={`courses-block__title courses-block__title-l block-left__text-hw`}>{curName}</p>
                        </div>
                    </div>
                    <div className="block-middle-info">
                        <p className="block-middle-info__expires"><span>Срок задания: </span>{expires}</p>
                        <TeachersBlock item={teacherItem ? teacherItem : {name: '', subject: ''} } styles={{marginTop: 20}}/>
                        {/*<p className="status">*/}
                            {/*Статус:&nbsp;*/}
                         {/*   <span*/}
                         {/*       className={`status-color ${status == "Выполняется" ? "status-color__purple" :*/}
                         {/*           status == "Назначенно" ? "status-color__green" :*/}
                         {/*               status == "Просрочено" ? "status-color__red" : ''*/}
                         {/*       }`}>*/}
                         {/*{currentHomework && currentHomework.status ? currentHomework.status : ''}*/}
                         {/*   </span>*/}
                        {/*</p>*/}
                        <p className="block-middle-info__text">
                            {deskr}
                        </p>
                    </div>

                    <div className="block-middle-info__files-block">
                        <p className="block-middle-info__files-block__text">Приклепленные файлы</p>
                        <div className="file-box block-middle-info__files-file-box">
                            {homeWorkFiles && homeWorkFiles.length > 0 ? homeWorkFiles.map((el, index) => (
                                <FileItem item={el} key={index}/>
                            )) : ''}
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
                                className={`status-color ${status == "Сдано" ? "status-color__purple" :
                                    status == "Назначенно" ? "status-color__green" :
                                        status == "Просрочено" ? "status-color__red" : ' '
                                }`}>
                    {status}</span>
                        </p>


                        <FileUploader items={taskFiles} multipart={true} onClick={sendCertif} homeWorkId={curId} status={status}/>


                    </div>
                </div>
            </div>
        </div>
    );
};

export default HomeworkPageStudent;