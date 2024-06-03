import React from 'react';
import { ToastContainer, toast, ToastOptions, TypeOptions } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './Toasty.scss';

const notify = (message: string, type: TypeOptions, options?: ToastOptions) => {
    toast(message, { type, ...options });
};

const Toasty: React.FC = () => (
    <ToastContainer />
);

export { Toasty, notify };
