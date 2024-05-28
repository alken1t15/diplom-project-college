import React, { useEffect, useRef, useState } from 'react';
import './FileUploader.scss';
const uploadImg = require('../../assets/images/UploadImg.png');
const fileIcon = require('../../assets/images/files.png');

interface IImage {
    name: string;
    url: string;
    size?: string;
    isFile?: boolean;
}

interface IFileUploader {
    dayId?: number;
    func?: () => void;
    items?: { name: string; file: string }[];
    status?: string;
    homeWorkId?: number;
    onClick: (items: any[]) => void;
    multipart?: boolean;
}

interface FileObject {
    name: string;
    file: Blob;
    typeFile: string;
    date: string;
}

const base64ToBlob = (base64: string, mimeType: string = ''): Blob => {
    const byteCharacters = atob(base64);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += 512) {
        const slice = byteCharacters.slice(offset, offset + 512);
        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
            byteNumbers[i] = slice.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
    }

    return new Blob(byteArrays, { type: mimeType });
};

const transformData = (data: { name: string; file: string }[]): FileObject[] => {
    const currentDate = new Date().toISOString();

    return data.map(item => {
        const base64String = item.file.split(',')[1] || item.file;
        const mimeType = item.file.match(/^data:(.*?);base64,/)?.[1] || 'application/octet-stream';
        return {
            name: item.name,
            file: base64ToBlob(base64String, mimeType),
            typeFile: "дз",
            date: currentDate,
        };
    });
};

