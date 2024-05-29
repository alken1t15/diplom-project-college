import React, { useEffect, useState } from 'react';
import './HomeWorksPageTeacher.scss';
import Filter, { IFilterItem } from "../../Components/Filter/Filter";
import Search from "../../UI/Search/Search";
import HomeWorkTeacherItem from "../../Components/HomeWorkTeacherItem/HomeWorkTeacherItem";
import Slider from "../../Components/Slider/Slider";
import CurrentStudentHomeWork, { IFile } from "../../Components/CurrentStudentHomeWork/CurrentStudentHomeWork";
import { getStudentHomeWork, getStudentHomeWorks, sendNewGrade, sendRepeatHw } from "../../Http/HomeWorks";

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
    const [filterItems, setFilterItems] = useState<IFilterItem[]>([
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

    const [searchText, setSearchText] = useState('');
    const [homeWorks, setHomeWorks] = useState<IHomeWorks[]>([]);
    const [curHomeWorks, setCurHomeWorks] = useState<ICurrentHomeWork>();
    const [slider, setSlider] = useState([
        {
            id: 1,
            name: 'Выставление оценок',
            active: true,
        },
        {
            id: 2,
            name: 'С оценками',
            active: false,
        },
    ]);
    const [curSlider, setCurSlider] = useState(1);

    const [curId, setCurId] = useState<number>();
    const [curName, setCurName] = useState<string>();
    const [curSubject, setCurSubject] = useState<string>();
    const [curGroup, setCurGroup] = useState<string>();
    const [curCount, setCurCount] = useState<string>();
    const [curDate, setCurDate] = useState<string>();
    const [curStudents, setCurStudents] = useState<IStudentHomeWorks[]>([]);

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
        }).catch((error) => {
            console.error(error);
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

                }
            }).catch((error) => {
                console.error(error);
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
                        console.log(newArr)
                        // setHomeWorks(newArr);
                        if (newArr.length > 0) {
                            setCurId(curId);
                        }
                    }).catch((error) => {});
                }
            }).catch((error) => {
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
                    <Filter items={filterItems} onClick={setCurrentFilter} />
                    <Search onChange={updateSearchText} style={{ marginLeft: 30 }} />
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
                    <p className="homework-teacher-item-inner-block__title">Предмет:</p>
                    <p className="homework-teacher-item-inner-block__text">{curSubject}</p>
                </div>
                <div className="homework-teacher-item-inner-block" style={{ marginTop: 15 }}>
                    <p className="homework-teacher-item-inner-block__title">Группа:</p>
                    <p className="homework-teacher-item-inner-block__text">{curGroup}</p>
                </div>
                <div className="homework-teacher-item-inner-block" style={{ marginTop: 25 }}>
                    <p className="homework-teacher-item-inner-block__text homework-teacher-item-inner-block__text-u">Выполнили: {curCount}</p>
                </div>
                <div className="homework-teacher-item-inner-block" style={{ marginTop: 10 }}>
                    <p className="homework-teacher-item-inner-block__text homework-teacher-item-inner-block__text-u">
                        <span className={`expires-block`}>Срок до:</span>
                        <span className={`expires-block__text`}>&nbsp;{curDate}</span></p>
                </div>

                <Slider items={slider} onChange={setSliderItems} styles={{ marginTop: 27 }} />

                <div className="current-student-box">
                    {curStudents.length > 0 ? curStudents.map((el: any, index: any) => (
                        <CurrentStudentHomeWork name={el.name} file={el.files} id={el.id} grade={el.grade} onClick={updateUserState} key={index} />
                    )) : ''}
                </div>
            </div>
        </div>
    );
};

export default HomeWorksPageTeacher;
