import { useSelector, useDispatch } from 'react-redux';
import { selectUser } from '../Store/Selectors/authSelectors';
import { setUser } from '../Store/Actions/authActions';

export const useAuth = () => {
    const user = useSelector(selectUser);
    return { user };
};

export const useAuthActions = () => {
    const dispatch = useDispatch();

    const updateUser = (user: any) => {
        dispatch(setUser(user));
    };


    return { updateUser };
};