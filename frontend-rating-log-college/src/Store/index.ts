import {combineReducers} from "redux";
import {createStore} from "redux";
import userReducer from "./UserReducer";

const rootReducer = combineReducers({
    user: userReducer,

})

export const store = createStore(rootReducer)