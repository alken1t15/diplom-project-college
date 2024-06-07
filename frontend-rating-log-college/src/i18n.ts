import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import ruTranslations from './locales/ru/translation.json';
import kzTranslations from './locales/kz/translation.json';

const savedLanguage = localStorage.getItem('language') || 'ru';

i18n
    .use(initReactI18next)
    .init({
        resources: {
            ru: {
                translation: ruTranslations
            },
            kz: {
                translation: kzTranslations
            }
        },
        lng: savedLanguage,
        fallbackLng: "ru",
        interpolation: {
            escapeValue: false
        }
    });

export default i18n;
