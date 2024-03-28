import React, {useEffect, useState} from 'react';
import './GradeLine.scss';
import InitialsImage from "../InitialsImage/InitialsImage";
interface IGradeLine{
    teacherName: string;
    subject: string;
    date: string;
    grade: number;
}
const GradeLine: React.FC<IGradeLine> = (props) => {

    let [teacherName, setTeacherName] = useState<string>()
    let [subject, setSubject] = useState<string>()
    let [date, setDate] = useState<string>()
    let [grade, setGrade] = useState<number>()
    let [initials, setInitials] = useState<string>()

    useEffect(()=>{
        setTeacherName(props.teacherName)
        setSubject(props.subject)
        setDate(props.date)
        setGrade(props.grade)

        let teachName = props.teacherName.split(' ');
        let f, n = '';
        f = teachName[0].slice(0, 1);
        n = teachName[1].slice(0, 1);

        setInitials(f+n)


    }, [props])


    return (
        <div className={`grade-line-block
        ${grade === 5 ? 'grade-block-green' : grade === 4 ? 'grade-block-dark-green' : grade === 3 ? 'grade-block-yellow' : grade === 2 ? 'grade-block-red' : ''}`}>
            <div className="grade-line-top">
                <div className="grade-line-top-l">
                    <InitialsImage initials={initials ? initials : ''} width={50} height={50} fontSize={24} textColor="#fff" backgroundColor="#d9d9d9" />
                </div>
                <div className="grade-line-top-r">
                    <p className="grade-line-top-r__title">{teacherName}</p>
                    <p className="grade-line-top-r__text">Предмет: {subject}</p>
                </div>
            </div>

            <div className="grade-line-wrapper">
                <div className="grade-line-column">
                    <p className="grade-line-column__text">Дата обьявление оценки:</p>
                    <p className="grade-line-column__text grade-line-column__text-color-wh">{date}</p>
                </div>
                <div className="grade-line-column">
                    <p className="grade-line-column__text">Оценка:</p>
                    <p className={`grade-line-column__text grade-line-column__text-grade
                    ${grade === 5 ? 'grade-green' : grade === 4 ? 'grade-dark-green' : grade === 3 ? 'grade-yellow' : grade === 2 ? 'grade-red' : ''}`}>{grade}</p>
                </div>

            </div>

        </div>
    );
};

export default GradeLine;