import React, {useState} from 'react';
import './FileItem.scss'

export interface IFileItem{
    id: number;
    text: string;
    date: string;
    img: any;
    size?: string;
}

interface IFileBlock{
    item: IFileItem;
    isMini?: boolean
}

const FileItem: React.FC<IFileBlock> = (props) => {

    let[item, setItem] = useState<IFileItem>(props.item)


    return (
        <div className={`file-item ${props.isMini ? 'file-item-m' : ''}`} key={item.id}>
            <img src={item.img} alt={item.text}/>
            <div className={`file-item-block ${props.isMini ? 'file-item-block-m' : ''}`}>
                <p className={`courses-block__title courses-block__title-l file-item-block__title ${props.isMini ? 'file-item-block__title-m' : ''}`}>{item.text}</p>
                {item.size ?
                    <p className={`courses-block__text file-item-block__text ${props.isMini ? 'file-item-block__text-m' : ''}`}>{item.size}</p> :
                    <p className={`courses-block__text file-item-block__text ${props.isMini ? 'file-item-block__text-m' : ''}`}>{item.date}</p>

                }
            </div>
        </div>
    );
};

export default FileItem;