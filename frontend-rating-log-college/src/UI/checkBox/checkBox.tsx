import React, { useEffect, useState } from 'react';
import './checkBox.scss';

interface ICheckBox {
    onChange: (value: boolean) => void;
    isActive: boolean;
}

const CheckBox: React.FC<ICheckBox> = (props) => {
    const [status, setStatus] = useState(props.isActive);

    useEffect(() => {
        setStatus(props.isActive);
    }, [props.isActive]);

    const handleToggle = () => {
        setStatus(!status);
        props.onChange(!status);
    };

    return (
        <div
            className={`switch ${status ? 'active' : ''}`}
            onClick={handleToggle}
        >
            <div className="switch-handle" />
        </div>
    );
};

export default CheckBox;
