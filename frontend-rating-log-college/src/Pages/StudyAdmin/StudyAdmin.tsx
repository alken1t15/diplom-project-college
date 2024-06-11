import React, { useEffect, useState } from 'react';
import './StudyAdmin.scss';
import {addGroup, addSemestr, addSubjectForStud, addTypeStudy, createAuditorium} from "../../Http/Admin";
import {
    getAllSpec,
    getAllSubjects,
    getAllTeachers,
    getAllGroups,
    getAllTime,
    getAllDays,
    getAllCur,
    getAllAudit,
    getInfoAboutGroup, getInfoAboutTypeStudyGroup
} from "../../Http/AdditionalHttp";
import Dropdown from "../../UI/Dropdown/Dropdown";
import DatePicker from "react-datepicker";
import {format} from "date-fns";
import {notify, Toasty} from "../../Components/Toasty/Toasty";
import Spinner from "../../Components/Spinner/Spinner";
import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectLanguage} from "../../Store/Selectors/authSelectors";

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
    const [studyInfo, setStudyInfo] = useState<{ id: number, name: string }[]>([]);
    const [curStudyInfo, setCurStudyInfo] = useState<number | null>(null);
    const [typeStudy, setTypeStudy] = useState<{ id: number, name: string }[]>([]);
    const [curTypeStudy, setCurTypeStudy] = useState<number | null>(null);
    const [curGroupSemestr, setCurGroupSemestr] = useState<number>();
    const [typeStart, setTypeStart] = useState(format(new Date(), 'yyyy-MM-dd'));
    const [typeEnd, setTypeEnd] = useState(format(new Date(), 'yyyy-MM-dd'));
    const [typeName, setTypeName] = useState('');
    const [numbCouple, setNumbCouple] = useState('');
    const [loading ,setLoading] = useState(true)
    const { t } = useTranslation();
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
                name: `${el.block} ${t('block')} , ${el.floor} ${t('floor')}, ${el.cabinet} ${t('audit')}`
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
        setTimeout(() => setLoading(false), 700);
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
                                 name: `${el.course} ${t('courseM')}, ${el.semester} ${t('semestr')}, ${el.dateStart} - ${el.dateEnd}`
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
        if(curGroupSemestr){
            getInfoAboutTypeStudyGroup(curGroupSemestr).then((response)=>{
                let newArr = response.data.map((el: any)=>{
                    let newOjb = {
                        id: el.id,
                        name: `${el.name}, ${el.dateStart} - ${el.dateEnd}`
                    }
                    return newOjb;
                })
                setTypeStudy(newArr)
            }).catch((error)=>{})
        }


    }, [curGroupSemestr])

    const handleSelectTeacher = (id: number) => setCurTeacher(id);
    const handleSelectGroup = (id: number) => setCurGroup(id);
    const handleSelectSubject = (id: number) => setCurSubject(id);
    const handleSelectTimeSlot = (id: number) => setCurTimeSlot(id);
    const handleSelectDay = (id: number) => setCurDay(id);
    const handleSelectCurator = (id: number) => setCurCurator(id);
    const handleSelectAuditorium = (id: number) => setCurAuditorium(id);
    const handleSelectSpecialization = (id: number) => setCurSpecialization(id);
    const handleSelectStudyInfo = (id: number) => {
        setCurStudyInfo(id)
        setCurGroupSemestr(id)
    }
    const handleSelectTypeStudyInfo = (id: number) => setCurTypeStudy(id);

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

    const handleDateTypeStart = (date: Date | null) => {
        if (date) {
            const newFormattedDate = format(date, 'yyyy-MM-dd');
            setTypeStart(newFormattedDate);
        } else {
            setTypeStart('');
        }
    };

    const handleDateTypeEnd = (date: Date | null) => {
        if (date) {
            const newFormattedDate = format(date, 'yyyy-MM-dd');
            setTypeEnd(newFormattedDate);
        } else {
            setTypeEnd('');
        }
    };


    return (
        <div className={'main-page'}>
            <div className={'block-middle block-middle-admin'}>
                <p className={'block-middle__text'}>{t('dataAdd')}</p>

              <div className="drop-container">
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allTeachers')}</p>
                      <Dropdown
                          items={teachers}
                          selectedId={curTeacher}
                          placeholder={t('selectTeachers')}
                          onSelect={handleSelectTeacher}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allTeachersSubjects')}</p>
                      <Dropdown
                          items={subjects}
                          selectedId={curSubject}
                          placeholder={t('selectSubjects')}
                          onSelect={handleSelectSubject}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allTimeSlots')}</p>
                      <Dropdown
                          items={timeSlots}
                          selectedId={curTimeSlot}
                          placeholder={t('selectTimeSlots')}
                          onSelect={handleSelectTimeSlot}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allDays')}</p>
                      <Dropdown
                          items={days}
                          selectedId={curDay}
                          placeholder={t('selectDays')}
                          onSelect={handleSelectDay}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allCurators')}</p>
                      <Dropdown
                          items={curators}
                          selectedId={curCurator}
                          placeholder={t('selectCurator')}
                          onSelect={handleSelectCurator}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allAudit')}</p>
                      <Dropdown
                          items={auditoriums}
                          selectedId={curAuditorium}
                          placeholder={t('selectAudit')}
                          onSelect={handleSelectAuditorium}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allSpec')}</p>
                      <Dropdown
                          items={specializations}
                          selectedId={curSpecialization}
                          placeholder={t('selectSpec')}
                          onSelect={handleSelectSpecialization}
                      />
                  </div>
                  <div className="drop-block">
                      <p className="drop-block__text">{t('allGroups')}</p>
                      <Dropdown
                          items={groups}
                          selectedId={curGroup}
                          placeholder={t('selectGroup')}
                          onSelect={handleSelectGroup}
                      />
                  </div>
                  {studyInfo?.length > 0 ?
                      <div className="drop-block">
                          <p className="drop-block__text">{t('allInfoGroups')}</p>
                          <Dropdown
                              items={studyInfo}
                              selectedId={curStudyInfo}
                              placeholder={t('selectInfoGroup')}
                              onSelect={handleSelectStudyInfo}
                          />
                      </div>
                      : ""
                  }
                  {typeStudy?.length > 0 ?
                      <div className="drop-block">
                          <p className="drop-block__text">{t('allTypeGroups')}</p>
                          <Dropdown
                              items={typeStudy}
                              selectedId={curTypeStudy}
                              placeholder={t('selectTypeGroup')}
                              onSelect={handleSelectTypeStudyInfo}
                          />
                      </div>
                      : ""
                  }


              </div>

               <div className="infos-block">

                   <div className="info-container info-container-st" style={{width: 350}}>
                       <p className="info-container__name">{t('addGroup')}</p>

                       <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                           <label className={`upload-placeholder-admin`}>{t('selectCurAndSpec')}</label>
                           <label className={`upload-placeholder-admin`}>{t('groupName')}</label>
                           <input type="text" value={groupValue} onChange={(e) => setGroupValue(e.target.value)}/>
                       </div>
                       <div className="btn-cont">
                           <button className={`admin-add-btn`} onClick={(e)=>{
                               if(curCurator && groupValue && curSpecialization) {
                                   addGroup(curCurator, groupValue,curSpecialization).then((response)=>{
                                       notify(`${t('groupSuccess')}`,'success')
                                       getAllGroups().then((response: any) => {
                                           const newGroups = response.data.map((el: any) => ({
                                               id: el.id,
                                               name: el.name
                                           }));
                                           setGroups(newGroups);
                                       }).catch((error) => {});

                                   }).catch((error)=>{
                                       notify(`${t('groupError')}`,'error')
                                   })
                               }
                               else{
                                   notify(`${t('groupData')}`,'error')
                               }

                           }}>{t('addGroupBtn')}</button>
                       </div>
                   </div>

                   <div className="info-container info-container-st" style={{width: 446}} >
                       <p className="info-container__name">{t('addAudit')}</p>
                       <label className={`upload-placeholder-admin`}>&nbsp;</label>
                       <div className="inner-container" >
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                               <label className={`upload-placeholder-admin`}>{t('numBlock')}</label>
                               <input  type="text"  value={block}  onChange={(e) => setBlock(e.target.value)}/>
                           </div>
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                               <label className={`upload-placeholder-admin`}>{t('numFloor')}</label>
                               <input  type="text"  value={floor}  onChange={(e) => setFloor(e.target.value)}/>
                           </div>
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e">
                               <label className={`upload-placeholder-admin`}>{t('numAudit')}</label>
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
                                               name: `${el.block} ${t('block')} , ${el.floor} ${t('floor')}, ${el.cabinet} ${t('audit')}`
                                           }));
                                           setAuditoriums(newAuditoriums);
                                           notify(`${t('auditSuccess')}`,'success')
                                       }).catch((error) => {});
                                   }).catch((error)=>{
                                       notify(`${t('auditError')}`,'error')
                                   })
                               }
                                else{
                                    notify(`${t('auditData')}`,'error')
                               }
                           }}>{t('addAuditBtn')}</button>
                       </div>
                   </div>

                   <div className="info-container info-container-st" style={{width: 774}}>
                       <p className="info-container__name">{t('addSemest')}</p>
                       <label className={`upload-placeholder-admin`} style={{marginLeft: 10}}>{t('selectGroupUpper')}</label>
                       <div className="inner-container" >
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e" style={{width: 259}} >
                               <label className={`upload-placeholder-admin`}>{t('numSemest')}</label>
                               <input  type="text"  value={semNum}  onChange={(e) => setSemNum(e.target.value)}/>
                           </div>
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e" style={{width: 259}}>
                               <label className={`upload-placeholder-admin`}>{t('numCourse')}</label>
                               <input  type="text"  value={courNum}  onChange={(e) => setCourNum(e.target.value)}/>
                           </div>
                           <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin custom-datepicker-wrapper-admin-stud">
                               <label className={`upload-placeholder-admin`}>{t('dateStart')}</label>
                               <DatePicker
                                   selected={formattedDate2 ? new Date(formattedDate2) : null}
                                   onChange={handleDateChangeSec}
                                   dateFormat="yyyy-MM-dd"
                                   className="custom-datepicker"
                                   locale="ru"
                               />
                           </div>
                           <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin custom-datepicker-wrapper-admin-stud">
                               <label className={`upload-placeholder-admin`}>{t('dateEnd')}</label>
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
                                       notify(`${t('semSuccess')}`,'success')
                                   }).catch((error)=>{
                                       notify(`${t('semError')}`,'error')
                                   })
                               }
                               else {
                                   notify(`${t('auditData')}`,'error')
                               }

                           }}>{t('addSemBtn')}</button>
                       </div>
                   </div>

                   <div className="info-container info-container-st">
                       <p className="info-container__name">{t('addTypeStudy')}</p>
                       <label className={`upload-placeholder-admin`} style={{marginLeft: 10}}>{t('selectCurSem')}</label>
                       <div className="inner-container" >
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e" style={{width: 259}} >
                               <label className={`upload-placeholder-admin`}>{t('typeText')}</label>
                               <input  type="text"  value={typeName}  onChange={(e) => setTypeName(e.target.value)}/>
                           </div>
                           <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin custom-datepicker-wrapper-admin-stud">
                               <label className={`upload-placeholder-admin`}>{t('dateStart')}</label>
                               <DatePicker
                                   selected={typeStart ? new Date(typeStart) : null}
                                   onChange={handleDateTypeStart}
                                   dateFormat="yyyy-MM-dd"
                                   className="custom-datepicker"
                                   locale="ru"
                               />
                           </div>
                           <div className="custom-datepicker-wrapper custom-datepicker-wrapper-admin custom-datepicker-wrapper-admin-stud">
                               <label className={`upload-placeholder-admin`}>{t('dateEnd')}</label>
                               <DatePicker
                                   selected={typeEnd ? new Date(typeEnd) : null}
                                   onChange={handleDateTypeEnd}
                                   dateFormat="yyyy-MM-dd"
                                   className="custom-datepicker"
                                   locale="ru"
                               />
                           </div>
                       </div>
                       <div className="btn-cont">
                           <button className={`admin-add-btn`} onClick={(e)=>{
                               if(curStudyInfo) {
                                   addTypeStudy(curStudyInfo, typeName, typeStart, typeEnd).then((response)=>{
                                       notify(`${t('studyTypeSuccess')}`,'success')
                                   }).catch((error)=>{
                                       notify(`${t('studyTypeError')}`,'error')
                                   })
                               }
                               else{
                                   notify(`${t('auditData')}`,'error')
                               }
                           }}>{t('studyTypeBtn')}</button>
                       </div>
                   </div>

                   <div className="info-container info-container-st" style={{width: 450}}>
                       <p className="info-container__name">{t('addSubjectGroup')}</p>
                       <label className={`upload-placeholder-admin`} style={{marginLeft: 10}}>{t('selectForSubject')}</label>
                       <div className="inner-container" >
                           <div className="admin-upload-container admin-upload-container-u admin-upload-container-u-e" style={{width: 259}} >
                               <label className={`upload-placeholder-admin`}>{t('countCouple')}</label>
                               <input  type="text"  value={numbCouple}  onChange={(e) => setNumbCouple(e.target.value)}/>
                           </div>
                       </div>
                       <div className="btn-cont">
                           <button className={`admin-add-btn`} onClick={(e)=>{
                               if(numbCouple && curTypeStudy && curTimeSlot && curSubject && curTeacher && curAuditorium && curDay) {
                                   addSubjectForStud(curTypeStudy, curTimeSlot, curSubject, curTeacher, curAuditorium, curDay, Number(numbCouple)).then((response)=>{
                                       notify(`${t('subjGroupSuccess')}`,'success')
                                   }).catch((error)=>{
                                       notify(`${t('subjGroupError')}`,'error')
                                   })
                               }

                               else{
                                   notify(`${t('auditData')}`,'error')
                               }
                           }}>{t('addGroupSubjBtn')}</button>
                       </div>
                   </div>

               </div>

            </div>
            <Spinner loading={loading} />
            <Toasty/>
        </div>
    );
};

export default StudyAdmin;
