import React, { useEffect, useState } from 'react';
import './Filter.scss';

const filterImg = require('../../assets/images/FilterVec.svg').default;

interface IFilterItemInbox {
    id: number;
    name: string;
    active: boolean;
}
export interface IFilterItem {
    id: number;
    name: string;
    items: IFilterItemInbox[];
}

interface IFilter {
    items: IFilterItem[];
    onClick: (parentId: number, itemId: number) => void;
}

const Filter: React.FC<IFilter> = (props) => {
    const [items, setItems] = useState<IFilterItem[]>(props.items);
    const [active, setActive] = useState(false);
    const [hoveredParentId, setHoveredParentId] = useState<number | null>(null);

    useEffect(() => {
        setItems(props.items);
    }, [props.items]);

    return (
        <div
            className={'filter-block'}
            onMouseOver={() => setActive(true)}
            onMouseLeave={() => {
                setActive(false);
                setHoveredParentId(null);
            }}
        >
            <img className={'filter-block__img'} src={filterImg} alt="Filter img" />
            <p className="filter-block__text">Фильтр</p>
            <div
                className={`filter-block-hidden ${active ? 'filter-block-visible' : ''}`}
            >
                {items.map((parent) => (
                    <div
                        key={parent.id}
                        className="filter-block-hidden-item"
                        onMouseOver={() => setHoveredParentId(parent.id)}
                        onMouseLeave={() => setHoveredParentId(null)}
                    >
                        <span>{parent.name}</span>
                        {hoveredParentId === parent.id && parent.items.map((item) => (
                            <p
                                key={item.id}
                                className="filter-block-hidden-item-inner"
                                onClick={() => props.onClick(parent.id, item.id)}
                            >
                                {item.name}
                            </p>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Filter;
