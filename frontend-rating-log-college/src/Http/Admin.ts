import {$api} from './index';

export const addNewTeacher = async (firstName: string, secondName: string, middleName: string, login: string, password: string, bornDate: string, startWork: string) => {
        let res = await $api.post(`create/teacher/add`, {firstName: firstName, secondName: secondName, middleName: middleName, login: login, password: password, bornDate: bornDate, startWork: startWork});
        return res;
};

export const addNewSpec = async (name: string) => {
        let res = await $api.post(`create/specialization/add`, {name: name});
        return res;
};

export const addNewSubject = async (name: string) => {
        let res = await $api.post(`create/subject/add`, {name: name});
        return res;
};

export const setCurator = async (idTeacher: number) => {
        let res = await $api.post(`create/curator/add`, {idTeacher: idTeacher});
        return res;
};

