import React, {useRef, useState} from 'react';
import './FileUploader.scss'
const uploadImg = require('../../assets/images/UploadImg.png');


interface IFileUploader{
    dayId?: number,
    func?: () => void;
}
const FileUploader: React.FC<IFileUploader> = (props) => {

    let [images, setImages] = useState<{ name: string, url: string }[]>([]);
    let [isDragging, setIsDragging] = useState(false);
    let fileInputRef = useRef<HTMLInputElement>(null)


    function selectFiles(){
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    }

    function onFileSelected(event: any){
        let files = event.target.files;
        const max_file_size = 20;
        let max_sile_size_bytes = max_file_size * 1024 * 1024;
        if(files.length == 0 ) return ;

        for(let i = 0; i < files.length; i++){
            if(files[i].type.split('/')[0] !== 'image' || files[i].size > max_sile_size_bytes) continue;

            if(!images.some((e: any)=> e.name == files[i].name)){
                setImages((prevState)=>[
                    ...prevState,
                    {
                        name: files[i].name,
                        url: URL.createObjectURL(files[i]),

                    },
                ]);
            }


        }

    }

    function deleteImage(index: number){
        setImages((prevState) => prevState.filter((el, i) => i !== index));

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
                        url: URL.createObjectURL(files[i]),

                    },
                ]);
            }


        }
    }


    return (
        <div className="card">
            <div className="top">

            </div>
            <div className="drag-area"
                 onClick={selectFiles}
                 onDragOver={(e)=>{
                onDragOver(e)
            }}
                 onDragLeave={onDragLeave} onDrop={onDrop}>
                {
                    isDragging ? (
                        <span className="select">
                                    Перетащите файлы или нажмите для загрузки
                                </span>
                    ) : (
                        <span className="select">
                            <img src={uploadImg} alt=""/>
                                    Перетащите файлы или нажмите для загрузки
                                    <p>Загружайте файлы не больше 20 мб</p>
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
            <div className="drag-container">
                {images.map((el, index) => (
                    <div className="image" key={index}>
                        <span className="delete" onClick={()=>{
                            deleteImage(index)
                        }}>&times;</span>
                        <img src={el.url} alt={el.name}/>
                    </div>
                ))}


            </div>
            <button className="button">
                Отправить
            </button>
        </div>
    );
};

export default FileUploader;