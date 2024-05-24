import { useNavigate } from "react-router-dom";

export const useCustomNavigate = () => {
    const navigate = useNavigate();

    const setNavigate = (link: any) => {
        navigate(link);
    }

    return { setNavigate };
};
