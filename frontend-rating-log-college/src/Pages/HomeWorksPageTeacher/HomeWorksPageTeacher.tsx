import React, { useEffect, useState } from 'react';
import './HomeWorksPageTeacher.scss';
import Filter, { IFilterItem } from "../../Components/Filter/Filter";
import Search from "../../UI/Search/Search";
import HomeWorkTeacherItem from "../../Components/HomeWorkTeacherItem/HomeWorkTeacherItem";
import Slider from "../../Components/Slider/Slider";
import CurrentStudentHomeWork, { IFile } from "../../Components/CurrentStudentHomeWork/CurrentStudentHomeWork";
import { getStudentHomeWork, getStudentHomeWorks, sendNewGrade, sendRepeatHw } from "../../Http/HomeWorks";
import Spinner from "../../Components/Spinner/Spinner";
import {notify, Toasty} from "../../Components/Toasty/Toasty";
import {useTranslation} from "react-i18next";

export interface ISchedule {
    groupName: string;
    time: string;
    subject: string;
    active?: boolean;
}

export interface IHomeWorks {
    id: number;
    name: string;
    subject: string;
    group: string;
    date: string;
    count: string;
    active: boolean;
}

interface IStudentHomeWorks {
    name: string;
    files: IFile[];
    id: number;
    grade: number | null;
    repeat: boolean;
}

export interface ICurrentHomeWork {
    id: number;
    name: string;
    subject: string;
    date: string;
    group: string;
    count: string;
    students: IStudentHomeWorks[];
}

