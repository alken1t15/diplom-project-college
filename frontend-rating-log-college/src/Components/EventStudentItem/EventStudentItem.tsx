import React, {useState} from 'react';
import './EventStudentItem.scss';
import InitialsImage from "../InitialsImage/InitialsImage";

export interface IEventStudentItem{
    id: number;
    count: number;
    name: string;
    bull: null | string;

}
interface EventStudent{
    items: IEventStudentItem;
    onChange: (id: number, value: string)=> void;
}
const EventStudentItem: React.FC<EventStudent> = (props) => {
    let [id, setId] = useState(props.items.id)
    let [count, setCount] = useState(props.items.count)
    let [name, setName] = useState(props.items.name)
    let [grade, setGrade] = useState(props.items.bull)
    let [inputValue, setInputValue] = useState(String(props.items.bull))

    const validateAndSetInput = (value: string) => {
        let numberValue = Number(value);
        if (isNaN(numberValue)) {

        } else if (numberValue < 0 || numberValue > 100) {

        } else {
            setInputValue(String(numberValue));
            props.onChange(id, String(numberValue))
        }
    };


    return (
        <div className={`event-student-item`}>
            <div className="event-student-item-l">
                <InitialsImage initials={`${name.split(' ')[0][0]}${name.split(' ')[1][0]}`} width={50} height={50} fontSize={24} textColor="#fff" backgroundColor="#d9d9d9" />
            </div>
            <div className="event-student-item-r">
                <p className="event-student-item-r__name">{name}</p>
                <p className="event-student-item-r__ommis">Пропусков:&nbsp;<span>{count}</span></p>
            </div>
            <input type="number" value={inputValue} onChange={(e)=>{
                validateAndSetInput(e.target.value)
            }} placeholder={grade === null ? 'Не выставленно' : ''} className="grade-input"/>

        </div>
    );
};

export default EventStudentItem;