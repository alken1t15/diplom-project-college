import React, {useEffect, useState} from 'react';
import './UserPageAdmin.scss';
import DatePicker, {registerLocale} from "react-datepicker";
import { format } from "date-fns";
import { ru } from 'date-fns/locale'
import {addNewStudent, addNewStudentFromFile, addNewTeacher} from "../../Http/Admin";
import userEvent from "@testing-library/user-event";
import {getAllGroups} from "../../Http/AdditionalHttp";
import Dropdown from "../../UI/Dropdown/Dropdown";
import FileUploader from "../../Components/FileUploader/FileUploader";
import {addNewCertificate, mainPageData} from "../../Http/MainPage";
import {IDataArrayItem} from "../../Components/Pagination/Pagination";

registerLocale('ru', ru);

const UserPageAdmin: React.FC = () => {
    let [teacherName, setTeacherName] = useState('');
    let [teacherMiddleName, setMiddleName] = useState('');
    let [teacherLastName, setLastName] = useState('');
    let [teacherLogin, setTeacherLogin] = useState('');
    let [teacherPassword, setTeacherPassword] = useState('');
    let [formattedDate, setFormattedDate] = useState(format(new Date(), 'yyyy-MM-dd'));
    let [formattedDate2, setFormattedDate2] = useState(format(new Date(), 'yyyy-MM-dd'));


    let [studBorn, setStudBorn] = useState(format(new Date(), 'yyyy-MM-dd'));
    let [studFName, setStudFName] = useState('');
    let [studScName, setStudScName] = useState('');
    let [studMidName, setStudMidName] = useState('');
    let [studLogin, setStudLogin] = useState('');
    let [studPass, setStudPass] = useState('');
    const [groups, setGroups] = useState<{ id: number, name: string }[]>([]);
    const [curGroup, setCurGroup] = useState<number | null>(null);


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

    const handleSelectGroup = (id: number) => setCurGroup(id);

    const handleBornDateChange = (date: Date | null) => {
        if (date) {
            const newFormattedDate = format(date, 'yyyy-MM-dd');
            setStudBorn(newFormattedDate);
        } else {
            setStudBorn('');
        }
    };

    function addStudent(){
        addNewTeacher(teacherName, teacherLastName, teacherMiddleName, teacherLogin, teacherPassword, formattedDate, formattedDate2).then((response)=>{

        }).catch((error)=>{})

    }


    useEffect(()=>{
        getAllGroups().then((response: any) => {
            const newGroups = response.data.map((el: any) => ({
                id: el.id,
                name: el.name
            }));
            setGroups(newGroups);
        }).catch((error) => {});
    },[])

    const sendNewStud = async (files: File[]) => {
        const formData = new FormData();

        files.forEach((file) => {
            formData.append('files', file, file.name);
            formData.append('id', String(curGroup));

        });
        try {
            const response = await addNewStudentFromFile(formData).then((response)=>{

            }).catch((error)=>{

            })

        } catch (error) {
        }
    };

    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-admin'}>
                <p className={'block-middle__text'}>
                    Добавление преподавателя
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
                           Логин
                       </label>
                       <input
                           type="text"
                           value={teacherLogin}
                           onChange={(e) => setTeacherLogin(e.target.value)}/>

                   </div>
                   <div className="admin-upload-container">
                       <label className={`upload-placeholder-admin`}>Пароль</label>
                       <input
                           type="text"
                           value={teacherPassword}
                           onChange={(e) => setTeacherPassword(e.target.value)}/>

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
                   <div className="btn-cont">
                       <button className={`admin-add-btn`} onClick={(e)=>{
                           if(teacherName && teacherPassword && teacherLogin && teacherMiddleName && teacherLastName) {
                               addStudent()
                           }
                       }}>Создать нового учителя</button>
                   </div>
               </div>
                <p className={'block-middle__text'} style={{marginTop: 50}}>
                    Добавление студента
                </p>
                <div className="info-cont">
                    <div className="admin-upload-container">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            Имя студента
                        </label>
                        <input
                            type="text"
                            value={studFName}
                            onChange={(e) => setStudFName(e.target.value)}/>

                    </div>
                    <div className="admin-upload-container">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            Фамилия студента
                        </label>
                        <input
                            type="text"
                            value={studScName}
                            onChange={(e) => setStudScName(e.target.value)}/>

                    </div>
                    <div className="admin-upload-container">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            Отчество студента
                        </label>
                        <input
                            type="text"
                            value={studMidName}
                            onChange={(e) => setStudMidName(e.target.value)}/>

                    </div>
                    <div className="admin-upload-container">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            Логин
                        </label>
                        <input
                            type="text"
                            value={studLogin}
                            onChange={(e) => setStudLogin(e.target.value)}/>

                    </div>
                    <div className="admin-upload-container">
                        <label className={`upload-placeholder-admin`}>Пароль</label>
                        <input
                            type="text"
                            value={studPass}
                            onChange={(e) => setStudPass(e.target.value)}/>

                    </div>
                    <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin">
                        <label className={`upload-placeholder-admin`}>Дата рождения</label>
                        <DatePicker
                            selected={studBorn ? new Date(studBorn) : null}
                            onChange={handleBornDateChange}
                            dateFormat="yyyy-MM-dd"
                            className="custom-datepicker"
                            locale="ru"
                        />
                    </div>
                    <div className="drop-block" style={{marginLeft: 10, marginTop: 10}}>
                        <p className="drop-block__text">Все группы</p>
                        <Dropdown
                            items={groups}
                            selectedId={curGroup}
                            placeholder="Выберите группу"
                            onSelect={handleSelectGroup}
                        />
                    </div>
                    <div className="btn-cont">
                        <button className={`admin-add-btn`} onClick={(e)=>{
                            if(studFName && studScName && studMidName && studLogin && studPass && studBorn && curGroup) {
                                addNewStudent(studFName, studScName, studMidName, studLogin, studPass, studBorn, curGroup).then((response)=>{

                                }).catch((error)=>{})
                            }
                        }}>Создать нового студента</button>
                    </div>
                </div>


                <p className={'block-middle__text'} style={{marginTop: 50}}>
                    Добавление студента с помощью файла
                </p>
                <div className="info-cont">
                    <div className="drop-block" style={{marginLeft: 10, marginTop: 10, width: '100%'}}>
                        <p className="drop-block__text">Все группы</p>
                        <Dropdown
                            items={groups}
                            selectedId={curGroup}
                            placeholder="Выберите группу"
                            onSelect={handleSelectGroup}
                        />
                    </div>
                    <div className="upl-cont" style={{width: 450, marginTop: 15}}>
                        <FileUploader onClick={sendNewStud} multipart={false}/>
                    </div>

                </div>

            </div>
        </div>
    );
};

export default UserPageAdmin;