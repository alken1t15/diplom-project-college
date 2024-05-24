export const SET_USER = 'SET_USER';
export const SET_LOADING = 'SET_LOADING';

export const setUser = (user: object) => ({
    type: SET_USER,
    payload: user,
});

export const setLoading = (loading: boolean) => ({
    type: SET_LOADING,
    payload: loading,
});
