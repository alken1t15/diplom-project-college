import React from 'react';
import './TimeBlock.scss';

export interface ITimeBlock{
    date: string;
    time: string;
    nameOfTheDay: string;
}
const TimeBlock: React.FC<ITimeBlock> = ({date, time, nameOfTheDay}) => {



    return (
        <div className={'time-block'}>
            <div className="time-block-left">
                {date.split(' ')[0]}
                <span>{date.split(' ')[1]}</span>
            </div>
            <div className="time-block-right">
                <p><span>{time.split(':')[0]}</span>:{time.split(':')[1]}</p>
                <span className={'time-block-right-span'}>{nameOfTheDay}</span>
            </div>
        </div>
    );
};

export default TimeBlock;