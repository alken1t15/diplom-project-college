import axios from "axios";
import { getItemFromLocalStorage } from "../Utils/LocalStore";

const $api = axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    withCredentials: true
});

const $host = axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    withCredentials: true
});

export interface IUser {
    token: string;
    role: string;
}

$api.interceptors.request.use(
    (config) => {
        const user: any = getItemFromLocalStorage('user');
        const token = user ? JSON.parse(user)?.token : null;

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            config.headers.Authorization = '';
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export {
    $api,
    $host
}
