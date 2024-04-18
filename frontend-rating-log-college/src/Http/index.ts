import axios from "axios";

const $api = axios.create({
    baseURL: process.env.REACT_APP_API_URL
})


$api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        config.headers.Authorization = `Bearer ${token}`;

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export {
    $api
}