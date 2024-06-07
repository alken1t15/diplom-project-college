import React, {useEffect, useState} from 'react';
import './MainPageAdmin.scss';
import {addNewSpec, addNewSubject, setCurator} from "../../Http/Admin";
import {Simulate} from "react-dom/test-utils";
import {getAllSpec, getAllSubjects, getAllTeachers} from "../../Http/AdditionalHttp";
import ToggleBtns, {IToggleBtnsItems} from "../../Components/ToggleBtns/ToggleBtns";
import Spinner from "../../Components/Spinner/Spinner";
import {notify, Toasty} from "../../Components/Toasty/Toasty";
import {useTranslation} from "react-i18next";
const MainPageAdmin: React.FC = () => {
    let[specialName, setSpecialName] = useState('');
    let[subjectName, setSubjectName] = useState('');
    let[curSpec, setCurSpec] = useState<IToggleBtnsItems[]>([]);
    let[curSubject, setCurSubject] = useState<IToggleBtnsItems[]>([]);
    let[teachers, setTeachers] = useState<IToggleBtnsItems[]>([]);
    let[curTeacher, setCurTeacher] = useState<number>();
    let[loading, setLoading] = useState(true);
    const { t } = useTranslation();
    function updateSpec(data: object[]){
        let newArr = data.map((el: any)=>{
            let newObj = {
                id: el.id,
                name: el.name
            }
            return newObj;
        })
        setCurSpec(newArr)
    }
    function updateSubject(data: object[]){
        let newArr = data.map((el: any)=>{
            let newObj = {
                id: el.id,
                name: el.name
            }
            return newObj;
        })
        setCurSubject(newArr)
    }
    function updateCurrentTeacher(id: number){
        let newArr = teachers.map((el: any)=>{
            let newObj = {
                active: el.id === id,
                id: el.id,
                name: el.name
            }
            return newObj;
        })
        setCurTeacher(id)
        setTeachers(newArr)
    }
    useEffect(()=>{
        getAllSpec().then((response: any)=>{
            updateSpec(response.data)
        }).catch((error)=>{})
        getAllSubjects().then((response: any)=>{
            updateSubject(response.data)
        }).catch((error)=>{})
        getAllTeachers().then((response: any)=>{
            let newArr = response.data.map((el: any)=>{
                let newObj = {
                    active: false,
                    id: el.id,
                    name: `${el.secondName} ${el.firstName} ${el.middleName}`
                }
                return newObj;
            })
            setTeachers(newArr)
        }).catch((error)=>{})
        setTimeout(() => setLoading(false), 700);
    },[])
    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-admin'}>
                <p className={'block-middle__text'}>{t('dataAdd')}</p>
                <div className="info-container">
                    <p className="info-container__name">{t('addSpecial')}</p>
                    <div className="admin-upload-container admin-upload-container-u">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            {t('specialName')}
                        </label>
                        <input
                            type="text"
                            value={specialName}
                            onChange={(e) => setSpecialName(e.target.value)}/>
                    </div>
                    <div className="btn-cont">
                        <button className={`admin-add-btn`} onClick={(e)=>{
                            if(specialName) {
                                addNewSpec(specialName).then((response)=>{
                                    getAllSpec().then((response: any)=>{
                                        updateSpec(response.data)
                                    }).catch((error)=>{})
                                    notify(`${t('specialSuccess')}`,'success')
                                }).catch((error)=>{
                                    notify(`${t('specialError')}`,'error')
                                })
                            }
                            else{
                                notify(`${t('specialError')}`,'error')
                            }
                        }}>{t('specialBtnAdd')}</button>
                    </div>
                </div>
                <div className="info-container">
                    <p className="info-container__name">{t('addSubject')}</p>
                    <div className="toble-btn-box">
                        <ToggleBtns items={curSubject} />
                    </div>
                    <div className="admin-upload-container admin-upload-container-u">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            {t('subjectName')}
                        </label>
                        <input
                            type="text"
                            value={subjectName}
                            onChange={(e) => setSubjectName(e.target.value)}/>
                    </div>
                    <div className="btn-cont">
                        <button className={`admin-add-btn`} onClick={(e)=>{
                            if(subjectName) {
                                addNewSubject(subjectName).then((response)=>{
                                    getAllSubjects().then((response: any)=>{
                                        updateSubject(response.data)
                                    }).catch((error)=>{})
                                    notify(`${t('subjectSuccess')}`,'success')
                                }).catch((error)=>{
                                    notify(`${t('subjectError')}`,'error')
                                })
                            }
                            else {
                                notify(`${t('subjectError')}`,'error')
                            }
                        }}>{t('addBtnSubj')}</button>
                    </div>
                </div>
                <div className="info-container">
                    <p className="info-container__name">{t('curatorAdd')}</p>
                    <div className="toble-btn-box">
                        <ToggleBtns items={teachers} onClick={updateCurrentTeacher}/>
                    </div>
                    <div className="admin-upload-container admin-upload-container-u">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            {t('selectTeacherUpper')}
                        </label>
                    </div>
                    <div className="btn-cont">
                        <button className={`admin-add-btn`} onClick={(e)=>{
                            setCurator(curTeacher ? curTeacher : 1).then((response)=>{
                                    getAllSubjects().then((response: any)=>{
                                        updateSubject(response.data)
                                    }).catch((error)=>{})
                                notify(`${t('curatorSuccess')}`,'success')
                                }).catch((error)=>{
                                notify(`${t('curatorError')}`,'error')
                            })
                        }}>{t('zakrep')}</button>
                    </div>
                </div>
            </div>
            <Spinner loading={loading} />
            <Toasty/>
        </div>
    );
};
export default MainPageAdmin;