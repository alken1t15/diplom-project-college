import React, {CSSProperties, FC, useState} from 'react';
import './Input.scss';

interface InputProps {
    placeholder: string;
    onChange: (value: string) => void;
    type: string;
    styles?: CSSProperties;
}

const Input: React.FC<InputProps> = ({ placeholder,onChange, type ,styles}) => {
    const [value, setValue] = useState('');
    const [isFocused, setIsFocused] = useState(false);

    const handleFocus = () => {
        setIsFocused(true);
    };

    function inputChange(e: React.ChangeEvent<HTMLInputElement>){
        setValue(e.target.value)
        onChange(e.target.value)
    }

    const handleBlur = () => {
        if(value !== ''){
            setIsFocused(true)
        }
        else setIsFocused(false);
    };

    return (
        <div className={`input-container ${isFocused ? 'focused' : ''}`}>
            <input
                style={styles}
                type={type}
                value={value}
                onFocus={handleFocus}
                onBlur={handleBlur}
                onChange={(e) => inputChange(e)}
            />
            <label>{placeholder}</label>
        </div>
    );
};

export default Input;
