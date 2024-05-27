import React, {ChangeEvent, CSSProperties, useState} from 'react';
import './Search.scss';

interface ISearch {
    onChange: (value: string) => void;
    style? : CSSProperties;
}

const searchImg = require('../../assets/images/SearchImg.png')
const Search: React.FC<ISearch> = (props) => {
    const [searchText, setSearchText] = useState('');
    let[inputActive, setInputActive] = useState(false)


    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const text = e.target.value;
        setSearchText(text);
        props.onChange(text);
    };

    return (
        <div className={`search-input ${inputActive || searchText.length > 0 ? 'search-input-a' : ''}`} style={props.style}>
            <img src={searchImg} alt=""/>
            <input
                type="text"
                onClick={(e)=>{
                    setInputActive(!inputActive)
                }}
                value={searchText}
                onChange={(e)=>{
                    handleChange(e)
                }}
                placeholder="Поиск"
            />
        </div>
    );
};

export default Search;
