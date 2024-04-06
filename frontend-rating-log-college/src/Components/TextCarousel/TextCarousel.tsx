import React from 'react';
import {Swiper, SwiperSlide, useSwiper} from 'swiper/react';
import {Navigation, Pagination, Scrollbar, A11y, Virtual} from 'swiper/modules';
import 'swiper/css';
import './TextCarousel.scss';

import img from '../../assets/images/Arrow.svg';
import img2 from '../../assets/images/Arrow2.svg';


interface CarouselProps {
    items: { id: number; text: string; active: boolean }[];
    onChange: (index: number) => void;
}

const TextCarousel: React.FC<CarouselProps> = ({ items, onChange }) => {
    const handleSlideChange = (swiper: any) => {
        onChange(swiper.realIndex);
    };

    const navigationPrevRef = React.useRef(null)
    const navigationNextRef = React.useRef(null)

    const swiper = useSwiper();

    return (
        <Swiper
            className={`text-carousel`}
            modules={[Navigation, A11y]}
            spaceBetween={30}
            grabCursor={true}
            scrollbar={{
                hide: true,
            }}
            loop={true}
            slidesPerView={1}
            navigation={{
                prevEl: navigationPrevRef.current,
                nextEl: navigationNextRef.current,
            }}
            onSwiper={(swiper: any) => {}}
            onSlideChange={(swiper: any) => handleSlideChange(swiper)}
        >
            <button className={`carousel-btn prev`} ref={navigationPrevRef}><img src={img2} alt=""/></button>
            {items.map((item) => (
                <SwiperSlide key={item.id}>
                    <div className={`text-item`}>
                        {item.text}
                    </div>
                </SwiperSlide>
            ))}
            <button className={`carousel-btn next`} ref={navigationNextRef}><img src={img} alt=""/></button>
        </Swiper>
    );
};

export default TextCarousel;
