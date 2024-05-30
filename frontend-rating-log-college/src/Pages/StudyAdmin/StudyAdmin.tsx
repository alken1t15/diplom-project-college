import React, { useEffect, useState } from 'react';
import './StudyAdmin.scss';
import {addGroup, addSemestr, createAuditorium} from "../../Http/Admin";
import {
    getAllSpec,
    getAllSubjects,
    getAllTeachers,
    getAllGroups,
    getAllTime,
    getAllDays,
    getAllCur,
    getAllAudit,
    getInfoAboutGroup
} from "../../Http/AdditionalHttp";
import Dropdown from "../../UI/Dropdown/Dropdown";
import DatePicker from "react-datepicker";
import {format} from "date-fns";

const StudyAdmin: React.FC = () => {
    const [teachers, setTeachers] = useState<{ id: number, name: string }[]>([]);
    const [groups, setGroups] = useState<{ id: number, name: string }[]>([]);
    const [subjects, setSubjects] = useState<{ id: number, name: string }[]>([]);
    const [timeSlots, setTimeSlots] = useState<{ id: number, name: string }[]>([]);
    const [days, setDays] = useState<{ id: number, name: string }[]>([]);
    const [curators, setCurators] = useState<{ id: number, name: string }[]>([]);
    const [auditoriums, setAuditoriums] = useState<{ id: number, name: string }[]>([]);
    const [curTeacher, setCurTeacher] = useState<number | null>(null);
    const [curGroup, setCurGroup] = useState<number | null>(null);
    const [curSubject, setCurSubject] = useState<number | null>(null);
    const [curTimeSlot, setCurTimeSlot] = useState<number | null>(null);
    const [curDay, setCurDay] = useState<number | null>(null);
    const [curCurator, setCurCurator] = useState<number | null>(null);
    const [curAuditorium, setCurAuditorium] = useState<number | null>(null);
    const [specializations, setSpecializations] = useState<{ id: number, name: string }[]>([]);
    const [curSpecialization, setCurSpecialization] = useState<number | null>(null);
    const [groupValue, setGroupValue] = useState('');
    const [block, setBlock] = useState('');
    const [floor, setFloor] = useState('');
    const [audit, setAudit] = useState('');
    const [semNum, setSemNum] = useState('');
    const [courNum, setCourNum] = useState('');
    const [formattedDate, setFormattedDate] = useState(format(new Date(), 'yyyy-MM-dd'));
    const [formattedDate2, setFormattedDate2] = useState(format(new Date(), 'yyyy-MM-dd'));
    const [studyInfo, setStudyInfo] = useState<{ id: number, name: string }[]>([])
    const [curStudyInfo, setCurStudyInfo] = useState<number | null>(null);
    const [typeStudy, setTypeStudy] = useState<{ id: number, name: string }[]>([])
    const [curTypeStudy, setCurTypeStudy] = useState<number | null>(null);

    useEffect(() => {
        getAllTeachers().then((response: any) => {
            const newTeachers = response.data.map((el: any) => ({
                id: el.id,
                name: `${el.secondName} ${el.firstName} ${el.middleName}`
            }));
            setTeachers(newTeachers);
        }).catch((error) => {});

        getAllGroups().then((response: any) => {
            const newGroups = response.data.map((el: any) => ({
                id: el.id,
                name: el.name
            }));
            setGroups(newGroups);
        }).catch((error) => {});

        getAllSubjects().then((response: any) => {
            const newSubjects = response.data.map((el: any) => ({
                id: el.id,
                name: el.name
            }));
            setSubjects(newSubjects);
        }).catch((error) => {});

        getAllTime().then((response: any) => {
            const newTimeSlots = response.data.map((el: any) => ({
                id: el.id,
                name: `${el.startLesson.slice(0,5)} - ${el.endLesson.slice(0,5)}`
            }));
            setTimeSlots(newTimeSlots);
        }).catch((error) => {});

        getAllDays().then((response: any) => {
            const newDays = response.data.map((el: any) => ({
                id: el.id,
                name: el.name
            }));
            setDays(newDays);
        }).catch((error) => {});

        getAllCur().then((response: any) => {
            const newCurators = response.data.map((el: any) => ({
                id: el.id,
                name: `${el.teacher.secondName} ${el.teacher.firstName} ${el.teacher.middleName}`
            }));
            setCurators(newCurators);
        }).catch((error) => {});

        getAllAudit().then((response: any) => {
            const newAuditoriums = response.data.map((el: any) => ({
                id: el.id,
                name: `${el.block} блок , ${el.floor} этаж, ${el.cabinet} аудитория`
            }));
            setAuditoriums(newAuditoriums);
        }).catch((error) => {});

        getAllSpec().then((response: any) => {
            const newSpecializations = response.data.map((el: any) => ({
                id: el.id,
                name: el.name
            }));
            setSpecializations(newSpecializations);
        }).catch((error) => {});
    }, []);

    useEffect(()=>{
        if(curGroup){
            getInfoAboutGroup().then((response: any) => {

                const newStud: {id: number, name: string}[] = [];
                response.data.forEach((elem: any) => {
                    if(elem.id === curGroup){
                        elem.semestersInfo.forEach((el: any)=>{

                             let newObj = {
                                 id: el.id,
                                 name: `${el.course} курс, ${el.semester} семестр, ${el.dateStart} - ${el.dateEnd}`
                             }
                            newStud.push(newObj)

                        })

                    }
                });
                setStudyInfo(newStud);
            }).catch((error) => {});
        }
    }, [curGroup])

    useEffect(()=>{

    }, [curStudyInfo])

    const handleSelectTeacher = (id: number) => setCurTeacher(id);
    const handleSelectGroup = (id: number) => setCurGroup(id);
    const handleSelectSubject = (id: number) => setCurSubject(id);
    const handleSelectTimeSlot = (id: number) => setCurTimeSlot(id);
    const handleSelectDay = (id: number) => setCurDay(id);
    const handleSelectCurator = (id: number) => setCurCurator(id);
    const handleSelectAuditorium = (id: number) => setCurAuditorium(id);
    const handleSelectSpecialization = (id: number) => setCurSpecialization(id);
    const handleSelectStudyInfo = (id: number) => setCurStudyInfo(id);
    const handleSelectTypeStudyInfo = (id: number) => setCurStudyInfo(id);

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
                <p className={'block-middle__text'}>Добавление данных</p>

              <div className="drop-container">
                  <div className="drop-block">
                      <p className="drop-block__text">Все преподаватели</p>
                      <Dropdown
                          items={teachers}
                          selectedId={curTeacher}
                          placeholder="Выберите преподавателя"
                          onSelect={handleSelectTeacher}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">Все предметы</p>
                      <Dropdown
                          items={subjects}
                          selectedId={curSubject}
                          placeholder="Выберите предмет"
                          onSelect={handleSelectSubject}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">Все временные слоты</p>
                      <Dropdown
                          items={timeSlots}
                          selectedId={curTimeSlot}
                          placeholder="Выберите временной слот"
                          onSelect={handleSelectTimeSlot}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">Все дни недели</p>
                      <Dropdown
                          items={days}
                          selectedId={curDay}
                          placeholder="Выберите день недели"
                          onSelect={handleSelectDay}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">Все кураторы</p>
                      <Dropdown
                          items={curators}
                          selectedId={curCurator}
                          placeholder="Выберите куратора"
                          onSelect={handleSelectCurator}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">Все аудитории</p>
                      <Dropdown
                          items={auditoriums}
                          selectedId={curAuditorium}
                          placeholder="Выберите аудиторию"
                          onSelect={handleSelectAuditorium}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">Все специализации</p>
                      <Dropdown
                          items={specializations}
                          selectedId={curSpecialization}
                          placeholder="Выберите специализацию"
                          onSelect={handleSelectSpecialization}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">Все группы</p>
                      <Dropdown
                          items={groups}
                          selectedId={curGroup}
                          placeholder="Выберите группу"
                          onSelect={handleSelectGroup}
                      />
                  </div>
                  {studyInfo?.length > 0 ?
                      <div className="drop-block">
                          <p className="drop-block__text">Все данные о группе</p>
                          <Dropdown
                              items={studyInfo}
                              selectedId={curStudyInfo}
                              placeholder="Выберите семестр"
                              onSelect={handleSelectStudyInfo}
                          />
                      </div>
                      : ""
                  }
                  {typeStudy?.length > 0 ?
                      <div className="drop-block">
                          <p className="drop-block__text">Все данные о группе</p>
                          <Dropdown
                              items={typeStudy}
                              selectedId={curTypeStudy}
                              placeholder="Выберите тип учебы"
                              onSelect={handleSelectTypeStudyInfo}
                          />
                      </div>
                      : ""
                  }


              </div>

               <div className="infos-block">

                   <div className="info-container info-container-st" style={{width: 350}}>
                       <p className="info-container__name">Создание группы</p>

                       <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                           <label className={`upload-placeholder-admin`}>Выберите куратора и специализацию выше</label>
                           <label className={`upload-placeholder-admin`}>Напишите название группы ниже</label>
                           <input
                               type="text"
                               value={groupValue}
                               onChange={(e) => setGroupValue(e.target.value)}/>


                       </div>
                       <div className="btn-cont">
                           <button className={`admin-add-btn`} onClick={(e)=>{
                               if(curCurator && groupValue && curSpecialization) {
                                   addGroup(curCurator, groupValue,curSpecialization).then((response)=>{
                                       getAllGroups().then((response: any) => {
                                           const newGroups = response.data.map((el: any) => ({
                                               id: el.id,
                                               name: el.name
                                           }));
                                           setGroups(newGroups);
                                       }).catch((error) => {});
                                   }).catch((error)=>{})
                               }

                           }}>Добавить группу</button>
                       </div>
                   </div>

                   <div className="info-container info-container-st" style={{width: 571}} >
                       <p className="info-container__name">Создание аудитории</p>
                       <label className={`upload-placeholder-admin`}>&nbsp;</label>
                       <div className="inner-container" >
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                               <label className={`upload-placeholder-admin`}>Номер блока</label>
                               <input  type="text"  value={block}  onChange={(e) => setBlock(e.target.value)}/>
                           </div>
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                               <label className={`upload-placeholder-admin`}>Номер этажа</label>
                               <input  type="text"  value={floor}  onChange={(e) => setFloor(e.target.value)}/>
                           </div>
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                               <label className={`upload-placeholder-admin`}>Номер аудитории</label>
                               <input  type="text"  value={audit}  onChange={(e) => setAudit(e.target.value)}/>
                           </div>
                       </div>
                       <div className="btn-cont">
                           <button className={`admin-add-btn`} onClick={(e)=>{
                               if(block && floor && audit) {
                                   createAuditorium(Number(block), Number(floor), Number(audit)).then((response)=>{
                                       getAllAudit().then((response: any) => {
                                           const newAuditoriums = response.data.map((el: any) => ({
                                               id: el.id,
                                               name: `${el.block} блок , ${el.floor} этаж, ${el.cabinet} аудитория`
                                           }));
                                           setAuditoriums(newAuditoriums);
                                       }).catch((error) => {});
                                   }).catch((error)=>{})
                               }

                           }}>Добавить аудиторию</button>
                       </div>
                   </div>

                   <div className="info-container info-container-st">
                       <p className="info-container__name">Добавить семестр</p>
                       <label className={`upload-placeholder-admin`} style={{marginLeft: 10}}>Выберите группу выше</label>
                       <div className="inner-container" >
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e" style={{width: 259}} >
                               <label className={`upload-placeholder-admin`}>Номер семестра</label>
                               <input  type="text"  value={semNum}  onChange={(e) => setSemNum(e.target.value)}/>
                           </div>
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e" style={{width: 259}}>
                               <label className={`upload-placeholder-admin`}>Номер курса</label>
                               <input  type="text"  value={courNum}  onChange={(e) => setCourNum(e.target.value)}/>
                           </div>
                           <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin custom-datepicker-wrapper-admin-stud">
                               <label className={`upload-placeholder-admin`}>Дата начала</label>
                               <DatePicker
                                   selected={formattedDate2 ? new Date(formattedDate2) : null}
                                   onChange={handleDateChangeSec}
                                   dateFormat="yyyy-MM-dd"
                                   className="custom-datepicker"
                                   locale="ru"
                               />
                           </div>
                           <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin custom-datepicker-wrapper-admin-stud">
                               <label className={`upload-placeholder-admin`}>Дата конца</label>
                               <DatePicker
                                   selected={formattedDate ? new Date(formattedDate) : null}
                                   onChange={handleDateChange}
                                   dateFormat="yyyy-MM-dd"
                                   className="custom-datepicker"
                                   locale="ru"
                               />
                           </div>
                       </div>
                       <div className="btn-cont">
                           <button className={`admin-add-btn`} onClick={(e)=>{
                               if(curGroup && semNum && courNum) {
                                   addSemestr(curGroup, semNum,courNum, formattedDate2, formattedDate).then((response)=>{
                                   }).catch((error)=>{})
                               }

                           }}>Добавить семестр</button>
                       </div>
                   </div>


               </div>

            </div>
        </div>
    );
};

export default StudyAdmin;
