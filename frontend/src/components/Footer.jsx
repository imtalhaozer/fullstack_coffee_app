import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png';

const Footer = () => {
  return (
    <div className='bg-fourth py-6'>
      <div className='container mx-auto flex justify-center items-center text-center space-x-12'>
        <div className='flex w-1/5'>
          <Link to="/">
            <img src={logo} alt="Logo" />
          </Link>
        </div>
        <div className='flex flex-col items-center space-y-2'>
          <Link className='font-bold' to="/kurumsal">Kahve Evi Kurumsal</Link>
          <Link to="/hakkimizda">Hakkımızda</Link>
          <Link to="/hedeflerimiz">Hedeflerimiz</Link>
          <Link to="/iletisim">İletişim Bilgilerimiz</Link>
        </div>
        <div className='flex'>
          <a href="https://github.com/imtalhaozer">2024 @imtalhaozer</a>
        </div>
      </div>
    </div>
  );
}

export default Footer;
