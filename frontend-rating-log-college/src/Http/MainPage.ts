import {$api} from './index';

export const mainPageData = async (numberOfMonth?: number) => {
    let res = await $api.post(`student/main`, {numberOfMonth: numberOfMonth ? numberOfMonth : 1});
    return res;
};