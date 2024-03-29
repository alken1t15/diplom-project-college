import React, {CSSProperties, useEffect, useState} from 'react';
import './LatenessItem.scss';

interface ILateness{
    id: number,
    type: string,
    text: string,
    subject: string
}

export interface ITardinessItem{
    date: string;
    nameOfDay: string;
    tardiness: ILateness[];
    styles?: CSSProperties
}
const LatenessItem: React.FC<ITardinessItem> = (props) => {
    let [item, setItem] = useState(props);

    useEffect(()=>{
        setItem(props)
    }, [props])

    return (
        <div className="lateness-item" style={item.styles}>
            <p className="lateness-item__date">{item.date}</p>
            <p className="lateness-item__day">День недели: {item.nameOfDay}</p>
            {item.tardiness.map((el, index)=>(
                <div className={`lateness-item__info-block`} key={el.id}>
                    <span className={`lateness-item__info-block-span-first 
                    ${el.type == 'green' ? 'text-green' : 
                    el.type == 'yellow' ? 'text-yellow' :
                    el.type == 'cyan' ? 'text-cyan' :
                    el.type == 'red' ? 'text-red' : ''
                        
                    }`}>{el.text}</span>
                    <span className={`lateness-item__info-block-span-second`}>{el.subject}</span>
                </div>
            ))}
        </div>
    );
};

export default LatenessItem;