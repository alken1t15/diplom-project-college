import React, {useState} from 'react';
import './GradePageStudent.scss';
import InitialsImage from "../../Components/InitialsImage/InitialsImage";
import GradeLine from "../../Components/GradeLine/GradeLine";
import Pagination from "../../Components/Pagination/Pagination";
import LatenessItem, {ITardinessItem} from "../../Components/Lateness/LatenessItem";
import ScheduleItem from "../../Components/Schedule/ScheduleItem";
import FileUploader from "../../Components/FileUploader/FileUploader";

const infoImg = require('../../assets/images/InformationImgg.png');
const gradeImg = require('../../assets/images/GradesImg.png');
const houseImg = require('../../assets/images/School.png');
const teachImg = require('../../assets/images/TeacherImg.png');
const backImg = require('../../assets/images/backImg.png');
const GradePageStudent: React.FC = () => {

    let[currentPage, setCurrentPage] = useState(7);
    let[currentCourse, setCurrentCourse] = useState(1);
    let[currentQuarter , setCurrentQuarter] = useState(1);
    

    function updateCurrentPage(value: any){
        setCurrentPage(value)
    }


    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-grade'}>



                <div className="block-middle-wrapper">
                    <div className="block-middle-tabs">

                    </div>
                    <Pagination  onChange={updateCurrentPage} styles={{marginBottom: 15}}/>
                </div>


            </div>
            <div className={'block-right'}>
                <p className={'block-right__text'}>
                    <img src={teachImg} alt="Info img"/>
                    Расписание на сегодня
                </p>

            </div>
        </div>
    );
};

export default GradePageStudent;