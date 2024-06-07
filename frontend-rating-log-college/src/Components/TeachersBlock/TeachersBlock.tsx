import React, {CSSProperties, useEffect, useState} from 'react';
import InitialsImage from "../InitialsImage/InitialsImage";
import './TeachersBlock.scss'
import {useTranslation} from "react-i18next";

export interface ITeachersItem{
    name: string;
    subject: string;
}

interface ITeachersBlock{
    item: ITeachersItem,
    styles?: CSSProperties
}
const TeachersBlock: React.FC<ITeachersBlock> = (props) => {
    let[item, setItem] = useState<ITeachersItem>(props.item);

    useEffect(()=>{
        setItem(props.item)
    }, [props])

    const { t } = useTranslation();

    return (
        <div className={`teachers-block`} style={props.styles}>
            <div className="teachers-block-left">
                <InitialsImage
                    initials={String(item.name.split(' ')[0].slice(0, 1)) + String(item.name.split(' ')[1].slice(0, 1))}
                    width={50} height={50} fontSize={24} textColor="#fff" backgroundColor="#d9d9d9"/>
            </div>
            <div className="teachers-block-right">
                <p className={`teachers-block-right__title`}>{item.name}</p>
                <p className={`teachers-block-right__text`}>{t('subject')}: {item.subject}</p>
            </div>
        </div>
    );
};

export default TeachersBlock;