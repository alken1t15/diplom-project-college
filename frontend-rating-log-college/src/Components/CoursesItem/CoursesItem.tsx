import React, {useEffect, useState} from 'react';
import './CoursesItem.scss';

export interface CourseItem{
    id: number;
    active: boolean;
    name: string;
    date: string;
}

interface ICourseBlock{
    item: CourseItem;
    onClick: (id: number) => void;
}

const CoursesItem: React.FC<ICourseBlock> = ({item, onClick}) => {
    let[curItem, setCurItem] = useState<CourseItem>(item)
    useEffect(() => {
        setCurItem(item)
    }, [item]);

    return (
        <button
            onClick={(e)=>{
                onClick(curItem.id)
            }}
            className={`courses-block ${curItem.active ? `courses-block-active` : ''}`}>
            <p className={`courses-block__title`}>{curItem.name}</p>
            <p className={`courses-block__text`}>{curItem.date}</p>
        </button>
    );
};

export default CoursesItem;