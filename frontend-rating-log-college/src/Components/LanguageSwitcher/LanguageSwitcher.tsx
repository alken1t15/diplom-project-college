import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { selectLanguage } from '../../Store/Selectors/authSelectors';
import { setLanguage } from '../../Store/Actions/authActions';
import './LanguageSwitcher.scss';
import { useTranslation } from 'react-i18next';

interface ILanguageSwitcher {
    horizontal?: boolean
}

const LanguageSwitcher: React.FC<ILanguageSwitcher> = (props) => {
    const dispatch = useDispatch();
    const currentLanguage = useSelector(selectLanguage);
    const { i18n } = useTranslation();

    useEffect(() => {
        const savedLanguage = localStorage.getItem('language') || 'ru';
        if (currentLanguage !== savedLanguage) {
            dispatch(setLanguage(savedLanguage));
            i18n.changeLanguage(savedLanguage);
        }
    }, [dispatch, currentLanguage, i18n]);

    const handleLanguageChange = (language: string) => {
        dispatch(setLanguage(language));
        i18n.changeLanguage(language);
        localStorage.setItem('language', language);
    };

    return (
        <div className={'language-switcher'} style={{ flexDirection: props.horizontal ? 'column' : 'row' }}>
            <button onClick={() => handleLanguageChange('ru')} disabled={currentLanguage === 'ru'}>
                RU
            </button>
            <button onClick={() => handleLanguageChange('kz')} disabled={currentLanguage === 'kz'}>
                KZ
            </button>
        </div>
    );
};

export default LanguageSwitcher;
