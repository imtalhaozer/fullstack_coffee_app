import React from 'react';
import forest from '../assets/forest.jpg';
import unicoffee from '../assets/unicoffee.webp';

export const Middle = () => {
  return (
    <div className='flex my-[60px] justify-center items-center'
          style={{ backgroundImage: `url(${forest})` }}>
        <div className='flex flex-row justify-center items-center space-x-4'>
            <img className='w-1/5 ml-[40px]' src={unicoffee} alt="Unicoffee" />
            <p className='font-caveat text-[50px] text-center'>her yudum da ayrı bir sihir...<br/> kahve keyfinizi sihirle taçlandırın</p>
        </div>
    </div>
  );
}

export default Middle;