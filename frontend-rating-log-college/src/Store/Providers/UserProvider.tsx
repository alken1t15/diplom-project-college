import React, { ReactNode, useEffect } from 'react';
import { Provider, useDispatch } from 'react-redux';
import { store } from '../index';
import { setUser, setLanguage } from '../Actions/authActions';
import { getItemFromLocalStorage } from '../../Utils/LocalStore';

interface UserProviderProps {
    children: ReactNode;
}

const UserProviderComponent: React.FC<UserProviderProps> = ({ children }) => {
    const dispatch = useDispatch();

    useEffect(() => {
        const storedUser: any = getItemFromLocalStorage('token');
        if (storedUser) {
            dispatch(setUser(storedUser));
        }
    }, [dispatch]);

    const handleLanguageChange = (language: string) => {
        dispatch(setLanguage(language));
    };

    return <>{children}</>;
};

export const UserProvider: React.FC<UserProviderProps> = ({ children }) => (
    <Provider store={store}>
        <UserProviderComponent>{children}</UserProviderComponent>
    </Provider>
);
