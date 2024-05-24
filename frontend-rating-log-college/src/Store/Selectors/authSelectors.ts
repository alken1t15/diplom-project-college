import { RootState } from '../index';

export const selectUser = (state: RootState) => state.auth.user;
export const selectLoading = (state: RootState) => state.auth.loading;
