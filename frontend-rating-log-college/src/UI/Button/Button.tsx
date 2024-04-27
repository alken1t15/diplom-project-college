import React, {useEffect, useState} from 'react';
import './Button.scss';
import {Link} from "react-router-dom";

interface BlueButtonProps{
    link?: number | string;
    name: string;
    onClick?: (email: string, password: string) => void;
    data?: {
        email: string;
        password: string;
    }
    active?: boolean;
    style?: React.CSSProperties;
}

const Button: React.FC<BlueButtonProps> = ({link,
                                                   name,
                                                   data,
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
    }, [link, name, active, onClick])

    return (
        <>
            {link && onClick ? (
                <Link
                    to={'/item/' + link}
                    style={style}
                    className={`standard-btn`}
                >
                    {name}
                </Link>
            ) : (
                !link && !onClick ?
                <a
                    style={style}
                    className={`standard-btn`}
                >
                    {name}
                </a>

                    :
                    onClick ?
                    <button
                        style={style}
                        className={`standard-btn`}
                        onClick={(e)=>{
                            e.preventDefault();
                            onClick(data ? data.email : '', data ? data.password : '')
                        }}
                    >
                        {name}
                    </button> : <></>

            )}

        </>
    );
};

export default Button;