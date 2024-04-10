import React, {useEffect, useState} from 'react';
import './HomeworkBlock.scss';

export interface HomeworkItem{
    id: number;
    active: boolean;
    name: string;
    date: string;
    expiresAt: string;
    teacher: string;
    subject: string;
}

interface IHomeworkBlock{
    item: HomeworkItem;
    onClick: (id: number) => void;
}
const HomeworkBlock: React.FC<IHomeworkBlock> = (props) => {
    let[curItem, setCurItem] = useState<HomeworkItem>(props.item)
    useEffect(() => {
        setCurItem(props.item)
    }, [props.item]);
    return (
        <div onClick={(e)=>{
            props.onClick(curItem.id)
        }} className={`homework-item ${curItem.active ? 'homework-item-active' : ''}`}>
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
    );
};

export default HomeworkBlock;