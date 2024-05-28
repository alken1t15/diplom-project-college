import React, {useEffect, useState} from 'react';
import './UploadPageTeacher.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import GradeLine from "../../Components/GradeLine/GradeLine";
import Pagination, {IDataArrayItem} from "../../Components/Pagination/Pagination";
import LatenessItem, {ITardinessItem} from "../../Components/Lateness/LatenessItem";
import ScheduleItem from "../../Components/Schedule/ScheduleItem";
import FileUploader from "../../Components/FileUploader/FileUploader";
import {addNewCertificate, mainPageData} from "../../Http/MainPage";
import {$api} from "../../Http";
import {addNewFiles} from "../../Http/Courses";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');
const UploadPageTeacher: React.FC = () => {

    let[groups, setGroups] = useState([])
    let[selectedGroup, setSelectedGroup] = useState(1)



    const sendGroupFile = async (items: { name: string; url: string }[]) => {
        const formData = new FormData();

        const fetchBlob = async (url: string) => {
            const response = await fetch(url);
            return await response.blob();
        };

        for (const item of items) {
            const blob = await fetchBlob(item.url);
            formData.append('files', blob, item.name);
            formData.append('id', String(selectedGroup));
        }

        try {
            const response = await addNewFiles(formData);
        } catch (error) {
        }
    };

    return (
        <div className={'main-page'}>
            <div className={'block-left block-left-add'}>
                <div className="block-left-header">
                    <p className={'block-left__text'}>
                        Добавление файла к группе
                    </p>
                    <div className="block-left-header-personal">
                        <FileUploader onClick={sendGroupFile} multipart={true}/>
                    </div>

                </div>


            </div>
            <div className={'block-middle block-middle-add'}>
                <p className={'block-middle__text'}>
                    Текущие задания
                </p>


            </div>
            <div className={'block-right block-right-add'}>
                <p className={'block-right__text'}>
                    <img src={teachImg} alt="Info img"/>
                    Расписание на сегодня
                </p>

            </div>
        </div>
    );
};

export default UploadPageTeacher;