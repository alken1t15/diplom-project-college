import { RootState } from '../index';

export const selectUser = (state: RootState) => state.auth.user;
export const selectLanguage = (state: RootState) => state.auth.language;
