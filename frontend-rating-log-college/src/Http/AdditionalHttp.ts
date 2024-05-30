import {$api} from './index';

export const getAllGroups = async () => {
    let res = await $api.get(`info/group`);
    return res;
};

export const getAllTeachers = async () => {
    let res = await $api.get(`info/teacher`);
    return res;
};

export const getAllSubjects = async () => {
    let res = await $api.get(`info/subject`);
    return res;
};

export const getAllTime = async () => {
    let res = await $api.get(`info/time`);
    return res;
};

export const getAllDays = async () => {
    let res = await $api.get(`info/week`);
    return res;
};

export const getAllSpec = async () => {
    let res = await $api.get(`info/specialization`);
    return res;
};

export const getAllCur = async () => {
    let res = await $api.get(`info/curator`);
    return res;
};

export const getAllAudit = async () => {
    let res = await $api.get(`info/auditorium`, {});
    return res;
};
