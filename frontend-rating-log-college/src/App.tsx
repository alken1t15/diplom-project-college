import React from 'react';
import './App.scss';
import {Route, Routes} from "react-router-dom";
import Layout from "./Components/Layout/Layout";
import NotFound from "./Pages/NotFound/NotFound";
import SignIn from "./Pages/SignIn/SignIn";
import {MAIN_PAGE_STUDENT_ROUTE, SIGN_IN_ROUTE, STUDENT, STUDENT_ROUTE} from "./Utils/Routes";
import MainPageStudent from "./Pages/MainPageStudent/MainPageStudent";
import StudentLayout from "./Components/StudentLayout/StudentLayout";

function App() {
  return (
    <div className="App">
      <Routes>

          <Route path="/" element={<Layout />}>
            <Route path={SIGN_IN_ROUTE} index element={<SignIn />} />


              <Route path={STUDENT_ROUTE}>
                  <StudentLayout>
                          <Route exact path={MAIN_PAGE_STUDENT_ROUTE} component={MainPageStudent} />
                  </StudentLayout>
              </Route>



              <Route path={STUDENT} element={<StudentLayout/>}>
                  <Route path={MAIN_PAGE_STUDENT_ROUTE} index element={<MainPageStudent />} />
              </Route>

            <Route path="*" element={<NotFound />} />
          </Route>

      </Routes>
    </div>
  );
}

export default App;
