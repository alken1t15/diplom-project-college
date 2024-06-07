import React, {useEffect, useState} from 'react';
import './ScheduleTeacherBlock.scss';
import {ISchedule} from "../../Pages/MainPageTeacher/MainPageTeacher";
import {useTranslation} from "react-i18next";

export interface IScheduleTeacherBlock{
    groupName: string;
    time: string;
    subject: string;
    curTime: string;
    nextItem: ISchedule | undefined;
    prevItem: ISchedule | undefined;
    active?: boolean | undefined;
    last?: boolean;
}
const ScheduleTeacherBlock:React.FC<IScheduleTeacherBlock> = (props) => {
    let[groupName, setGroupName] = useState(props.groupName);
    let[time, SetTime] = useState(props.time);
    let[subject, setSubject] = useState(props.subject);
    let[curTime, setCurTime] = useState(props.curTime);
    let[nextItem, setNextItem] = useState<ISchedule | undefined>(props.nextItem);
    let[prevItem, SetPrevItem] = useState<ISchedule | undefined>(props.prevItem);
    let[active, setActive] = useState(props.active);
    let[timeLeft ,setTimeLeft] = useState('');
    let[last ,setLast] = useState(false);

    const { t } = useTranslation();

    function calculateTimeToNext(curTime: string, nextItem: any): string {
        if(nextItem){
            let curTimeSplit = curTime.split(':').map(Number);
            let nextTimeSplit = nextItem.time.split(':').map(Number);

            let curDateTime = new Date(0, 0, 0, curTimeSplit[0], curTimeSplit[1]);
            let nextDateTime = new Date(0, 0, 0, nextTimeSplit[0], nextTimeSplit[1]);

            let timeDifference = nextDateTime.getTime() - curDateTime.getTime();
            let hoursDifference = Math.floor(timeDifference / (1000 * 60 * 60));
            let minutesDifference = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
            if(timeDifference <= 0){
                return '';

            }
            if(hoursDifference === 0){
                if(minutesDifference <= 1){
                    return `${t('nextSubject')}`;
                }
                return `${t('nextSubjectMini')} ${minutesDifference} ${t('minutes')}`;
            }
            else{
                const hoursLimit = 24;

                if (hoursDifference >= hoursLimit) {
                    return `${t('toNextPare')} ${hoursLimit} ${t('hours')}`;
                } else {
                    const hoursText = hoursDifference === 1 ? `${t('hour')}` : hoursDifference > 1 && hoursDifference < 5 ? `${t('hourA')}` : `${t('hours')}`;
                    const minutesText = minutesDifference === 1 ? `${t('minutesA')}` : `${t('minutes')}`;

                    return `${t('nextSubjectMiniA')} ${hoursDifference} ${hoursText} ${t('and')} ${minutesDifference} ${minutesText}`;
                }

            }
        }

        return '';


    }

    useEffect(()=>{
        setCurTime(props.curTime)
        setActive(props.active)
        setTimeLeft(calculateTimeToNext(curTime, nextItem))
        setLast(props.last ? props.last : false)



    }, [props])


    return (
       <div className={`top-block ${last ? 'top-block-last' : ''} ${active ? 'top-block-active' : ''}` }>
           <div className="circle ">
               <div className={`${active ? 'circle-full' : ''}`}></div>
           </div>
           <div className={`schedule-teacher-block ${active ? 'schedule-teacher-block-active' : ''}`}>
               <div className="schedule-teacher-block-top">
                   <span className={`schedule-teacher-block-top-left-text`}>{groupName}</span>
                   <span className={`schedule-teacher-block-top-right-text`}>{time}</span>
               </div>
               <p className="schedule-teacher-block-subject">
                   {t('subject')}: {subject}
               </p>
               <p className="schedule-teacher-block-next">
                   {timeLeft !== '' && active ? timeLeft : '\u00A0'}
               </p>
           </div>
       </div>
    );
};

export default ScheduleTeacherBlock;