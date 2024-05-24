import { createStore, combineReducers } from 'redux';
import { authReducer } from './Reducers/authReducer';

const rootReducer = combineReducers({
    auth: authReducer,
});

export type RootState = ReturnType<typeof rootReducer>;

export const store = createStore(rootReducer);