import {$api} from './index';

export const getCoursesItems = async (id?: string) => {
        let res;
        if(id === ''){
            res = await $api.post(`student/home/task`, {});
            return res;
        }
        else{
            res = await $api.post(`student/home/task`, {id: id});
            return res;
        }

};


export const sendHomeWorkFiles = async (id?: number, formData?: any) => {
    if(id && formData){
        let res = await $api.post(`student/home/add?id=${id}`, formData);
        return res;
    }

};


export const getStudentHomeWorks = async () => {
    let res = await $api.post(`teacher/home`, );
    return res;
};

export const getStudentHomeWork = async (idWork: number, name: string) => {
    let res = await $api.post(`teacher/home/group`, {idWork: idWork, name: name});
    return res;
};

export const sendRepeatHw = async (idWork: number, idStudent: number) => {
    let res = await $api.post(`teacher/work/repeat`, {idWork: idWork, idStudent: idStudent, repeat: true});
    return res;
};

export const sendNewGrade = async (idWork: number, idStudent: number, ball: number) => {
    let res = await $api.post(`teacher/work/add/ball`, {idWork: idWork, idStudent: idStudent, ball: ball});
    return res;
};

