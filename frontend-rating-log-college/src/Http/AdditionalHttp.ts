import {$api} from './index';

export const getAllGroups = async () => {
    let res = await $api.get(`info/group`);
    return res;
};

export const getAllSubjects = async () => {
    let res = await $api.get(`info/subject`);
    return res;
};