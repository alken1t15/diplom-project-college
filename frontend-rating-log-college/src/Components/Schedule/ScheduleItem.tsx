import React, {CSSProperties, useEffect, useState} from 'react';
import './ScheduleItem.scss';

export interface ISchedule{
    id: number,
    time: string,
    subject: string
}

export interface ITardinessItem{
    date: string;
    nameOfDay: string;
    schedules: ISchedule[];
    styles?: CSSProperties
}
const ScheduleItem: React.FC<ITardinessItem> = (props) => {
    let [item, setItem] = useState(props);

    useEffect(()=>{
        setItem(props)
        console.log(props.schedules[0].time.split(' ')[0])
    }, [props])

    return (
        <div className="schedule-item" style={item.styles}>
            <p className="schedule-item__date">{item.date}</p>
            <p className="schedule-item__day">День недели: {item.nameOfDay}</p>


            {item.schedules.map((el, index)=>(
                <div className={`schedule-item__info-block`} key={el.id}>
                    <p>
                        <span className={`schedule-item__info-block-span-first`}>{el.time.split(' ')[0]}</span>
                        <span className={`schedule-item__info-block-span-first`}>{el.time.split(' ')[1]}</span>
                    </p>
                    <span className={`schedule-item__info-block-span-second`}>{el.subject}</span>
                </div>
            ))}
        </div>
    );
};

export default ScheduleItem;