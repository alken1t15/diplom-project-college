import React, {useEffect, useState} from 'react';

import './StudentWithoutCertificate.scss';
import InitialsImage from "../InitialsImage/InitialsImage";
import CheckBox from "../../UI/checkBox/checkBox";
import {useTranslation} from "react-i18next";
export interface IStudentWithoutCertificateItem{
    name: string;
    count: number;
    status: boolean;
    id: number;
    idCertificate?: any;
}

interface IForStudent{
    items: IStudentWithoutCertificateItem;
    onChange: (id: number, value: boolean) => void;
}

const StudentWithoutCertificate: React.FC<IForStudent> = (props) => {
    let[name, setName] = useState(props.items.name);
    let[count, setCount] = useState(props.items.count);
    let[status, setStatus] = useState(props.items.status);
    let[id, setId] = useState(props.items.id);
    let[cert, setCrt] = useState(props.items.idCertificate);

    useEffect(()=>{
        setName(props.items.name)
        setCount(props.items.count)
        setId(props.items.id)
        setCrt(props.items.idCertificate)
        console.log(props.items.idCertificate)
    }, [props])

    function updateStatus(value: boolean){
        props.onChange(id, value)
        setStatus(value)
    }

    const { t } = useTranslation();
    return (
        <div className={`studentWithoutCertificate-block`}>
            <div className={`studentWithoutCertificate-block-l`}>
                <div className="studentWithoutCertificate-block-img">
                    <InitialsImage initials={`${name.split(' ')[0][0]}${name.split(' ')[1][0]}`} width={50} height={50} fontSize={24} textColor="#fff" backgroundColor="#d9d9d9" />
                </div>
                <div className="studentWithoutCertificate-block-text">
                    <p className="studentWithoutCertificate-block-text__name">{name}</p>
                    <p className="studentWithoutCertificate-block-text__count">{t('omissionCount')}:&nbsp;<span>{count}</span></p>
                </div>

            </div>
            {cert !== undefined  && cert !== null ? '' :
                <div className={`omis-block`}>
                    <div className={`omis-block__text`}>{status ? 'Присутствует' : 'Отсутствует'}:</div>
                    <CheckBox onChange={updateStatus} isActive={status}/>
                </div>}

        </div>
    );
};

export default StudentWithoutCertificate;