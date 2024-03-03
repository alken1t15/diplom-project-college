import React, {useState} from 'react';
import "./SignIn.scss";
import Input from "../../UI/Input/Input";

function SignIn() {
    let[email, setEmail] = useState<string>('');
    function getEmail(value: string) {
        setEmail(value);
    }
    return (
        <div className={'sign-in-block'}>
            <div className="wrapper sign-in-wrapper">
                <div className="sign-in-left-block">
                    <img className={"Logo"} src="../../assets/images/Logo.png" alt="Logo"/>
                    <p className={'title-big'}></p>
                    <Input type={"text"} onChange={getEmail} placeholder={'Email'}/>

                </div>
                <div className="sign-in-right-block">

                </div>
            </div>
        </div>
    );
}

export default SignIn;