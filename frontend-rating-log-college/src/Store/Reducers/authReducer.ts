
import { SET_USER, SET_LANGUAGE } from '../Actions/authActions';

interface AuthState {
    user: any;
    language: string;
}

const initialState: AuthState = {
    user: null,
    language: 'ru',
};

export const authReducer = (state = initialState, action: any): AuthState => {
    switch (action.type) {
        case SET_USER:
            return { ...state, user: action.payload };
        case SET_LANGUAGE:
            return { ...state, language: action.payload };
        default:
            return state;
    }
};
