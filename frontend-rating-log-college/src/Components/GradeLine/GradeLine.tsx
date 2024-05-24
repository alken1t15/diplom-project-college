import React, {CSSProperties, useEffect, useState} from 'react';
import './GradeLine.scss';
import InitialsImage from "../InitialsImage/InitialsImage";
import TeachersBlock from "../TeachersBlock/TeachersBlock";
export interface IGradeLineItem{
    teacherName: string;
    subject: string;
    date: string;
    grade: number;
}

interface IGradeLine{
    item: IGradeLineItem;
    styles?: CSSProperties,
    teachersBlock?: boolean
}
const GradeLine: React.FC<IGradeLine> = (props) => {

    let[item, setItem] = useState<IGradeLineItem>(props.item)

    useEffect(()=>{
      setItem(props.item)

    }, [props])


    return (
        <div className={`grade-line-block
        ${item.grade === 5 ? 'grade-block-green' : item.grade === 4 ? 'grade-block-dark-green' : item.grade === 3 ? 'grade-block-yellow' : item.grade === 2 ? 'grade-block-red' : ''}`}>
            <div className="grade-line-top">
                {props.teachersBlock ? <>
                    <TeachersBlock item={{name: item.teacherName, subject: item.subject}} styles={props.styles}/>
                </> : <>
                    <TeachersBlock item={{ name: item.teacherName, subject: item.subject}}/>
                </>}
            </div>

            <div className="grade-line-wrapper">
                <div className="grade-line-column">
                    <p className="grade-line-column__text">Дата обьявление оценки:</p>
                    <p className="grade-line-column__text grade-line-column__text-color-wh">{item.date}</p>
                </div>
                <div className="grade-line-column">
                    <p className="grade-line-column__text">Оценка:</p>
                    <p className={`grade-line-column__text grade-line-column__text-grade
                    ${item.grade === 5 ? 'grade-green' : item.grade === 4 ? 'grade-dark-green' : item.grade === 3 ? 'grade-yellow' : item.grade === 2 ? 'grade-red' : ''}`}>{item.grade}</p>
                </div>

            </div>

        </div>
    );
};

export default GradeLine;