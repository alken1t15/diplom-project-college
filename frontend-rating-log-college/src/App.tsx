import React from 'react';
import './App.scss';
import {Route, Routes} from "react-router-dom";
import NotFound from "./Pages/NotFound/NotFound";
import SignIn from "./Pages/SignIn/SignIn";
import {
    COURSES_STUDENT_ROUTE,
    GRADE_STUDENT_ROUTE, HW_STUDENT_ROUTE,
    MAIN_PAGE_STUDENT_ROUTE,
    SIGN_IN_ROUTE,
    STUDENT_ROUTE, TEACHER_GRADES_PAGE_ROUTE, TEACHER_HOMEWORKS_PAGE_ROUTE, TEACHER_MAIN_PAGE_ROUTE, TEACHER_ROUTE
} from "./Utils/Routes";
import MainPageStudent from "./Pages/MainPageStudent/MainPageStudent";
import StudentLayout from "./Components/StudentLayout/StudentLayout";
import GradePageStudent from "./Pages/GradePageStudent/GradePageStudent";
import CoursesPageStudent from "./Pages/CoursesPageStudent/CoursesPageStudent";
import HomeworkPageStudent from "./Pages/HomeworkPageStudent/HomeworkPageStudent";
import TeacherLayout from "./Components/TeacherLayout/TeacherLayout";
import MainPageTeacher from "./Pages/MainPageTeacher/MainPageTeacher";
import HomeWorksPageTeacher from "./Pages/HomeWorksPageTeacher/HomeWorksPageTeacher";

function App() {
  return (
    <div className="App">
       
      <Routes>
          <Route path="/">
            <Route path={SIGN_IN_ROUTE} index element={<SignIn />} />


              <Route path={STUDENT_ROUTE} element={<StudentLayout/>}>
                <Route path={MAIN_PAGE_STUDENT_ROUTE} element={<MainPageStudent/>}/>
                <Route path={GRADE_STUDENT_ROUTE} element={<GradePageStudent/>}/>
                <Route path={COURSES_STUDENT_ROUTE} element={<CoursesPageStudent/>}/>
                <Route path={HW_STUDENT_ROUTE} element={<HomeworkPageStudent/>}/>
              </Route>

              <Route path={TEACHER_ROUTE} element={<TeacherLayout/>}>
                  <Route path={TEACHER_MAIN_PAGE_ROUTE} element={<MainPageTeacher/>}/>
                  <Route path={TEACHER_HOMEWORKS_PAGE_ROUTE} element={<HomeWorksPageTeacher/>}/>
                  <Route path={TEACHER_GRADES_PAGE_ROUTE} element={<MainPageTeacher/>}/>
              </Route>


            <Route path="*" element={<NotFound />} />
          </Route>

      </Routes>
    </div>
  );
}

export default App;
