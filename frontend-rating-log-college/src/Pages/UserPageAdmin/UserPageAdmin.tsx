import React, {useState} from 'react';
import './UserPageAdmin.scss';
import DatePicker, {registerLocale} from "react-datepicker";
import { format } from "date-fns";
import { ru } from 'date-fns/locale'

registerLocale('ru', ru);

const UserPageAdmin: React.FC = () => {
    let [teacherName, setTeacherName] = useState('')
    let [teacherMiddleName, setMiddleName] = useState('')
    let [teacherLastName, setLastName] = useState('')

    const [formattedDate, setFormattedDate] = useState(format(new Date(), 'yyyy-MM-dd'));
    const [formattedDate2, setFormattedDate2] = useState(format(new Date(), 'yyyy-MM-dd'));
    const handleDateChange = (date: Date | null) => {
        if (date) {
            const newFormattedDate = format(date, 'yyyy-MM-dd');
            setFormattedDate(newFormattedDate);
        } else {
            setFormattedDate('');
        }
    };

    const handleDateChangeSec = (date: Date | null) => {
        if (date) {
            const newFormattedDate = format(date, 'yyyy-MM-dd');
            setFormattedDate2(newFormattedDate);
        } else {
            setFormattedDate2('');
        }
    };


    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-admin'}>
                <p className={'block-middle__text'}>
                    Добавление данных
                </p>
               <div className="info-cont">
                   <div className="admin-upload-container">
                       <label
                           className={`upload-placeholder-admin`}
                       >
                           Имя учителя
                       </label>
                       <input
                           type="text"
                           value={teacherName}
                           onChange={(e) => setTeacherName(e.target.value)}/>

                   </div>
                   <div className="admin-upload-container">
                       <label
                           className={`upload-placeholder-admin`}
                       >
                           Фамилия учителя
                       </label>
                       <input
                           type="text"
                           value={teacherMiddleName}
                           onChange={(e) => setMiddleName(e.target.value)}/>

                   </div>
                   <div className="admin-upload-container">
                       <label
                           className={`upload-placeholder-admin`}
                       >
                           Отчество учителя
                       </label>
                       <input
                           type="text"
                           value={teacherLastName}
                           onChange={(e) => setLastName(e.target.value)}/>

                   </div>
                   <div className="admin-upload-container">
                       <label
                           className={`upload-placeholder-admin`}
                       >
                           Имя учителя
                       </label>
                       <input
                           type="text"
                           value={teacherName}
                           onChange={(e) => setTeacherName(e.target.value)}/>

                   </div>
                   <div className="admin-upload-container">
                       <label className={`upload-placeholder-admin`}>Имя учителя</label>
                       <input
                           type="text"
                           value={teacherName}
                           onChange={(e) => setTeacherName(e.target.value)}/>

                   </div>
                   <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin">
                       <label className={`upload-placeholder-admin`}>Дата рождения</label>
                       <DatePicker
                           selected={formattedDate ? new Date(formattedDate) : null}
                           onChange={handleDateChange}
                           dateFormat="yyyy-MM-dd"
                           className="custom-datepicker"
                           locale="ru"
                       />
                   </div>
                   <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin">
                       <label className={`upload-placeholder-admin`}>Дата начала работы</label>
                       <DatePicker
                           selected={formattedDate2 ? new Date(formattedDate2) : null}
                           onChange={handleDateChangeSec}
                           dateFormat="yyyy-MM-dd"
                           className="custom-datepicker"
                           locale="ru"
                       />
                   </div>
               </div>

            </div>
        </div>
    );
};

export default UserPageAdmin;