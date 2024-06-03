import React, { useEffect } from 'react';
import { BounceLoader } from 'react-spinners';
import './Spinner.scss';

interface SpinnerProps {
    loading: boolean;
}

const Spinner: React.FC<SpinnerProps> = ({ loading }) => {
    useEffect(() => {
        if (loading) {
            document.body.classList.add('no-scroll');
        } else {
            document.body.classList.remove('no-scroll');
        }
    }, [loading]);

    return (
        <div className={`spinner ${loading ? 'visible' : 'hidden'}`}>
            <BounceLoader color="#8383FF" loading={loading} size={60} />
        </div>
    );
};

export default Spinner;
