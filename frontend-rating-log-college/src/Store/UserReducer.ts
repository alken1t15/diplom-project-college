const SET_USER = "SET_USER"
const GET_USER = "GET_USER"

const defaultState: object = {
    user: {}
}


export default function reposReducer(state = defaultState, action: any) {
    switch (action.type) {
        case SET_USER:
            return {
                state: action.payload
            }
        case GET_USER:
            return {
                state
            }
        default:
            return state
    }
}

export const SetUser = (user: object) => ({type:SET_USER, payload:user})
export const GetUser = () => ({type:GET_USER})