import {$api} from './index';

export const login = (email: string, password: string) => {
    return $api.post(`login/jwt`, {email, password});
};