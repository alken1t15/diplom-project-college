import React from 'react';
import './App.scss';
import {Route, Routes} from "react-router-dom";
import Layout from "./Components/Layout/Layout";
import NotFound from "./Pages/NotFound/NotFound";
import SignIn from "./Pages/SignIn/SignIn";
import {SIGN_IN_ROUTE} from "./Utils/Routes";

function App() {
  return (
    <div className="App">
      <Routes>

          <Route path="/" element={<Layout />}>
            <Route path={SIGN_IN_ROUTE} index element={<SignIn />} />
            <Route path="*" element={<NotFound />} />
          </Route>

      </Routes>
    </div>
  );
}

export default App;
