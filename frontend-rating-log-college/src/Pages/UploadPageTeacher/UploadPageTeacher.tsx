import React, { useEffect, useState } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import './UploadPageTeacher.scss';
import FileUploader from "../../Components/FileUploader/FileUploader";
import { addNewFiles } from "../../Http/Courses";
import HomeWorkTeacherItem from "../../Components/HomeWorkTeacherItem/HomeWorkTeacherItem";
import { ICurrentHomeWork, IHomeWorks } from "../HomeWorksPageTeacher/HomeWorksPageTeacher";
import {
    addFileToTask,
    addNewHomeWork,
    getAllAboutGroupAndSubjects,
    getStudentHomeWork,
    getStudentHomeWorks
} from "../../Http/HomeWorks";
import ToggleBtns, { IToggleBtnsItems } from "../../Components/ToggleBtns/ToggleBtns";
import DatePicker, { registerLocale } from "react-datepicker";
import { format } from "date-fns";
import { ru } from 'date-fns/locale'

registerLocale('ru', ru);

const UploadPageTeacher: React.FC = () => {

    let [groups, setGroups] = useState<IToggleBtnsItems[]>([]);
    let [selectedGroup, setSelectedGroup] = useState<number>();
    let [homeWorks, setHoweWorks] = useState<IHomeWorks[]>([]);
    let [curHomeWorks, setCurHomeWorks] = useState<ICurrentHomeWork>();
    let [groups2, setGroups2] = useState<IToggleBtnsItems[]>([]);
    let [selectedGroup2, setSelectedGroup2] = useState<number>();
    let [subjects, setSubjects] = useState<IToggleBtnsItems[]>([]);
    let [selectedSubjects, setSelectedSubjects] = useState<string>('');
    const [formattedDate, setFormattedDate] = useState(format(new Date(), 'yyyy-MM-dd'));
    const [formattedDate2, setFormattedDate2] = useState(format(new Date(), 'yyyy-MM-dd'));
    let [curHwName, setCurHwName] = useState('')
    let[hwName, setHwName] = useState('')
    let[hwDesk, setHwDesk] = useState('')

    const [isFocused, setIsFocused] = useState(false);
    const [isFocusedDesk, setIsFocusedDesk] = useState(false);

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

    const addNewFile = async (files: File[]) => {
        const formData = new FormData();

        files.forEach((file) => {
            formData.append('files', file, file.name);
        });
        formData.append('id', String(selectedGroup));

        try {
            const response = await addNewFiles(formData);
        } catch (error) {
        }
    };

    const uploadHwFiles = async (files: File[]) => {
        const formData = new FormData();

        files.forEach((file) => {
            formData.append('files', file, file.name);
        });

        try {
            const response = await addFileToTask(curHomeWorks? curHomeWorks.id : 1,formData);
        } catch (error) {
        }
    };

    useEffect(() => {
        getStudentHomeWorks().then((response) => {
            let newArr = response.data.map((el: any, index: any) => {
                let newObj = {
                    id: el.idWork,
                    name: el.name,
                    subject: el.nameSubject,
                    group: el.groupName,
                    date: el.dateEnd,
                    count: el.completedCount,
                    active: index === 0
                }
                return newObj;
            });
            setHoweWorks(newArr);
            updateCurrentHomeWork(newArr[0].id);
        }).catch((error) => { });
        getAllAboutGroupAndSubjects().then((response) => {
            let newArr = response.data.groupsStudyDTOS.map((el: any) => {
                let newObj = {
                    active: false,
                    id: el.id,
                    name: el.name
                }
                return newObj;
            });
            setGroups(newArr);
            setGroups2(newArr);

            let newSubjectArr = response.data.subjectStudyDTOS.map((el: any) => {
                let newObj = {
                    active: false,
                    id: el.id,
                    name: el.name
                }
                return newObj;
            });
            setSubjects(newSubjectArr);
        }).catch((error) => { });

    }, []);

    function updateCurrentGroup(id: number) {
        let newArr: IToggleBtnsItems[] = groups.map((el: any) => {
            let newObj = {
                active: el.id === id,
                id: el.id,
                name: el.name
            }
            return newObj;
        });
        setGroups(newArr);
        setSelectedGroup(id);
    }

    function updateCurrentSubjects(id: number) {
        let curNameSub: string = '';
        let newArr: IToggleBtnsItems[] = subjects.map((el: any) => {
            let newObj = {
                active: el.id === id,
                id: el.id,
                name: el.name
            }
            if (el.id === id) {
                curNameSub = el.name;
            }
            return newObj;
        });
        setSubjects(newArr);
        setSelectedSubjects(curNameSub);
    }

    function updateCurrentGroupSec(id: number) {
        let curGroupId = 0;
        let newArr: IToggleBtnsItems[] = groups2.map((el: any) => {
            let newObj = {
                active: el.id === id,
                id: el.id,
                name: el.name
            }
            if(el.id === id){
                curGroupId = el.id
            }
            return newObj;
        });

        setGroups2(newArr);
        setSelectedGroup2(id);
    }

    const updateCurrentHomeWork = (id: number) => {
        getStudentHomeWork(id, 'Сдано').then((response) => {
            let newArr = response.data.students.map((el: any) => {
                let newOBj = {
                    name: el.name,
                    files: [
                        {
                            fileBase64: '',
                            name: '1.png'
                        }
                    ],
                    id: el.id,
                    grade: el.ball,
                }
                return newOBj;
            });
            let newObj: ICurrentHomeWork = {
                id: response.data.idWork,
                name: response.data.name,
                subject: response.data.nameSubject,
                group: response.data.groupName,
                count: response.data.completedCount,
                date: response.data.dateEnd,
                students: newArr
            }

            setCurHomeWorks(newObj);
        }).catch((error) => {

        });
    }

    const setNewCurrentHw = (id: number) => {
        let curHwId = 0;
        let newArr = homeWorks.map((el: any) => {
            el.active = el.id === id;
            if (el.id === id) {
                curHwId = el.id;
            }
            return el;
        });
        setHoweWorks(newArr);
        updateCurrentHomeWork(curHwId);
        updateCurrentHomeWork(id);
    }

    function sendHw(){
        addNewHomeWork(selectedGroup2 ? selectedGroup2 : 1, formattedDate, formattedDate2, hwName, hwDesk, selectedSubjects).then((response)=>{
            getStudentHomeWorks().then((response) => {
                let newArr = response.data.map((el: any, index: any) => {
                    let newObj = {
                        id: el.idWork,
                        name: el.name,
                        subject: el.nameSubject,
                        group: el.groupName,
                        date: el.dateEnd,
                        count: el.completedCount,
                        active: index === 0
                    }
                    return newObj;
                });
                setHoweWorks(newArr);
                updateCurrentHomeWork(newArr[0].id);
            }).catch((error) => { });
        }).catch((error)=>{})

    }


    return (
        <div className={'main-page'}>
            <div className={'block-left block-left-add'}>
                <div className="block-left-header">
                    <p className={'block-left__text'}>
                        Добавление файла к группе
                    </p>
                    <div className="block-left-header-personal block-left-header-personal-upload">
                        <div className="toble-btn-box">
                            <ToggleBtns items={groups} onClick={updateCurrentGroup} />
                        </div>
                        <br />
                        <FileUploader onClick={addNewFile} multipart={false} />
                    </div>

                </div>


            </div>
            <div className={'block-middle block-middle-add'}>
                <p className={'block-middle__text'}>
                    Текущие задания
                </p>

                <div className="homeworks-box homeworks-box-u">
                    {homeWorks.length > 0 ? homeWorks.map((el: any, index: any) => (
                        <HomeWorkTeacherItem onClick={setNewCurrentHw} id={el.id} name={el.name} count={el.count} active={el.active} subject={el.subject} group={el.group} date={el.date} key={index} />
                    )) : ''}
                </div>

            </div>
            <div className={'block-right block-right-add'}>
                <p className={'block-right__text'}>
                    Добавить домашние задание
                </p>
                <ToggleBtns items={groups2} onClick={updateCurrentGroupSec} />
                <ToggleBtns items={subjects} onClick={updateCurrentSubjects} />
              <div className="date-wp">
                  <div className="custom-datepicker-wrapper">
                      <DatePicker
                          selected={formattedDate ? new Date(formattedDate) : null}
                          onChange={handleDateChange}
                          dateFormat="yyyy-MM-dd"
                          className="custom-datepicker"
                          onKeyDown={(e) => e.preventDefault()}
                          minDate={new Date(new Date().setDate(new Date().getDate() - 1))}
                          locale="ru"
                      />
                  </div>
                  <div className="custom-datepicker-wrapper" style={{marginLeft: 15}}>
                      <DatePicker
                          selected={formattedDate2 ? new Date(formattedDate2) : null}
                          onChange={handleDateChangeSec}
                          dateFormat="yyyy-MM-dd"
                          className="custom-datepicker"
                          onKeyDown={(e) => e.preventDefault()}
                          minDate={new Date()}
                          locale="ru"
                      />
                  </div>
              </div>
                <br/>
                <div className="upload-container">
                    <input
                        type="text"
                        value={hwName}
                        onFocus={() => setIsFocused(true)}
                        onBlur={() => setIsFocused(false)}
                        onChange={(e) => setHwName(e.target.value)}
                    />
                    <label
                        className={`upload-placeholder ${isFocused || hwName ? "active" : ""}`}
                    >
                        Название работы
                    </label>
                </div>
                <br/>
                <div className="textarea-container">
                    <label className={`textarea-text`}>Описание работы</label>
      <textarea
          value={hwDesk}
          onFocus={() => setIsFocusedDesk(true)}
          onBlur={() => setIsFocusedDesk(false)}
          onChange={(e) => setHwDesk(e.target.value)}
      />

                </div>

                <button className={'upl-bn'} onClick={(e)=>{
                   if(hwName.length > 0 && hwDesk.length > 0 && formattedDate && formattedDate2) {
                       sendHw()
                   }
                }}>Добавить задание </button>

                <p className={'block-right__text block-right__text-upl'}>
                    Добавить файл к заданию
                </p>
                <div style={{width: 350}}>
                    <FileUploader onClick={uploadHwFiles} multipart={true} />
                </div>



            </div>
        </div>
    );
};

export default UploadPageTeacher;
