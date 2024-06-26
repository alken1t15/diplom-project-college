import {ReactComponent as fImage} from "../../assets/images/icons Q2.svg";
import {ReactComponent as sImage} from "../../assets/images/user.svg";
import {ReactComponent as foImage} from "../../assets/images/study.svg";
import React, {useEffect, useState} from 'react';
import {Link, Outlet, useNavigate, useParams} from "react-router-dom";
import {
    ADMIN_MAIN_PAGE_ROUTE, ADMIN_STUDY_PAGE_ROUTE, ADMIN_USERS_PAGE_ROUTE,
    SIGN_IN_ROUTE,
} from "../../Utils/Routes";
import './Admin.scss';
import button from "../../UI/Button/Button";
import {logOut} from "../../Http/User";
import LanguageSwitcher from "../LanguageSwitcher/LanguageSwitcher";

const Logo = require('../../assets/images/Logo.png');
const LogOut = require('../../assets/images/ExitPng.png');

const Admin: React.FC = () => {

    let [linkButtons, setLinkButtons] = useState([
        {
            id: 1,
            name: 'Main page icon',
            link: ADMIN_MAIN_PAGE_ROUTE,
            active: true,
            img: fImage
        },

        {
            id: 2,
            name: 'Homework page icon',
            link: ADMIN_STUDY_PAGE_ROUTE,
            active: false,
            img: foImage
        },
        {
            id: 3,
            name: 'Users page icon',
            link: ADMIN_USERS_PAGE_ROUTE,
            active: false,
            img: sImage
        },
    ])
    let navigator = useNavigate();
    let [userName ,setUserName] = useState('')
    let [userLastName ,setUSerLastName] = useState('')

    useEffect(()=>{
        let newArr = linkButtons.map((el, index)=>{
            el.active = el.link == window.location.href.split('3000')[1];
            return el;
        })
        setLinkButtons(newArr)
    }, [])

    function changeActiveTab(id: number) {
        const currentIndex = linkButtons.findIndex(el => el.active) + 1;

        if(id == currentIndex){

        }

        else if(currentIndex < id){

            for(let index = currentIndex; index !< linkButtons.length; index++ ){

                setTimeout(() => {
                        setLinkButtons(prevState => {
                            const updatedTabs = [...prevState];
                            updatedTabs[index].active = updatedTabs[index].id <= id;
                            return updatedTabs;
                        });
                        }, index * 100);

            }

            linkButtons.forEach((el, index) => {
                setTimeout(() => {
                    setLinkButtons(prevState => {
                        const updatedTabs = [...prevState];
                        updatedTabs[index].active = updatedTabs[index].id == id;


                        return updatedTabs;
                    });
                }, index * 200);
            });

            setTimeout(() => {
                navigator(linkButtons[id-1].link)
            }, 4 * 200);

        }


        else{

            for(let index = currentIndex-1; index > id-1; index-- ){
                setTimeout(() => {
                    setLinkButtons(prevState => {
                        const updatedTabs = [...prevState];
                        updatedTabs[index].active = updatedTabs[index].id >= id;
                        return updatedTabs;
                    });
                }, (linkButtons.length - index - 1) * 100);

            }

            [...linkButtons].reverse().forEach((el, i) => {
                setTimeout(() => {
                    setLinkButtons(prevState => {
                        const updatedTabs = [...prevState];
                        updatedTabs[i].active = updatedTabs[i].id == id;

                        return updatedTabs;
                    });
                }, (linkButtons.length - i - 1) * 200);
            });

            setTimeout(() => {
                navigator(linkButtons[id-1].link)
            }, 4 * 200);


        }

    }

    function exit(){
        logOut()
            .then(response=>{
                navigator(SIGN_IN_ROUTE);
            })
            .catch(error=>{
                navigator(SIGN_IN_ROUTE);
            })
    }

    return (
        <div className={'student-layout'}>
                <div className={"navigation-block"}>
                    <div className={'link-main'}>
                        <Link to={ADMIN_MAIN_PAGE_ROUTE}>
                            <img className={'link-logo'} src={Logo} alt="logo img"/>
                        </Link>
                    </div>
                    <nav className={"nav nav-admin"}>
                        <div className={'nav-list nav-list-t'}>
                        {linkButtons.map((el, index)=>(
                            <button key={el.id} onClick={(e)=>{
                                changeActiveTab(el.id)
                            }} className={`link-button ${el.active ? 'link-button-active' : ''}`}>
                                {React.createElement(el.img, {
                                    className: `link-img
                                    ${el.active && el.id != 2 ? 'link-img-active' : ''}
                                    ${el.active && el.id == 2 ? 'link-img-active-4-admin' : '' }
                                    ${index === 2 ? 'link-button-active-user' : ''}
                                    `
                                })}

                            </button>
                        ))}
                        </div>
                        <div className="nav-other nav-other-admin">
                            <LanguageSwitcher/>
                            <button className="nav-other-logout" onClick={exit}><img src={LogOut} alt="logout"/></button>
                        </div>
                    </nav>
                </div>
                <Outlet />



        </div>
    );
};

export default Admin;