import React, { useEffect, useState } from 'react';
import './CurrentStudentHomeWork.scss';
import InitialsImage from "../InitialsImage/InitialsImage";
import JSZip from 'jszip';
import { saveAs } from 'file-saver';
import {ReactComponent as sendImg} from "../../assets/images/send.svg";
const repeatImg = require('../../assets/images/RepeatImg.png');
const downloadImg = require('../../assets/images/UploadImg.png');
const editImg = require('../../assets/images/edit.png');

export interface IFile {
    name: string;
    fileBase64: string;
}

export interface ICurrentStudentHomeWork {
    name: string;
    file: IFile[];
    id: number;
    onClick: (id: number, value: string) => void;
    grade?: number;
}

const CurrentStudentHomeWork: React.FC<ICurrentStudentHomeWork> = (props) => {
    let [name, setName] = useState<string>(props.name);
    let [files, setFiles] = useState<IFile[]>(props.file || []);
    let [id, setId] = useState<number>(props.id);
    let [grade, setGrade] = useState<number | undefined>(props.grade);
    let [inputValue , setInputValue] = useState('')
    let [editGrade, setEditGrade] = useState(false)

    useEffect(() => {
        setName(props.name);
        setFiles(props.file || []);
        setId(props.id);
        setGrade(props.grade);
    }, [props]);

    let downloadAndZipFiles = async () => {
        let zip = new JSZip();

        try {
            for (const file of files) {
                const fileBlob = base64ToBlob(file.fileBase64);
                zip.file(file.name, fileBlob, { base64: true });
            }

            const zipBlob = await zip.generateAsync({ type: 'blob' });
            saveAs(zipBlob, `exotus-${name}.zip`);
        } catch (error) {
            console.error('Error creating zip:', error);
        }
    };

    const base64ToBlob = (base64: string): Blob => {
        let byteCharacters = atob(base64);
        let byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        let byteArray = new Uint8Array(byteNumbers);
        return new Blob([byteArray]);
    };

    const validateAndSetInput = (value: string) => {
        let numberValue = Number(value);
        if (isNaN(numberValue)) {

        } else if (numberValue < 0 || numberValue > 100) {

        } else {
            setInputValue(String(numberValue));
        }
    };

    return (
        <div className={`current-student-item`}>
            <div className="current-student-item-top">
                <InitialsImage
                    initials={`${name.split(' ')[0][0]}${name.split(' ')[1][0]}`}
                    width={50}
                    height={50}
                    fontSize={24}
                    textColor="#fff"
                    backgroundColor="#d9d9d9"
                />
                <p className="current-student-item-top__text">{name}</p>
            </div>
            {grade || grade === 0 ?
                <>
                    <div className="current-student-item-middle">
                        <button className="current-student-item-middle-button-download" onClick={downloadAndZipFiles}>
                            <img src={downloadImg} alt="Download image" />
                            <span>Скачать</span>
                        </button>
                        <button className="current-student-item-middle-button-repeat" onClick={() => props.onClick(id, 'изменить оценку')}>
                            <img src={editImg} alt="Repeat image" />
                            <span onClick={(e)=>{
                                setEditGrade(true)
                            }}>Изменить оценку</span>
                        </button>
                    </div>
                    <div className="current-student-item-bottom-e">

                        {editGrade ? <>  <input placeholder={`Выставить оценку`} type="number" className={`current-student-item-bottom__input`}
                                                value={inputValue}
                                                onChange={(e) => validateAndSetInput(e.target.value)}
                            />
                                <button className="send-btn" onClick={(e)=>{
                                    props.onClick(id, inputValue)
                                    setEditGrade(false)
                                }}>
                                    {React.createElement(sendImg, {
                                        className: `send-img`})}
                                </button></>
                      :
                            <><span className={`current-student-item-bottom-e-grade`}>Оценка:</span>
                                <div className={`current-student-item-bottom-e-box`}>
                                    <div className={`
                            current-student-item-bottom-e-box-line
                            ${grade >= 0 && grade < 40 ? 'current-student-item-bottom-e-box-line-r' :
                                        grade >= 40 && grade < 70 ? 'current-student-item-bottom-e-box-line-y' :
                                            grade >= 70 && grade < 90 ? 'current-student-item-bottom-e-box-line-dg' :
                                                grade >= 90 && grade <= 100 ? 'current-student-item-bottom-e-box-line-g' : ''
                                    }
                            `}
                                         style={{width: grade+'%'}}
                                    ></div>
                                </div>
                                <span className={`current-student-item-bottom-e-grade`}>{grade}%</span></>}



                    </div>
                </> :

            <>
                <div className="current-student-item-middle">
                    <button className="current-student-item-middle-button-download" onClick={downloadAndZipFiles}>
                        <img src={downloadImg} alt="Download image" />
                        <span>Скачать</span>
                    </button>
                    <button className="current-student-item-middle-button-repeat" onClick={() => props.onClick(id, 'пересдача')}>
                        <img src={repeatImg} alt="Repeat image" />
                        <span>Отправить на пересдачу</span>
                    </button>
                </div>
                <div className="current-student-item-bottom">
                    <input placeholder={`Выставить оценку`} type="number" className={`current-student-item-bottom__input`}
                           value={inputValue}
                           onChange={(e) => validateAndSetInput(e.target.value)}
                    />
                    <button className="send-btn" onClick={(e)=>{
                        props.onClick(id, inputValue)
                    }}>
                        {React.createElement(sendImg, {
                            className: `send-img`})}
                    </button>
                </div>
            </>
            }
        </div>
    );
};

export default CurrentStudentHomeWork;
