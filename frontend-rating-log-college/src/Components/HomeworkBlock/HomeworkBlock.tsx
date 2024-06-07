import React, {useEffect, useState} from 'react';
import './HomeworkBlock.scss';
import {useTranslation} from "react-i18next";

export interface HomeworkItem{
    id: number;
    active: boolean;
    name: string;
    date: string;
    expiresAt: string;
    teacher: string;
    subject: string;
    scholar?: string;
    dueDate?: string;
}

interface IHomeworkBlock{
    item: HomeworkItem;
    onClick?: (id: number) => void;
    forTeacher?: boolean;
}
const HomeworkBlock: React.FC<IHomeworkBlock> = (props) => {
    let[curItem, setCurItem] = useState<HomeworkItem>(props.item)
    useEffect(() => {
        setCurItem(props.item)
    }, [props.item]);
    const { t } = useTranslation();
    return (
        <>
            {props.forTeacher ?
                <div className={`homework-item`}>
                    <div className="homework-item-l">
                        <p className={`homework-item-l__text`}>{curItem.date}</p>
                    </div>
                    <div className="homework-item-r">
                        <div className="homework-item-r-top">
                            <p className="homework-item-r-top__title">{curItem.name}</p>
                            <p className="homework-item__text homework-item__text-t" style={{marginTop: 5}}>{t('sdano')}: <span>{curItem.dueDate}</span></p>
                        </div>
                        <div className="homework-item-r-bot">
                            <p className="homework-item__text homework-item__text-t">{t('student')}: <span>{curItem.scholar}</span></p>
                        </div>
                    </div>
                </div>
                :
                <div
                    onClick={(e)=>{
                        if (props.onClick) {
                        props.onClick(curItem.id)
                        } else {
                        }
                        }}
                     className={`homework-item ${curItem.active ? 'homework-item-active' : ''}`}>
                    <div className="homework-item-l">
                        <p className={`homework-item-l__text`}>{curItem.date}</p>
                    </div>
                    <div className="homework-item-r">
                        <div className="homework-item-r-top">
                            <p className="homework-item-r-top__title">{curItem.name}</p>
                            <p className="homework-item__text" style={{marginTop: 5}}>{curItem.expiresAt}</p>
                        </div>
                        <div className="homework-item-r-bot">
                            <p className="homework-item__text">{curItem.teacher}</p>
                            <p className="homework-item__text">{curItem.subject}</p>
                        </div>
                    </div>
                </div>
            }
        </>
    );
};

export default HomeworkBlock;