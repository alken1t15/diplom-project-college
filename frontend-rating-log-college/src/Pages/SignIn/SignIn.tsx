import React, {useEffect, useState} from 'react';
import "./SignIn.scss";
import Input from "../../UI/Input/Input";
import Button from "../../UI/Button/Button";
import {login} from "../../Http/User";
import {ADMIN_MAIN_PAGE_ROUTE, MAIN_PAGE_STUDENT_ROUTE, TEACHER_MAIN_PAGE_ROUTE} from "../../Utils/Routes";
import {useDispatch} from "react-redux";
import {useCustomNavigate} from "../../hooks/navigator";
import {removeItemFromLocalStorage, setItemsInLocalStorage} from "../../Utils/LocalStore";
import {setUser} from "../../Store/Actions/authActions";
import {notify, Toasty} from "../../Components/Toasty/Toasty";
import {useTranslation} from "react-i18next";
import LanguageSwitcher from "../../Components/LanguageSwitcher/LanguageSwitcher";
const logoImg = require("../../assets/images/Logo.png");
const signInImg = require("../../assets/images/SignInImage.png");

const SignIn: React.FC = () => {
    let[email, setEmail] = useState<string>('');
    let[password, setPassword] = useState<string>('');
    const dispatch = useDispatch();
    const { setNavigate } = useCustomNavigate();
    function getEmail(value: string) {
        setEmail(value);
    }
    function getPassword(value: string) {
        setPassword(value);
    }

    function sendUserData(email: string, password: string): void{
        login(email, password)
            .then((response)=>{
                let userObj = {
                    token: response.data['jwt-token'],
                    role: response.data['role']
                }
                setItemsInLocalStorage('user', userObj);
                dispatch(setUser(userObj))
                if(response.data['role'] === 'student'){
                    setNavigate(MAIN_PAGE_STUDENT_ROUTE)
                }
                else if(response.data['role'] === 'teacher'){
                    setNavigate(TEACHER_MAIN_PAGE_ROUTE)
                }
                else if(response.data['role'] === 'admin'){
                    setNavigate(ADMIN_MAIN_PAGE_ROUTE)
                }

            })
            .catch((error)=>{
                notify(`${t('dataErr')}`,'error')
            })
    }

    useEffect(()=>{
        removeItemFromLocalStorage('user')
    }, [])

    const { t } = useTranslation();

    return (
        <div className={'sign-in-block'}>
            <div className="wrapper sign-in-wrapper">
                <form className="sign-in-left-block">
                    <img className={"Logo"} src={logoImg} alt="Logo"/>
                    <p className={'title-big'}>{t('welcome')}<br/> <span>{t('authorization')}</span></p>
                    <Input type={"text"} onChange={getEmail} placeholder={t('login')}/>
                    <Input type={"password"} onChange={getPassword} placeholder={t('password')}/>
                    <Button style={{marginTop: 40}} name={t('sign')} data={{email, password}} onClick={(e)=>{sendUserData(email, password)}}/>
                    <LanguageSwitcher horizontal={false}/>
                </form>
                <div className="sign-in-right-block">
                    <div>
                        <img src={signInImg} alt=""/>
                    </div>
                </div>
            </div>
            <Toasty/>
        </div>
    );
}

export default SignIn;