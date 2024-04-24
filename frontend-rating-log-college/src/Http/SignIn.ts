import {$api} from './index';

export const login = async (email: string, password: string) => {
    let res = await $api.post(`login/jwt`, {email, password});
    return res;
};