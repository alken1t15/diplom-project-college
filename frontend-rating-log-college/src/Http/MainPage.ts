import {$api} from './index';

export const mainPageData = async (numberOfMonth?: number) => {
    let res = await $api.post(`student/main`, {numberOfMonth: numberOfMonth ? numberOfMonth : 1});
    return res;
};

export const mainPageTeacherData = async () => {
    let res = await $api.post(`teacher/main`, {});
    return res;
};