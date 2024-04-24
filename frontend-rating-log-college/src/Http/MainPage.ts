import {$api} from './index';

export const mainPageData = async () => {
    let res = await $api.post(`student/main`, {numberOfMonth: 1});
    return res;
};