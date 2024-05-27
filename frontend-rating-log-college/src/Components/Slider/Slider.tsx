import React, {CSSProperties, useEffect, useState} from 'react';

import './Slider.scss';
export interface ISliderItem{
    id: number;
    name: string;
    active: boolean;
}
interface ISlider{
    items: ISliderItem[];
    onChange: (id: number) => void;
    styles? : CSSProperties
}
const Slider: React.FC<ISlider> = (props) => {
    let[items, setItems] = useState(props.items);

    useEffect(()=>{
        setItems(props.items)
    }, [props])

    return (
        <div className={`slider-box`} style={props.styles}>
            {items.map((el: any, index: any)=>(
                <button className={`slider-item ${el.active ? 'slider-item-a' : ''}`}
                onClick={(e)=>{
                    props.onChange(el.id)
                }}
               style={{ marginLeft: index !== 0 ? `15px` : 0 }}
                key={index}
                >{el.name}</button>
            ))}
        </div>
    );
};

export default Slider;