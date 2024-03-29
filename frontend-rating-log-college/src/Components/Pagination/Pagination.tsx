import React, {CSSProperties, useEffect, useState} from 'react';
import './Pagination.scss';
interface IDataArrayItem{
    id: number,
    isActive: boolean,
    date: string,
    number: string,
}

interface IPagination{
    onChange: (value: number) => void;
    styles?: CSSProperties
}
const Pagination: React.FC<IPagination> = (props) => {
    let [dataArray, setDataArray] = useState<IDataArrayItem[]>([
        {
            id: 1,
            isActive: true,
            date: 'Янв',
            number: "01"
        },
        {
            id: 2,
            isActive: false,
            date: 'Фев',
            number: "02"
        },
        {
            id: 3,
            isActive: false,
            date: 'Март',
            number: "03"
        },
        {
            id: 4,
            isActive: false,
            date: 'Апр',
            number: "04"
        },
        {
            id: 5,
            isActive: false,
            date: 'Май',
            number: "05"
        },
        {
            id: 6,
            isActive: false,
            date: 'Июн',
            number: "06"
        },
        {
            id: 9,
            isActive: false,
            date: 'Сен',
            number: "09"
        },
        {
            id: 10,
            isActive: false,
            date: 'Окт',
            number: "10"
        },
        {
            id: 11,
            isActive: false,
            date: 'Нояб',
            number: "11"
        },
        {
            id: 12,
            isActive: false,
            date: 'Дек',
            number: "12"
        },
    ])

    function changeCurrentPage(id: number){
        props.onChange(id);

        let dataArr = [...dataArray];

        let newArr = dataArr.map((el, index)=>{
            el.isActive = el.id === id;
            return el;
        })
    }

    return (
        <div className={'pagination-block'} style={props.styles}>
            {dataArray.map((el, index) =>(
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