const HomeWorksPageTeacher: React.FC = () => {
    let [filterItems, setFilterItems] = useState<IFilterItem[]>([
        {
            id: 1,
            name: 'Предмет',
            items: [
                {
                    id: 1,
                    name: 'Веб-программирование',
                    active: false,
                }
            ]
        },
        {
            id: 2,
            name: 'Группа',
            items: [
                {
                    id: 1,
                    name: 'П-20-51б',
                    active: false,
                },
                {
                    id: 2,
                    name: 'П-20-53к',
                    active: false,
                },
            ]
        },
    ]);
    let [searchText, setSearchText] = useState('');
    const { t } = useTranslation();
    let [homeWorks, setHomeWorks] = useState<IHomeWorks[]>([]);
    let [curHomeWorks, setCurHomeWorks] = useState<ICurrentHomeWork>();
    let [slider, setSlider] = useState([
        {
            id: 1,
            name: `${t('sliderWithOutGrades')}`,
            active: true,
        },
        {
            id: 2,
            name: `${t('sliderWithGrades')}`,
            active: false,
        },
    ]);
    let [curSlider, setCurSlider] = useState(1);
    let [curId, setCurId] = useState<number>();
    let [curName, setCurName] = useState<string>();
    let [curSubject, setCurSubject] = useState<string>();
    let [curGroup, setCurGroup] = useState<string>();
    let [curCount, setCurCount] = useState<string>();
    let [curDate, setCurDate] = useState<string>();
    let [curStudents, setCurStudents] = useState<IStudentHomeWorks[]>([]);
    let [loading, setLoading] = useState(true)

    useEffect(() => {
        getStudentHomeWorks().then((response) => {
            const newArr = response.data.map((el: any, index: any) => ({
                id: el.idWork,
                name: el.name,
                subject: el.nameSubject,
                group: el.groupName,
                date: el.dateEnd,
                count: el.completedCount,
                active: index === 0,
            }));
            setHomeWorks(newArr);
            if (newArr.length > 0) {
                setCurId(newArr[0].id);
            }
            setTimeout(() => setLoading(false), 700);
        }).catch((error) => {
        });
    }, []);

    useEffect(() => {
        if (curId !== undefined) {
            updateCurrentHomeWork(curId);
        }
    }, [curId, curSlider]);

    const updateCurrentHomeWork = (id: number) => {
        getStudentHomeWork(id, curSlider === 1 ? 'Сдано' : 'Проверено').then((response) => {
            const newArr = response.data.students.map((el: any) => {
                const newArrFiles = el.files.map((el1: any) => ({
                    fileBase64: el1.file,
                    name: el1.name,
                }));
                return {
                    name: el.name,
                    files: newArrFiles,
                    id: el.id,
                    grade: el.ball,
                    repeat: curSlider === 2,
                };
            });

            setCurHomeWorks({
                id: response.data.idWork,
                name: response.data.name,
                subject: response.data.nameSubject,
                group: response.data.groupName,
                date: response.data.dateEnd,
                count: response.data.completedCount,
                students: newArr,
            });
            setCurName(response.data.name);
            setCurSubject(response.data.nameSubject);
            setCurGroup(response.data.groupName);
            setCurCount(response.data.completedCount);
            setCurDate(response.data.dateEnd);
            setCurStudents(newArr);
        }).catch((error) => {
            console.error(error);
        });
    };

    const setNewCurrentHw = (id: number) => {
        setHomeWorks((prevHomeWorks) => {
            const newArr = prevHomeWorks.map((el) => {
                el.active = el.id === id;
                return el;
            });
            return newArr;
        });
        setCurId(id);
    };

    const setCurrentFilter = (parentId: number, itemId: number) => {
        console.log(parentId, itemId);
    };

    const updateSearchText = (value: string) => {
        setSearchText(value);
    };

    const setSliderItems = (id: number) => {
        const newArr = slider.map((el) => {
            el.active = el.id === id;
            return el;
        });
        setSlider(newArr);
        setCurSlider(id);
    };

    const updateUserState = (id: number, value: string) => {
        if (value === 'пересдача') {
            sendRepeatHw(curHomeWorks ? curHomeWorks.id : 9999, id).then(() => {
                if (curHomeWorks) {
                    updateCurrentHomeWork(curId ? curId : 1);

                    getStudentHomeWorks().then((response) => {
                        const newArr = response.data.map((el: any, index: any) => ({
                            id: el.idWork,
                            name: el.name,
                            subject: el.nameSubject,
                            group: el.groupName,
                            date: el.dateEnd,
                            count: el.completedCount,
                            active: el.idWork === curId,
                        }));
                        setHomeWorks(newArr);
                        if (newArr.length > 0) {
                            setCurId(curId);
                        }
                    }).catch((error) => {});

                    notify(`${t('studentToRepeat')}`, 'success');

                }
            }).catch((error) => {
                notify(`${t('studentToRepeatError')}`, 'error');
            });
        } else if (Number(value)) {
            sendNewGrade(curHomeWorks ? curHomeWorks.id : 9999, id, Number(value)).then(() => {
                if (curHomeWorks) {
                    updateCurrentHomeWork(curId ? curId : 1);

                    getStudentHomeWorks().then((response) => {
                        const newArr = response.data.map((el: any, index: any) => {
                          let newObj = {id: el.idWork,
                              name: el.name,
                              subject: el.nameSubject,
                              group: el.groupName,
                              date: el.dateEnd,
                              count: el.completedCount,
                              active: el.idWork === curId};
                            return newObj

                        });

                        if (newArr.length > 0) {
                            setCurId(curId);
                        }

                    }).catch((error) => {});

                    notify(`${t('studentGrade')}`, 'success');
                }
            }).catch((error) => {
                notify(`${t('studentGradeError')}`, 'error');
            });
        }

    };

    return (
        <div className={'main-page main-page-t hw-page-t'}>
            <div className={'block-middle block-middle-t block-middle-t-hw'}>
                <p className={'block-middle__text block-middle__text-t'}>
                    Домашние задания
                </p>

                <div className="block-middle-top block-middle-top-hw-t">
                    {/*<Filter items={filterItems} onClick={setCurrentFilter} />*/}
                    {/*<Search onChange={updateSearchText} />*/}
                </div>

                <div className="homeworks-box">
                    {homeWorks.length > 0 ? homeWorks.map((el: any, index: any) => (
                        <HomeWorkTeacherItem onClick={setNewCurrentHw} id={el.id} name={el.name} count={el.count} active={el.active} subject={el.subject} group={el.group} date={el.date} key={index} />
                    )) : ''}
                </div>
            </div>
            <div className={'block-right block-right-t'} style={{ paddingTop: 40 }}>
                <p className={'block-right__text'} style={{ paddingBottom: 16 }}>
                    {curName}
                </p>
                <div className="homework-teacher-item-inner-block" style={{ marginTop: 15 }}>
                    <p className="homework-teacher-item-inner-block__title">{t('subject')}:</p>
                    <p className="homework-teacher-item-inner-block__text">{curSubject}</p>
                </div>
                <div className="homework-teacher-item-inner-block" style={{ marginTop: 15 }}>
                    <p className="homework-teacher-item-inner-block__title">{t('group')}:</p>
                    <p className="homework-teacher-item-inner-block__text">{curGroup}</p>
                </div>
                <div className="homework-teacher-item-inner-block" style={{ marginTop: 25 }}>
                    <p className="homework-teacher-item-inner-block__text homework-teacher-item-inner-block__text-u">{t('completed')}: {curCount}</p>
                </div>
                <div className="homework-teacher-item-inner-block" style={{ marginTop: 10 }}>
                    <p className="homework-teacher-item-inner-block__text homework-teacher-item-inner-block__text-u">
                        <span className={`expires-block`}>{t('expiresTo')}:</span>
                        <span className={`expires-block__text`}>&nbsp;{curDate}</span></p>
                </div>

                <Slider items={slider} onChange={setSliderItems} styles={{ marginTop: 27 }} />

                <div className="current-student-box">
                    {curStudents.length > 0 ? curStudents.map((el: any, index: any) => (
                        <CurrentStudentHomeWork name={el.name} file={el.files} id={el.id} grade={el.grade} onClick={updateUserState} key={index} />
                    )) : ''}
                </div>
            </div>
            <Spinner loading={loading} />
            <Toasty/>
        </div>
    );
};

export default HomeWorksPageTeacher;
