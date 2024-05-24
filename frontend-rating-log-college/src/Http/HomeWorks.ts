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

export const sendHomeWorkFiles = async (id?: number, files?: object[]) => {
    let res;
    res = await $api.post(`student/home/add`, {idHomeTask: id, files: files});
    return res;


};