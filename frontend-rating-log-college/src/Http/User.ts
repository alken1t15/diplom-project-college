import {$api} from './index';

export const login = async (login: string, password: string) => {
    let res = await $api.post(`login/jwt`, {"login": login, "password": password});
    return res;
};

export const logOut = async () => {
    let res = await $api.post(`logOut/jwt`);
    localStorage.setItem('token', '');
    return res;
};