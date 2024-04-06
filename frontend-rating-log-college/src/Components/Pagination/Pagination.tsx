import React, {CSSProperties, useEffect, useState} from 'react';
import './Pagination.scss';
export interface IDataArrayItem{
    id: number,
    isActive: boolean,
    date: string,
    number: string,
}

export interface IPagination{
    items: IDataArrayItem[]
    onChange: (value: number) => void;
    styles?: CSSProperties
}
const Pagination: React.FC<IPagination> = (props) => {
    let [dateArray, setDateArray] = useState<IDataArrayItem[]>(props.items)

    useEffect(() => {
        setDateArray(props.items)
    }, [props]);

    function changeCurrentPage(id: number){
        props.onChange(id);

        let dataArr = [...dateArray];

        let newArr = dataArr.map((el, index)=>{
            el.isActive = el.id === id;
            return el;
        })
    }

    return (
        <div className={'pagination-block'} style={props.styles}>
            {dateArray.map((el, index) =>(
                <button onClick={(e)=>{
                    changeCurrentPage(el.id)
                }} className={`pagination-item ${el.isActive ? 'pagination-item-active' : ''}`} key={el.id}>
                    <p className={`pagination-item__title`}>{el.number}</p>
                    <p className={`pagination-item__text`}>{el.date}</p>
                </button>
            ))}
        </div>
    );
};

export default Pagination;