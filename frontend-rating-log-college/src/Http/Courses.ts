import {$api} from './index';

export const getCoursesItems = async (course?: number) => {
        let res = await $api.post(`student/courses`, {course: course});
        return res;
};

export const addNewFiles = async (formData: any) => {
        if(formData){
                let res = await $api.post(`teacher/courses/add`, formData);
                return res;
        }
};