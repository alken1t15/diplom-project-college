import React, {useEffect, useState} from 'react';
import './MainPageAdmin.scss';
import {addNewSpec, addNewSubject, setCurator} from "../../Http/Admin";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;
import {getAllSpec, getAllSubjects, getAllTeachers} from "../../Http/AdditionalHttp";
import ToggleBtns, {IToggleBtnsItems} from "../../Components/ToggleBtns/ToggleBtns";
import Spinner from "../../Components/Spinner/Spinner";
import {notify, Toasty} from "../../Components/Toasty/Toasty";

const MainPageAdmin: React.FC = () => {
    let[specialName, setSpecialName] = useState('');
    let[subjectName, setSubjectName] = useState('');
    let[curSpec, setCurSpec] = useState<IToggleBtnsItems[]>([]);
    let[curSubject, setCurSubject] = useState<IToggleBtnsItems[]>([]);
    let[teachers, setTeachers] = useState<IToggleBtnsItems[]>([]);
    let[curTeacher, setCurTeacher] = useState<number>();
    let[loading, setLoading] = useState(true);

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
                <p className={'block-middle__text'}>Добавление данных</p>
                <div className="info-container">
                    <p className="info-container__name">Добавление специальности</p>

                    <div className="admin-upload-container admin-upload-container-u">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            Название специальности
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
                                    notify('Специальность успешно добавлена','success')
                                }).catch((error)=>{
                                    notify('Не удалось добавить специальность','error')
                                })
                            }
                            else{
                                notify('Не удалось добавить специальность','error')
                            }
                        }}>Добавить специальность</button>
                    </div>
                </div>
                <div className="info-container">
                    <p className="info-container__name">Добавление предмета</p>
                    <div className="toble-btn-box">
                        <ToggleBtns items={curSubject} />
                    </div>
                    <div className="admin-upload-container admin-upload-container-u">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            Название предмета
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
                                    notify('Специальность успешно добавлена','success')
                                }).catch((error)=>{
                                    notify('Не удалось добавить название предмета','error')
                                })
                            }
                            else {
                                notify('Не удалось добавить название предмета','error')
                            }
                        }}>Добавить предмет</button>
                    </div>
                </div>
                <div className="info-container">
                    <p className="info-container__name">Прикрепление куратора</p>
                    <div className="toble-btn-box">
                        <ToggleBtns items={teachers} onClick={updateCurrentTeacher}/>
                    </div>
                    <div className="admin-upload-container admin-upload-container-u">
                        <label
                            className={`upload-placeholder-admin`}
                        >
                            Выберите преподавателя выше
                        </label>


                    </div>
                    <div className="btn-cont">
                        <button className={`admin-add-btn`} onClick={(e)=>{

                            setCurator(curTeacher ? curTeacher : 1).then((response)=>{
                                    getAllSubjects().then((response: any)=>{
                                        updateSubject(response.data)
                                    }).catch((error)=>{})
                                notify('Куратор успешно добавлен','success')
                                }).catch((error)=>{
                                notify('Не удалось добавить куратора','error')
                            })

                        }}>Закрепить</button>
                    </div>
                </div>
            </div>
            <Spinner loading={loading} />
            <Toasty/>
        </div>
    );
};

export default MainPageAdmin;