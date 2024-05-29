import React, {CSSProperties, useEffect, useState} from 'react';
import './ToggleBtns.scss';
import button from "../../UI/Button/Button";

export interface IToggleBtnsItems{
    name: string;
    active?: boolean;
    id: number;
}
export interface IToggleBtns{
    items: IToggleBtnsItems[];
    onClick?: (id: number) => void;
    style?: CSSProperties;
}
const ToggleBtns: React.FC<IToggleBtns> = (props) => {
    let [items, setItems] = useState(props.items)

    useEffect(()=>{
        setItems(props.items)
    },[props])

    return (
        <div className={`toggleBtns-box`} style={props.style}>
            {items.length > 0 ? items.map((el: any, index: any)=>(
                <button className={`toggleBtns-button ${el.active ? 'toggleBtns-button-a' : ''}`}
                onClick={(e)=>{
                    if(props.onClick) {
                        props.onClick(el.id)
                    }

                }}
                        key={index}
                >{el.name}</button>
            )) : ''}
        </div>
    );
};

export default ToggleBtns;