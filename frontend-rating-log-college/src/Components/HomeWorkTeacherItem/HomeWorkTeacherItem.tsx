import React, {useEffect, useState} from 'react';

import './HomeWorkTeacherItem.scss';
import {IHomeWorks} from "../../Pages/HomeWorksPageTeacher/HomeWorksPageTeacher";
import {useTranslation} from "react-i18next";

interface IHomeWorkTeacherItem extends IHomeWorks{
    onClick: (id: number) => void;
}
const HomeWorkTeacherItem: React.FC<IHomeWorkTeacherItem> = (props) => {
    let [name, setName] = useState(props.name)
    let [id, setId] = useState(props.id)
    let [subject, setSubject] = useState(props.subject)
    let [group, setGroup] = useState(props.group)
    let [date, setDate] = useState(props.date)
    let [count, setCount] = useState(props.count)
    let [active, setActive] = useState(props.active)

    useEffect(()=>{
        setName(props.name)
        setId(props.id)
        setSubject(props.subject)
        setGroup(props.group)
        setDate(props.date)
        setCount(props.count)
        setActive(props.active)
    }, [props])

    const { t } = useTranslation();

    return (
        <div className={`homework-teacher-item ${active ? 'homework-teacher-item-a' : ''}`}
        onClick={(e)=>{
            props.onClick(id)
        }}
        >
            <p className="homework-teacher-item__name">{name}</p>
            <div className="homework-teacher-item-inner-block">
                <p className="homework-teacher-item-inner-block__title">{t('subject')}:</p>
                <p className="homework-teacher-item-inner-block__text">{subject}</p>
            </div>
            <div className="homework-teacher-item-inner-block">
                <p className="homework-teacher-item-inner-block__title">{t('group')}:</p>
                <p className="homework-teacher-item-inner-block__text">{group}</p>
            </div>
            <div className="homework-teacher-item-inner-block">
                <p className="homework-teacher-item-inner-block__title">{t('expiresTo')}:</p>
                <p className="homework-teacher-item-inner-block__text">{date}</p>
            </div>
            <div className="homework-teacher-item-inner-block">
                <p className="homework-teacher-item-inner-block__text">{t('completed')}: {count}</p>
            </div>
        </div>
    );
};

export default HomeWorkTeacherItem;