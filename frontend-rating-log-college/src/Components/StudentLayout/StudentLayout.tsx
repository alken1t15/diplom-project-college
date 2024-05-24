import {ReactComponent as fImage} from "../../assets/images/NavbarF.svg";
import {ReactComponent as sImage} from "../../assets/images/NavbarS.svg";
import {ReactComponent as tImage} from "../../assets/images/NavbarT.svg";
import {ReactComponent as foImage} from "../../assets/images/NavbarFo.svg";
import React, {useEffect, useState} from 'react';
import {Link, Outlet, useNavigate, useParams} from "react-router-dom";
import {
    COURSES_STUDENT_ROUTE,
    GRADE_STUDENT_ROUTE,
    HW_STUDENT_ROUTE,
    MAIN_PAGE_STUDENT_ROUTE, SIGN_IN_ROUTE
} from "../../Utils/Routes";
import './StudentLayout.scss';
import InitialsImage from "../InitialsImage/InitialsImage";
import button from "../../UI/Button/Button";
import {logOut} from "../../Http/User";
import {mainPageData} from "../../Http/MainPage";

const Logo = require('../../assets/images/Logo.png');
const LogOut = require('../../assets/images/ExitPng.png');

const StudentLayout: React.FC = () => {

    let [linkButtons, setLinkButtons] = useState([
        {
            id: 1,
            name: 'Main page icon',
            link: MAIN_PAGE_STUDENT_ROUTE,
            active: true,
            img: fImage
        },
        {
            id: 2,
            name: 'Grade page icon',
            link: GRADE_STUDENT_ROUTE,
            active: false,
            img: sImage
        },
        {
            id: 3,
            name: 'Courses page icon',
            link: COURSES_STUDENT_ROUTE,
            active: false,
            img: tImage
        },
        {
            id: 4,
            name: 'Homework page icon',
            link: HW_STUDENT_ROUTE,
            active: false,
            img: foImage
        }
    ])
    let [user, setUser] = useState({
        name: '',
        lastName: '',
        groupName: '',
        yearGroup: '',
    });
    let navigator = useNavigate();

    useEffect(()=>{
        let newArr = linkButtons.map((el, index)=>{
            el.active = el.link == window.location.href.split('3000')[1];
            return el;
        })
        setLinkButtons(newArr)
        mainPageData()
            .then(response=>{
                let obj = {
                    name: response.data.name,
                    lastName: response.data.lastName,
                    groupName: response.data.groupName,
                    yearGroup: response.data.yearGroup,

                };
                setUser(obj)
            })
            .catch(error=>{

            })
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
                        <Link to={MAIN_PAGE_STUDENT_ROUTE}>
                            <img className={'link-logo'} src={Logo} alt="logo img"/>
                        </Link>
                    </div>
                    <nav className={"nav"}>
                        <div className={'nav-list'}>
                        {linkButtons.map((el, index)=>(
                            <button key={el.id} onClick={(e)=>{
                                changeActiveTab(el.id)
                            }} className={`link-button ${el.active ? 'link-button-active' : ''}`}>
                                {React.createElement(el.img, {
                                    className: `link-img
                                    ${el.active && el.id != 4 ? 'link-img-active' : ''}
                                    ${el.active && el.id == 4 ? 'link-img-active-4' : '' }`
                                })}

                            </button>
                        ))}
                        </div>
                        <div className="nav-other">
                            <button className="nav-other-user">
                                <InitialsImage initials={`${user.name.split('')[0]}${user.lastName.split('')[0]}`} width={50} height={50} fontSize={24} textColor="#fff" backgroundColor="#d9d9d9" />
                            </button>
                            <button className="nav-other-logout" onClick={exit}><img src={LogOut} alt="logout"/></button>
                        </div>
                    </nav>
                </div>
                <Outlet />



        </div>
    );
};

export default StudentLayout;