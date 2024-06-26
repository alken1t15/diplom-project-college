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
import Spinner from "../../Components/Spinner/Spinner";
import {notify, Toasty} from "../../Components/Toasty/Toasty";
import {useTranslation} from "react-i18next";

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

    let[loading, setLoading] = useState(true)

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
    const { t } = useTranslation();
    function addStudent(){
        addNewTeacher(teacherName, teacherLastName, teacherMiddleName, teacherLogin, teacherPassword, formattedDate, formattedDate2).then((response)=>{
            notify(`${t('teacherSuc')}`,'success')
        }).catch((error)=>{
            notify(`${t('teacherErr')}`,'error')
        })
    }


    useEffect(()=>{
        getAllGroups().then((response: any) => {
            const newGroups = response.data.map((el: any) => ({
                id: el.id,
                name: el.name
            }));
            setGroups(newGroups);
        }).catch((error) => {});
        setTimeout(() => setLoading(false), 700);
    },[])

    const sendNewStud = async (files: File[]) => {
        const formData = new FormData();

        files.forEach((file) => {
            formData.append('files', file, file.name);
            formData.append('id', String(curGroup));

        });
        try {
            const response = await addNewStudentFromFile(formData).then((response)=>{
                notify('Студент успешно добавлен','success')
            }).catch((error)=>{
                notify(`${t('studSuc')}`,'error')
            })

        } catch (error) {
            notify(`${t('studErr')}`,'error')
        }
    };

    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-admin'}>
                <p className={'block-middle__text'}>
                    {t('addTeach')}
                </p>
               <div className="info-cont">
                   <div className="admin-upload-container">
                       <label
                           className={`upload-placeholder-admin`}
                       >
                           {t('teachName')}
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
                           {t('teachLastName')}
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
                           {t('teachMidName')}
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
                           {t('login')}
                       </label>
                       <input
                           type="text"
                           value={teacherLogin}
                           onChange={(e) => setTeacherLogin(e.target.value)}/>

                   </div>
                   <div className="admin-upload-container">
                       <label className={`upload-placeholder-admin`}>
                           {t('password')}
                       </label>
                       <input
                           type="text"
                           value={teacherPassword}
                           onChange={(e) => setTeacherPassword(e.target.value)}/>

                   </div>
                   <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin">
                       <label className={`upload-placeholder-admin`}>{t('bornDate')}</label>
                       <DatePicker
                           selected={formattedDate ? new Date(formattedDate) : null}
                           onChange={handleDateChange}
                           dateFormat="yyyy-MM-dd"
                           className="custom-datepicker"
                           locale="ru"
                       />
                   </div>
                   <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin">
                       <label className={`upload-placeholder-admin`}>{t('yearDates')}</label>
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
                           else{
                               notify(`${t('emptyFields')}`,'error')
                           }

                       }}>{t('addNewTeach')}</button>
                   </div>
               </div>
                <p className={'block-middle__text'} style={{marginTop: 50}}>
                    {t('addStud')}
                </p>
                <div className="info-cont">
                    <div className="admin-upload-container">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            {t('studFName')}
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
                            {t('studLName')}
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
                            {t('studMName')}
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
                            {t('login')}
                        </label>
                        <input
                            type="text"
                            value={studLogin}
                            onChange={(e) => setStudLogin(e.target.value)}/>

                    </div>
                    <div className="admin-upload-container">
                        <label className={`upload-placeholder-admin`}> {t('password')}</label>
                        <input
                            type="text"
                            value={studPass}
                            onChange={(e) => setStudPass(e.target.value)}/>

                    </div>
                    <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin">
                        <label className={`upload-placeholder-admin`}>{t('bornDate')}</label>
                        <DatePicker
                            selected={studBorn ? new Date(studBorn) : null}
                            onChange={handleBornDateChange}
                            dateFormat="yyyy-MM-dd"
                            className="custom-datepicker"
                            locale="ru"
                        />
                    </div>
                    <div className="drop-block" style={{marginLeft: 10, marginTop: 10}}>
                        <p className="drop-block__text">{t('allGroups')}</p>
                        <Dropdown
                            items={groups}
                            selectedId={curGroup}
                            placeholder={t('selGroupForStud')}
                            onSelect={handleSelectGroup}
                        />
                    </div>
                    <div className="btn-cont">
                        <button className={`admin-add-btn`} onClick={(e)=>{
                            if(studFName && studScName && studMidName && studLogin && studPass && studBorn && curGroup) {
                                addNewStudent(studFName, studScName, studMidName, studLogin, studPass, studBorn, curGroup).then((response)=>{
                                    notify(`${t('studSuccess')}`,'success')
                                }).catch((error)=>{
                                    notify(`${t('studError')}`,'error')
                                })
                            }
                            else{
                                notify(`${t('emptyFields')}`,'error')
                            }
                        }}>{t('createStudBtn')}</button>
                    </div>
                </div>


                <p className={'block-middle__text'} style={{marginTop: 50}}>
                    {t('createStudWitchFile')}
                </p>
                <div className="info-cont">
                    <div className="drop-block" style={{marginLeft: 10, marginTop: 10, width: '100%'}}>
                        <p className="drop-block__text">{t('allGroups')}</p>
                        <Dropdown
                            items={groups}
                            selectedId={curGroup}
                            placeholder={t('selGroupForStud')}
                            onSelect={handleSelectGroup}
                        />
                    </div>
                    <div className="upl-cont" style={{width: 450, marginTop: 15}}>
                        <FileUploader onClick={sendNewStud} multipart={false}/>
                    </div>

                </div>

            </div>
            <Spinner loading={loading} />
            <Toasty/>
        </div>
    );
};

export default UserPageAdmin;