import React, {useEffect, useState} from 'react';
import "./SignIn.scss";
import Input from "../../UI/Input/Input";
import Button from "../../UI/Button/Button";
import {login} from "../../Http/User";
import {useNavigate} from "react-router-dom";
import {MAIN_PAGE_STUDENT_ROUTE} from "../../Utils/Routes";
const logoImg = require("../../assets/images/Logo.png");
const signInImg = require("../../assets/images/SignInImage.png");

const SignIn: React.FC = () => {
    let[email, setEmail] = useState<string>('');
    let[password, setPassword] = useState<string>('');
    let navigator = useNavigate();
    function getEmail(value: string) {
        setEmail(value);
    }
    function getPassword(value: string) {
        setPassword(value);
    }

    function sendUserData(email: string, password: string): void{
        login(email, password)
            .then((response)=>{
                localStorage.setItem('token', response.data['jwt-token']);
                navigator(MAIN_PAGE_STUDENT_ROUTE)
            })
            .catch((error)=>{
            })
    }

    useEffect(()=>{
        localStorage.setItem('token', '')
    }, [])

    return (
        <div className={'sign-in-block'}>
            <div className="wrapper sign-in-wrapper">
                <form className="sign-in-left-block">
                    <img className={"Logo"} src={logoImg} alt="Logo"/>
                    <p className={'title-big'}>Добро пожаловать! <br/> <span>Авторизация</span></p>
                    <Input type={"text"} onChange={getEmail} placeholder={'Email'}/>
                    <Input type={"password"} onChange={getPassword} placeholder={'Password'}/>
                    <Button style={{marginTop: 40}} name={'Войти'} data={{email, password}} onClick={(e)=>{sendUserData(email, password)}}/>
                </form>
                <div className="sign-in-right-block">
                    <div>
                        <img src={signInImg} alt=""/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SignIn;