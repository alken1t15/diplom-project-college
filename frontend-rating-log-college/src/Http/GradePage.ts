import {$api} from './index';

export const gradePageData = async (course?: number, semester?: number, month?: number) => {
    if(course && semester && month){
        let res = await $api.post(`student/grade`, {course, semester, month});
        return res;
    }
    let res = await $api.post(`student/grade`, {course: 1, semester: 1, month: 1});
    return res;
};