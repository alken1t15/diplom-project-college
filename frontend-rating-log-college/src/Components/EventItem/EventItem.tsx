import React from 'react';
import './EventItem.scss'
import {IEventItem} from "../../Pages/EventPageTeacher/EventPageTeacher";
const EventItem: React.FC<IEventItem> = (props) => {
    return (
        <div className={`event-item`}>
            <div className="event-item-l">{props.dateStart}</div>
            <div className="event-item-r">
                <p className="event-item-r-top">{props.name}</p>
                <p className="event-item-r-middle">{props.dateStart} - {props.dateEnd}</p>
                <p className="event-item-r-bot">{props.group}</p>
            </div>
        </div>
    );
};

export default EventItem;