import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

export const Register = () => {
    const [userName, setUserName] = useState('');
    const [userLastName, setUserLastName] = useState('');
    const [userMail, setUserMail] = useState('');
    const [userPassword, setUserPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate(); 

    const handleSubmit = async () => {
        try {
            const response = await axios.post('http://localhost:8080/user/save', {
                userName,
                userLastName,
                userMail,
                userPassword,
            });
            const id=response.data
            if (id) {
                navigate("/login");
            } else {
                setError('Geçersiz işlem veya kullanıcı kayıtlı');
            }
        } catch (error) {
            setError('Geçersiz işlem veya kullanıcı kayıtlı');
        }
    };

    return (
        <>
            <Navbar/>
            <div className='flex justify-center items-center pt-[280px] pb-[50px]'>
                <div className='text-center'>
                    <div className='pb-[50px]'>
                        <h1 className='text-[38px] font-bold'>KAYIT</h1>
                    </div>
                    <div className='mb-3 text-left'>
                        <label htmlFor="user_name" className='block font-bold text-[20px]'>İSİM</label>
                        <input type="text" onChange={(e) => setUserName(e.target.value)} value={userName} className='border border-gray-400 h-[60px] w-[432px] px-[29px]' />
                    </div>
                    <div className='mb-3 text-left'>
                        <label htmlFor="user_last_name" className='block font-bold text-[20px]'>SOYİSİM</label>
                        <input type="text" onChange={(e) => setUserLastName(e.target.value)} value={userLastName} className='border border-gray-400 h-[60px] w-[432px] px-[29px]' />
                    </div>
                    <div className='mb-3 text-left'>
                        <label htmlFor="user_mail" className='block font-bold text-[20px]'>E-POSTA</label>
                        <input type="email" onChange={(e) => setUserMail(e.target.value)} value={userMail} className='border border-gray-400 h-[60px] w-[432px] px-[29px]' />
                    </div>
                    <div className='mb-3 text-left'>
                        <label htmlFor="user_password" className='block font-bold text-[20px]'>ŞİFRE</label>
                        <input type="password" onChange={(e) => setUserPassword(e.target.value)} value={userPassword} className='border border-gray-400 h-[60px] w-[432px] px-[29px]'/>
                    </div>
                    <div className='text-left mb-6'>
                        <Link to="/login" className='text-cyan-500 underline'>Zaten hesabın var mı? Giriş yap</Link>
                    </div>
                    <div className='text-left mb-6'>
                        {error && <p style={{ color: 'red' }}>{error}</p>}
                    </div>
                    <div>
                        <button onClick={handleSubmit} className='bg-black text-fourth hover:shadow-2xl min-h-[60px] w-auto px-[28px] font-bold text-[20px] rounded-xl'>KAYIT OL</button>
                    </div>
                </div>
            </div>
            <Footer/>
        </>
    );
};
