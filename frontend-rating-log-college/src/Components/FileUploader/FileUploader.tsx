import React, {useEffect, useRef, useState} from 'react';
import './FileUploader.scss'
import {sendHomeWorkFiles} from "../../Http/HomeWorks";
const uploadImg = require('../../assets/images/UploadImg.png');

interface IImage{
    name: string;
    url: string;
    size?: string;
}

interface IFileUploader{
    dayId?: number,
    func?: () => void;
    items?: [],
    status?: string,
    homeWorkId?: number;
}

interface FileObject {
    name: string;
    file: string;
    typeFile: string;
    date: string;
}

const transformData = (data: { name: string, url: string }[]): FileObject[] => {
    const currentDate = new Date().toISOString();

    return data.map(item => ({
        name: item.name,
        file: item.url,
        typeFile: "дз",
        date: currentDate,
    }));
};
const FileUploader: React.FC<IFileUploader> = (props) => {

    let [images, setImages] = useState<IImage[]>(props.items ? props.items : []);
    let [isDragging, setIsDragging] = useState(false);
    let fileInputRef = useRef<HTMLInputElement>(null)
    let containerRef = useRef<HTMLDivElement>(null);
    let[hwId, setHwId] = useState(props.homeWorkId)
    let [active, isActive] = useState()
    let [sendingFiles, setSendingFiles] = useState()
    const [transformedData, setTransformedData] = useState<FileObject[]>([]);

    function selectFiles(){
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    }

    function onFileSelected(event: any){
        let files = event.target.files;
        const max_file_size = 100;
        let max_sile_size_bytes = max_file_size * 1024 * 1024;
        if(files.length == 0 ) return ;

        for(let i = 0; i < files.length; i++){
            if(files[i].type.split('/')[0] !== 'image' || files[i].size > max_sile_size_bytes) continue;

            if(!images.some((e: any)=> e.name == files[i].name)){
                setImages((prevState)=>[
                    ...prevState,
                    {
                        name: files[i].name,
                        url: URL.createObjectURL(files[i])

                    },
                ]);
            }


        }

    }

    function deleteImage(index: number) {
        setImages((prevState) => {
            const newState = [...prevState];
            newState.splice(index, 1);
            return newState;
        });
    }

    function onDragOver(e: any){
        e.preventDefault();
        setIsDragging(true);
        e.dataTransfer.dropEffect = 'copy';
    }

    function onDragLeave(e: any){
        e.preventDefault();
        setIsDragging(false)
    }

    function onDrop(e:any){
        e.preventDefault();
        setIsDragging(false);
        let files = e.dataTransfer.files;
        for(let i = 0; i < files.length; i++){
            if(files[i].type.split('/')[0] !== 'image') continue;

            if(!images.some((e: any)=> e.name == files[i].name)){
                setImages((prevState)=>[
                    ...prevState,
                    {
                        name: files[i].name,
                        url: URL.createObjectURL(files[i])
                    },
                ]);
            }


        }
    }

    useEffect(()=>{
        setHwId(props.homeWorkId)
    },[props])

    const handleImageLoad = (index: number) => {
        if (containerRef.current) {
            const imageElement = containerRef.current.children[index] as HTMLElement;
            if (imageElement) {
                imageElement.classList.remove('loading');
            }
        }
    };

    useEffect(()=>{
        const data = transformData(images);
        setTransformedData(data);
    }, [images])

    return (
        <div className="card">

            <div className={`drag-area ${props.status === "Сдано" ? 'drag-area-none' : ' '}`}
                 onClick={selectFiles}
                 onDragOver={(e)=>{
                onDragOver(e)
            }}
                 onDragLeave={onDragLeave} onDrop={onDrop}>
                {
                    isDragging ? (
                        <span className="select">
                                    Перетащите файлы
                                </span>
                    ) : (
                        <span className="select ">
                          <span className="select-top">
                                <img src={uploadImg} alt=""/>
                                    <span>Перетащите файлы или нажмите для загрузки</span>
                          </span>
                                    <p className={`select-light`}>Загружайте файлы не больше 100 мб</p>
                                </span>
                    )
                }


                <input type="file" name='file' className={`file`} multiple={true}
                       ref={fileInputRef}
                       onChange={(e)=>{
                           onFileSelected(e)
                       }}
                />


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
            onClick={(e)=>{
                sendHomeWorkFiles(hwId, transformedData).then((response)=>{
                    console.log(response.data)
                })
                    .catch((error)=>{

                    })
            }}
            >
                {props.status === "Назначенно" ? 'Сдать' :
                    props.status === "Сдано" ? 'Пересдать':
                        props.status === "Просрочено" ? 'Сдать с опозданием':
                            'Отправить'
                }
            </button>
        </div>
    );
};

export default FileUploader;