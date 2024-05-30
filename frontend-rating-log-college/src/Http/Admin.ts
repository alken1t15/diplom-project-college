import {$api} from './index';

export const addNewTeacher = async (firstName: string, secondName: string, middleName: string, login: string, password: string, bornDate: string, startWork: string) => {
        let res = await $api.post(`create/teacher/add`, {firstName: firstName, secondName: secondName, middleName: middleName, login: login, password: password, bornDate: bornDate, startWork: startWork});
        return res;
};

export const addNewStudent = async (firstName: string, secondName: string, middleName: string, login: string, password: string, bornDate: string, groupId: number ) => {
        let res = await $api.post(`create/student/add`, {firstName: firstName, secondName: secondName, middleName: middleName, login: login, password: password, bornDate: bornDate, subgroupName: 'А', groupId : groupId});
        return res;
};

export const addNewStudentFromFile = async (formData: FormData) => {
                let res = await $api.post(`create/student/add/excel`, formData);
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


export const addGroup = async (idCurator: number, name: string, idSpecialization: number) => {
        let res = await $api.post(`create/group/add`, {idCurator: idCurator, name: name, idSpecialization: idSpecialization});
        return res;
};

export const createAuditorium = async (block: number, flour: number, cabinet: number) => {
        let res = await $api.post(`create/auditorium/add`, {block: block, flour: flour, cabinet: cabinet});
        return res;
};

export const addSemestr = async (idGroup: number, semester: string, course: string, start: string, end: string) => {
        let res = await $api.post(`create/study/process/add`, {idGroup: idGroup, semester: semester, course: course, start: start, end: end });
        return res;
};

export const addTypeStudy = async (idStudyProcess: number, name: string, start: string, end: string) => {
        let res = await $api.post(`create/type/study/add`, {idStudyProcess: idStudyProcess, name: name, start: start, end: end });
        return res;
};

export const addSubjectForStud = async (idTypeStudy: number, idTimeStudy: number, idSubject: number, idTeacher: number, auditorium: number, idWeek: number, numberOfCouple: number) => {
        let res = await $api.post(`create/plan/study/add`, {idTypeStudy: idTypeStudy, idTimeStudy: idTimeStudy, idSubject: idSubject, idTeacher: idTeacher, auditorium: auditorium, idWeek: idWeek, numberOfCouple: numberOfCouple });
        return res;
};






