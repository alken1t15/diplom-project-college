import React, { useState } from 'react';
import DatePicker, { registerLocale } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ru } from 'date-fns/locale';
import './Calendar.scss';

registerLocale('ru', ru);

interface ICalendar{
    onChange: (value: Date) => void;
}

const Calendar: React.FC<ICalendar> = (props) => {
    const [selectedDate, setSelectedDate] = useState<Date | null>(new Date());

    const handleDateChange = (date: Date | null) => {
        setSelectedDate(date);
        if (date) {
            props.onChange(date)
            // alert(`Выбранная дата: ${date.toLocaleDateString()}`);
        }
    };

    const getDayClassName = (date: Date): string => {
        const today = new Date();
        const day = date.getDay();
        let className = '';

        if (date.getDate() === today.getDate() && date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear()) {
            className = 'today';
        } else if (day === 0) {
            className = 'sunday';
        } else if (day === 6) {
            className = 'saturday';
        }

        return className;
    };

    return (
        <div className="calendar-container">
            <DatePicker
                selected={selectedDate}
                onChange={handleDateChange}
                inline
                locale="ru"
                calendarClassName="custom-calendar"
                dayClassName={getDayClassName}
            />
        </div>
    );
};

export default Calendar;