const FileUploader: React.FC<IFileUploader> = (props) => {
    const [images, setImages] = useState<IImage[]>([]);
    const [parentImgs, setParentImgs] = useState<IImage[]>([]);
    const [isDragging, setIsDragging] = useState(false);
    const fileInputRef = useRef<HTMLInputElement>(null);
    const containerRef = useRef<HTMLDivElement>(null);
    const [hwId, setHwId] = useState(props.homeWorkId);
    const [active, isActive] = useState<boolean | undefined>(undefined);
    const [sendingFiles, setSendingFiles] = useState<any[]>([]);
    const [transformedData, setTransformedData] = useState<FileObject[]>([]);

    function selectFiles() {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    }

    function onFileSelected(event: React.ChangeEvent<HTMLInputElement>) {
        const files = event.target.files;
        const max_file_size = 100;
        const max_file_size_bytes = max_file_size * 1024 * 1024;
        if (!files || files.length === 0) return;

        if (props.multipart) {
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                if (file.size > max_file_size_bytes) continue;
                const isImage = file.type.split('/')[0] === 'image';
                if (!images.some(e => e.name === file.name)) {
                    setImages(prevState => [
                        ...prevState,
                        {
                            name: file.name,
                            url: isImage ? URL.createObjectURL(file) : fileIcon,
                            isFile: !isImage
                        },
                    ]);
                }
            }
        } else {
            const file = files[0];
            if (file.size > max_file_size_bytes) return;
            const isImage = file.type.split('/')[0] === 'image';
            setImages([
                {
                    name: file.name,
                    url: isImage ? URL.createObjectURL(file) : fileIcon,
                    isFile: !isImage
                },
            ]);
        }
    }

    function deleteImage(index: number) {
        setImages(prevState => {
            const newState = [...prevState];
            newState.splice(index, 1);
            return newState;
        });
    }

    function onDragOver(e: React.DragEvent<HTMLDivElement>) {
        e.preventDefault();
        setIsDragging(true);
        e.dataTransfer.dropEffect = 'copy';
    }

    function onDragLeave(e: React.DragEvent<HTMLDivElement>) {
        e.preventDefault();
        setIsDragging(false);
    }

    function onDrop(e: React.DragEvent<HTMLDivElement>) {
        e.preventDefault();
        setIsDragging(false);
        const files = e.dataTransfer.files;
        const max_file_size = 100;
        const max_file_size_bytes = max_file_size * 1024 * 1024;

        if (!files || files.length === 0) return;

        if (props.multipart) {
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                if (file.size > max_file_size_bytes) continue;
                const isImage = file.type.split('/')[0] === 'image';
                if (!images.some(e => e.name === file.name)) {
                    setImages(prevState => [
                        ...prevState,
                        {
                            name: file.name,
                            url: isImage ? URL.createObjectURL(file) : fileIcon,
                            isFile: !isImage
                        },
                    ]);
                }
            }
        } else {
            const file = files[0];
            if (file.size > max_file_size_bytes) return;
            const isImage = file.type.split('/')[0] === 'image';
            setImages([
                {
                    name: file.name,
                    url: isImage ? URL.createObjectURL(file) : fileIcon,
                    isFile: !isImage
                },
            ]);
        }
    }

    useEffect(() => {
        setHwId(props.homeWorkId);
    }, [props]);

    useEffect(() => {
        const data = transformData(props.items || []);
        setTransformedData(data);
    }, [props.items]);

    useEffect(() => {
        if (props.items && props.items.length > 0) {
            const newParentImgs: IImage[] = props.items.map(item => {
                try {
                    const base64String = item.file.split(',')[1] || item.file;
                    const mimeType = item.file.match(/^data:(.*?);base64,/)?.[1] || 'application/octet-stream';
                    const blob = base64ToBlob(base64String, mimeType);
                    const blobUrl = URL.createObjectURL(blob);
                    return { name: item.name, url: blobUrl };
                } catch (error) {
                    console.error("Invalid base64 string:", item.file);
                    return { name: item.name, url: '' };
                }
            });
            setParentImgs(newParentImgs);
        }
    }, [props.items]);

    const handleImageLoad = (index: number) => {
        if (containerRef.current) {
            const imageElement = containerRef.current.children[index] as HTMLElement;
            if (imageElement) {
                imageElement.classList.remove('loading');
            }
        }
    };

    return (
        <div className="card">
            <div className={`drag-area ${props.status === "Сдано" ? 'drag-area-none' : ' '}`}
                 onClick={selectFiles}
                 onDragOver={onDragOver}
                 onDragLeave={onDragLeave}
                 onDrop={onDrop}>
                {
                    isDragging ? (
                        <span className="select">Перетащите файлы</span>
                    ) : (
                        <span className="select">
                          <span className="select-top">
                                <img src={uploadImg} alt=""/>
                                <span>Перетащите файлы или нажмите для загрузки</span>
                          </span>
                          <p className={`select-light`}>Загружайте файлы не больше 100 мб</p>
                        </span>
                    )
                }
                <input type="file" name='file' className={`file`} multiple={props.multipart}
                       ref={fileInputRef}
                       onChange={onFileSelected}/>
            </div>
            <div className="drag-container" ref={containerRef}>
                {images.map((el, index) => (
                    <div className="image loading" key={index}>
                        <img className={'loaded-img'} src={el.url} alt={el.name} onLoad={() => handleImageLoad(index)}/>
                        <div className="loader-info">
                            <p className={'loader-text'}>{el.name}</p>
                            <div className="loader">
                                <div className="loader-line">
                                    <div></div>
                                </div>
                            </div>
                            {props.status === 'Сдано'}
                        </div>
                        <p className={`delete`} onClick={() => deleteImage(index)}>&times;</p>
                    </div>
                ))}
            </div>
            <button className={`button ${images.length === 0 ? 'none' : ' '}`}
                    onClick={() => {
                        setImages([])
                        props.onClick(images);
                    }}>
                {props.status === "Назначенно" ? 'Сдать' :
                    props.status === "Сдано" ? 'Пересдать' :
                        props.status === "Просрочено" ? 'Сдать с опозданием' :
                            'Отправить'}
            </button>
            {parentImgs.length > 0 && (
                <div className="preview">
                    <h3>Ваши прикрепленные файлы:</h3>
                    {parentImgs.map((img, index) => (
                        <a key={index} href={img.url} download={img.name} target="_blank" rel="noopener noreferrer" style={{display: "inline"}}>
                            <img src={img.url} alt={img.name} onError={(e) => console.error("Image load error: ", e)} />
                        </a>
                    ))}
                </div>
            )}
        </div>
    );
};

export default FileUploader;
