import React, {useEffect, useState} from 'react';

import './StudentWithoutCertificate.scss';
import InitialsImage from "../InitialsImage/InitialsImage";
interface IStudentWithoutCertificateItem{
    name: string,
    count: number,
    status: boolean
}
interface IStudentWithoutCertificateBlock{
    item: IStudentWithoutCertificateItem
}
const StudentWithoutCertificate: React.FC<IStudentWithoutCertificateBlock> = (props) => {
    let[item, setItem] = useState(props.item);

    useEffect(()=>{
        setItem(props.item)
    }, [props])
    return (
        <div className={`studentWithoutCertificate-block`}>
            {/*<InitialsImage initials={(userName ? userName.split(' ')[0][0]: '') + (userName ? userName.split(' ')[1][0] : '')}  width={70} height={70} fontSize={30} textColor="#fff" backgroundColor="#d9d9d9" />*/}
        </div>
    );
};

export default StudentWithoutCertificate;