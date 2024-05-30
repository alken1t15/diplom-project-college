import React, { useState } from 'react';
import './Dropdown.scss';

interface DropdownProps {
    items: { id: number, name: string }[];
    selectedId: number | null;
    placeholder: string;
    onSelect: (id: number) => void;
}

const Dropdown: React.FC<DropdownProps> = ({ items, selectedId, placeholder, onSelect }) => {
    const [isOpen, setIsOpen] = useState(false);

    const handleSelect = (id: number) => {
        onSelect(id);
        setIsOpen(false);
    };

    return (
        <div className="dropdown">
            <div className="dropdown__selected" onClick={() => setIsOpen(!isOpen)}>
                {selectedId ? items.find(item => item.id === selectedId)?.name : placeholder}
            </div>
            <div className={`dropdown__menu ${isOpen ? 'open' : ''}`}>
                {items.map(item => (
                    <div
                        key={item.id}
                        className={`dropdown__item ${item.id === selectedId ? 'dropdown__item--selected' : ''}`}
                        onClick={() => handleSelect(item.id)}
                    >
                        {item.name}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Dropdown;
