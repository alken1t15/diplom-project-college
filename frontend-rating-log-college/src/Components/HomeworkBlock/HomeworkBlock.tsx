import React, {useEffect, useState} from 'react';
import './HomeworkBlock.scss';

interface HomeworkItem{
    id: number;
    active: boolean;
    name: string;
    date: string;
}

interface IHomeworkBlock{
    item: HomeworkItem;
}
const HomeworkBlock: React.FC<IHomeworkBlock> = (props) => {
    let[curItem, setCurItem] = useState<HomeworkItem>(props.item)
    useEffect(() => {
        setCurItem(props.item)
    }, [props.item]);
    return (
        <button className={`courses-block ${curItem.active ? `courses-block-active` : ''}`}>
            <p className={`courses-block__title`}>{curItem.name}</p>
            <p className={`courses-block__text`}>{curItem.date}</p>
        </button>
    );
};

export default HomeworkBlock;