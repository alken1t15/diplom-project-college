import React, {useState} from 'react';
import './Filter.scss'

const filterImg = require('../../assets/images/FilterVec.svg').default;

interface IFilterItemInbox{
    id: number;
    name: string;
    active: boolean;
}
export interface IFilterItem{
    id: number;
    name: string;
    items: IFilterItemInbox[]
}

interface IFilter{
    items: IFilterItem[],
    onCLick: (parentId: number, itemId: number) => void;
}
const Filter: React.FC<IFilter> = (props) => {

    let [items, setItems] = useState<IFilterItem[]>(props.items)
    let [active, setActive] = useState(false)

    return (
        <div className={'filter-block'}
             onMouseOver={(e) => { setActive(true); }}
             onMouseLeave={(e) => { setActive(false); }}
        >
            <img className={'filter-block__img'} src={filterImg} alt="Filter img"/>
            <p className="filter-block__text">Фильтр</p>
            <div className={`filter-block-hidden ${active ? 'filter-block-visible' : ''}`}>
                {items ? items.map((el, index) => (
                    <p className="filter-block-hidden-item" key={index}>
                        <span>{el.name}</span>
                    </p>
                )) : <></>}
            </div>

        </div>
    );
};

export default Filter;