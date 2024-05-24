import { SET_USER, SET_LOADING } from '../Actions/authActions';

interface AuthState {
    user: any;
    loading: boolean;
}

const initialState: AuthState = {
    user: null,
    loading: true,
};

export const authReducer = (state = initialState, action: any): AuthState => {
    switch (action.type) {
        case SET_USER:
            return { ...state, user: action.payload };
        case SET_LOADING:
            return { ...state, loading: action.payload };
        default:
            return state;
    }
};
