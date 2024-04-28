import React, {useEffect, useState} from 'react';
import './HomeWorksPageTeacher.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import GradeLine from "../../Components/GradeLine/GradeLine";
import Pagination, {IDataArrayItem} from "../../Components/Pagination/Pagination";
import LatenessItem, {ITardinessItem} from "../../Components/Lateness/LatenessItem";
import ScheduleItem from "../../Components/Schedule/ScheduleItem";
import FileUploader from "../../Components/FileUploader/FileUploader";
import {mainPageData} from "../../Http/MainPage";
import doneImg from '../../assets/images/Check.svg';
import HomeworkBlock, {HomeworkItem} from "../../Components/HomeworkBlock/HomeworkBlock";
import TimeBlock, {ITimeBlock} from "../../Components/TimeBlock/TimeBlock";
import ScheduleTeacherBlock from "../../Components/ScheduleTeacherBlock/ScheduleTeacherBlock";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');

export interface ISchedule{
    groupName: string;
    time: string;
    subject: string;
    active?: boolean;
}

const HomeWorksPageTeacher: React.FC = () => {



    useEffect(()=>{
        // mainPageData()
        //     .then(response=>{
        //         console.log(response.data)
        //     })
        //     .catch(error=>{
        //
        //     })
    }, [])


    return (
        <div className={'main-page main-page-t'}>
            <div className={'block-middle block-middle-t'}>
                <p className={'block-middle__text block-middle__text-t'}>
                    Домашние задания
                </p>


            </div>
            <div className={'block-right block-right-t'}>
                <p className={'block-right__text'}>

                </p>


            </div>
        </div>
    );
};

export default HomeWorksPageTeacher;