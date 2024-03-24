import React, {useEffect, useState} from 'react';
import './Button.scss';
import {Link} from "react-router-dom";

interface BlueButtonProps{
    link?: number | string;
    name: string;
    onClick?: () => void;
    active?: boolean;
    style?: React.CSSProperties;
}

const Button: React.FC<BlueButtonProps> = ({link,
                                                   name,
                                                   onClick,
                                                   style,
                                                   active}) => {
    let [linkValue, setLinkValue] = useState<number | string | undefined>(link);
    let [nameValue, setNameValue] = useState<string>(name);
    let [isActive, setIsActive] = useState<boolean | undefined>(undefined);
    useEffect(()=>{
        setLinkValue(link);
        setNameValue(name);
        setIsActive(active);
    }, [link, name, active])

    return (
        <>
            {link ? (
                <Link
                    to={'/item/' + link}
                    style={style}
                    className={`standard-btn`}
                >
                    {name}
                </Link>
            ) : (
                <a
                    onClick={onClick}
                    style={style}
                    className={`standard-btn`}
                >
                    {name}
                </a>
            )}

        </>
    );
};

export default Button;