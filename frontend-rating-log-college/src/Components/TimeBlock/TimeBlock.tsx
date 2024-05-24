import React from 'react';
import './TimeBlock.scss';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';
export interface ITimeBlock{
    time: string;
}
const TimeBlock: React.FC<ITimeBlock> = ({ time}) => {

    const today = new Date();
    const day = format(today, 'dd MMM', { locale: ru });
    const weekday = format(today, 'EEEE', { locale: ru });

    return (
        <div className={'time-block'}>
            <div className="time-block-left">
                {day.split(' ')[0]}
                <span>{day.split(' ')[1]}</span>
            </div>
            <div className="time-block-right">
                <p><span>{time.split(':')[0]}</span>:{time.split(':')[1]}</p>
                <span className={'time-block-right-span'}>{weekday}</span>
            </div>
        </div>
    );
};

export default TimeBlock;