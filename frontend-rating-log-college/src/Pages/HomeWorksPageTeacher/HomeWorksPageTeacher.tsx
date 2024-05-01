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
import Filter, {IFilterItem} from "../../Components/Filter/Filter";

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

    let[filterItems, setFilterItems]=  useState<IFilterItem[]>([
        {
            id: 1,
            name: 'Предмет',
            items: [
                {
                    id: 1,
                    name: 'Веб-программирование',
                    active: false,
                }
            ]
        },
        {
            id: 2,
            name: 'Группа',
            items: [
                {
                    id: 1,
                    name: 'П-20-51б',
                    active: false,
                },
                {
                    id: 2,
                    name: 'П-20-53к',
                    active: false,
                },
            ]
        },
    ])

    useEffect(()=>{
        // mainPageData()
        //     .then(response=>{
        //         console.log(response.data)
        //     })
        //     .catch(error=>{
        //
        //     })
    }, [])

    function setCurrentFilter(parentId: number, itemId: number){
        console.log(parentId)
        console.log(itemId)
    }

    return (
        <div className={'main-page main-page-t hw-page-t'}>
            <div className={'block-middle block-middle-t'}>
                <p className={'block-middle__text block-middle__text-t'}>
                    Домашние задания
                </p>

                <div className="block-middle-top">
                    <Filter items={filterItems} onCLick={setCurrentFilter}/>
                </div>

            </div>
            <div className={'block-right block-right-t'}>
                <p className={'block-right__text'}>

                </p>


            </div>
        </div>
    );
};

export default HomeWorksPageTeacher;