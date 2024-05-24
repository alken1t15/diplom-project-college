import {$api} from './index';

export const getCoursesItems = async (course?: number) => {
        let res = await $api.post(`student/courses`, {course: course});
        return res;
};