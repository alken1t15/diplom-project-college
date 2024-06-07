import React, { useState, useEffect } from 'react';
import './FileItem.scss';
import {useTranslation} from "react-i18next";
const fileIcon = require('../../assets/images/filesSVg.svg').default;
export interface IFileItem {
    id: number;
    text: string;
    date?: string;
    img: string;
    size?: string;
    desk?: string;
    subject?: string;
}

interface IFileBlock {
    item: IFileItem;
    isMini?: boolean;
}

const downloadFile = (url: string, filename: string) => {
    const link = document.createElement('a');
    link.href = url;
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
};

const getMimeType = (filename: string): string => {
    const extension = filename.split('.').pop()?.toLowerCase();
    switch (extension) {
        case 'jpg':
        case 'jpeg':
            return 'image/jpeg';
        case 'png':
            return 'image/png';
        case 'gif':
            return 'image/gif';
        case 'pdf':
            return 'application/pdf';
        case 'zip':
            return 'application/zip';
        case 'rar':
            return 'application/x-rar-compressed';
        case '7z':
            return 'application/x-7z-compressed';
        default:
            return 'application/octet-stream';
    }
};

const convertBase64ToBlob = (base64: string, mimeType: string): Blob => {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: mimeType });
};

const FileItem: React.FC<IFileBlock> = ({ item, isMini }) => {
    const [imageSrc, setImageSrc] = useState<string>('');
    const { t } = useTranslation();
    useEffect(() => {
        const mimeType = getMimeType(item.text);
        try {
            const blob = convertBase64ToBlob(item.img, mimeType);
            const objectUrl = URL.createObjectURL(blob);

            if (mimeType.startsWith('image/')) {
                setImageSrc(objectUrl);
            } else {
                setImageSrc(fileIcon);
            }

            return () => URL.revokeObjectURL(objectUrl);
        } catch (error) {
            console.error(`${t('errorToBlob')}:`, error);
            setImageSrc(fileIcon);
        }
    }, [item.img, item.text]);

    const handleDownload = () => {
        const mimeType = getMimeType(item.text);
        try {
            const blob = convertBase64ToBlob(item.img, mimeType);
            const objectUrl = URL.createObjectURL(blob);
            downloadFile(objectUrl, item.text);
            URL.revokeObjectURL(objectUrl);
        } catch (error) {
            console.error(`${t('errorToBlob')}:`, error);
        }
    };

    return (
        <div
            className={`file-item ${isMini ? 'file-item-m' : ''}`}
            key={item.id}
            onClick={handleDownload}
        >
            <img src={imageSrc} alt={item.text} />
            <div className={`file-item-block ${isMini ? 'file-item-block-m' : ''}`}>
                <p className={`courses-block__title courses-block__title-l file-item-block__title ${isMini ? 'file-item-block__title-m' : ''}`}>{item.text ? item.text : ''}</p>
                {item.desk !== undefined ?  <p className={`courses-block__title courses-block__title-l file-item-block__title file-item-block__title-text ${isMini ? 'file-item-block__title-m' : ''}`}>{item.desk ? item.desk : ''}</p> : ''}
                {item.subject !== 'undefined' ? <p className={`courses-block__title courses-block__title-l file-item-block__title file-item-block__title-text ${isMini ? 'file-item-block__title-m' : ''}`}>{item.subject ? item.subject  :''}</p> : ''}
                {item.size ?
                    <p className={`courses-block__text file-item-block__text ${isMini ? 'file-item-block__text-m' : ''}`}>{item.size}</p> :
                    <p className={`courses-block__text file-item-block__text ${isMini ? 'file-item-block__text-m' : ''}`}>{item.date}</p>
                }
            </div>
        </div>
    );
};

export default FileItem;
