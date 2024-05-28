import {$api} from './index';

export const getAllGroups = async () => {
    let res = await $api.get(`info/group`);
    return res;
};