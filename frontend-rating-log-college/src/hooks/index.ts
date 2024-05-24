import { useSelector, useDispatch } from 'react-redux';
import { selectUser, selectLoading } from '../Store/Selectors/authSelectors';
import { setUser, setLoading } from '../Store/Actions/authActions';

export const useAuth = () => {
    const user = useSelector(selectUser);
    const loading = useSelector(selectLoading);
    return { user, loading };
};

export const useAuthActions = () => {
    const dispatch = useDispatch();

    const updateUser = (user: any) => {
        dispatch(setUser(user));
    };

    const updateLoading = (loading: boolean) => {
        dispatch(setLoading(loading));
    };

    return { updateUser, updateLoading };
};