export const SET_USER = 'SET_USER';

export const setUser = (user: object) => ({
    type: SET_USER,
    payload: user,
});


export const SET_LANGUAGE = 'SET_LANGUAGE';

export const setLanguage = (language: string) => ({
    type: SET_LANGUAGE,
    payload: language,
});