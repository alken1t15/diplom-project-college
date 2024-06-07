import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.scss';
import App from './App';
import {BrowserRouter} from "react-router-dom";
import {Provider} from "react-redux";
import {store} from "./Store/index";
import {UserProvider} from "./Store/Providers/UserProvider";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
      <Provider store={store}>
          <UserProvider>
              <BrowserRouter>
                  <App />
              </BrowserRouter>
          </UserProvider>
      </Provider>
  </React.StrictMode>
);

