import React, { useEffect, useRef, useState } from 'react';
import './FileUploader.scss';
const uploadImg = require('../../assets/images/UploadImg.png');
const fileIcon = require('../../assets/images/filesSVg.svg').default;

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
    onClick: (files: File[]) => void;
    multipart?: boolean;
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

const FileUploader: React.FC<IFileUploader> = (props) => {
    const [images, setImages] = useState<IImage[]>([]);
    const [files, setFiles] = useState<File[]>([]);
    const [parentImgs, setParentImgs] = useState<IImage[]>([]);
    const [isDragging, setIsDragging] = useState(false);
    const fileInputRef = useRef<HTMLInputElement>(null);
    const containerRef = useRef<HTMLDivElement>(null);
    const [hwId, setHwId] = useState(props.homeWorkId);
    const [active, isActive] = useState<boolean | undefined>(undefined);

    function selectFiles() {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    }

    function onFileSelected(event: React.ChangeEvent<HTMLInputElement>) {
        const selectedFiles = event.target.files;
        const max_file_size = 100;
        const max_file_size_bytes = max_file_size * 1024 * 1024;
        if (!selectedFiles || selectedFiles.length === 0) return;

        const newImages: IImage[] = [];
        const newFiles: File[] = [];
        for (let i = 0; i < selectedFiles.length; i++) {
            const file = selectedFiles[i];
            if (file.size > max_file_size_bytes) continue;
            const isImage = file.type.split('/')[0] === 'image';
            if (!images.some(e => e.name === file.name)) {
                newImages.push({
                    name: file.name,
                    url: isImage ? URL.createObjectURL(file) : fileIcon,
                    isFile: !isImage
                });
                newFiles.push(file);
            }
        }
        setImages(prevState => [...prevState, ...newImages]);
        setFiles(prevState => [...prevState, ...newFiles]);
    }

    function deleteImage(index: number) {
        setImages(prevState => {
            const newState = [...prevState];
            newState.splice(index, 1);
            return newState;
        });
        setFiles(prevState => {
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
        const droppedFiles = e.dataTransfer.files;
        const max_file_size = 100;
        const max_file_size_bytes = max_file_size * 1024 * 1024;

        if (!droppedFiles || droppedFiles.length === 0) return;

        const newImages: IImage[] = [];
        const newFiles: File[] = [];
        for (let i = 0; i < droppedFiles.length; i++) {
            const file = droppedFiles[i];
            if (file.size > max_file_size_bytes) continue;
            const isImage = file.type.split('/')[0] === 'image';
            if (!images.some(e => e.name === file.name)) {
                newImages.push({
                    name: file.name,
                    url: isImage ? URL.createObjectURL(file) : fileIcon,
                    isFile: !isImage
                });
                newFiles.push(file);
            }
        }
        setImages(prevState => [...prevState, ...newImages]);
        setFiles(prevState => [...prevState, ...newFiles]);
    }

    useEffect(() => {
        setHwId(props.homeWorkId);
    }, [props]);

    useEffect(() => {
        if (props.items && props.items.length > 0) {
            const newParentImgs: IImage[] = props.items.map(item => {
                try {
                    const base64String = item.file.split(',')[1] || item.file;
                    const mimeType = item.file.match(/^data:(.*?);base64,/)?.[1] || 'application/octet-stream';
                    const blob = base64ToBlob(base64String, mimeType);
                    const blobUrl = URL.createObjectURL(blob);
                    return {
                        name: item.name,
                        url: blobUrl,
                        isFile: !mimeType.startsWith('image/')
                    };
                } catch (error) {
                    console.error("Invalid base64 string:", item.file);
                    return { name: item.name, url: fileIcon, isFile: true };
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

    const handleImageError = (e: React.SyntheticEvent<HTMLImageElement, Event>) => {
        e.currentTarget.src = fileIcon;
    };

    const handleSubmit = () => {
        props.onClick(files);
        setImages([]);
        setFiles([]);
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
                        <img className={'loaded-img'} src={el.url} alt={el.name} onLoad={() => handleImageLoad(index)} onError={handleImageError}/>
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
                    onClick={handleSubmit}>
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
                            <img src={img.url} alt={img.name} onError={handleImageError} />
                        </a>
                    ))}
                </div>
            )}
        </div>
    );
};

export default FileUploader;
