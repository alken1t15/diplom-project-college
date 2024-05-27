import {$api} from './index';

export const mainPageData = async (numberOfMonth?: number) => {
    let res = await $api.post(`student/main`, {numberOfMonth: numberOfMonth ? numberOfMonth : 1});
    return res;
};

export const mainPageTeacherData = async () => {
    let res = await $api.post(`teacher/main`, {});
    return res;
};

export const mainPageTeacherUpdateData = async (idGroupStep: number, certificateHave: boolean) => {
    let res = await $api.post(`teacher/main`, {idGroupStep: idGroupStep, certificateHave: certificateHave });
    return res;
};

export const addNewCertificate = async (formData: any) => {
    try {
        let res = await $api.post(`student/certificate/add`, formData);
        return res;
    } catch (error) {
        console.error('Error uploading certificate:', error);
        throw error;
    }
};

export const mainPageTeacherUpdateOmission = async (idGroup: number, idStudent: number, nameSubject: string, status: boolean, numberCouple: number) => {
    let res = await $api.post(`teacher/omission`, {idGroup: idGroup, idStudent: idStudent, nameSubject: nameSubject, status: status, numberCouple: numberCouple });
    return res;
};
