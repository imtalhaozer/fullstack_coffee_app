import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

export const Login = () => {
    const [username, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate(); 

    const handleSubmit = async () => {
        try {
            const response = await axios.post('http://localhost:8080/generatetoken', {
                username,
                password,
            });

            const token = response.data;

            if(token){
                localStorage.setItem('token', token);
                navigate("/");
            } else {
                setError('Geçersiz kullanıcı'); //invalid user error -@talha
            }
        } catch (error) {
            setError('Geçersiz kullanıcı'); //invalid user error -@talha
        }
    };

    return (
        <>
            <Navbar/>
            <div className='flex justify-center items-center pt-[280px] pb-[50px]'>
                <div className='text-center'>
                    <div className='pb-[50px]'>
                        <h1 className='text-[38px] font-bold'>ÜYE GİRİŞİ</h1>
                    </div>
                    <div className='mb-3 text-left'>
                        <label htmlFor="customer_email" className='block font-bold text-[20px]'>EMAIL</label>
                        <input type="email" onChange={(e) => setUserName(e.target.value)} value={username} className='border border-gray-400 h-[60px] w-[432px] px-[29px]' />
                    </div>
                    <div className='text-left mb-3'>
                        <label htmlFor="password" className='block font-bold text-[20px]'>ŞiFRE</label>
                        <input type="password" onChange={(e) => setPassword(e.target.value)} value={password} className='border border-gray-400 h-[60px] w-[432px] px-[29px]'/>
                    </div>
                    <div className='text-left mb-6'>
                        <Link to="/register" className='text-cyan-500 underline'>Hesabın yoksa kaydol</Link>
                    </div>
                    <div className='text-left mb-6'>
                        {error && <p style={{ color: 'red' }}>{error}</p>}
                    </div>
                    <div>
                        <button onClick={handleSubmit} className='bg-black text-fourth hover:shadow-2xl min-h-[60px] w-auto px-[28px] font-bold text-[20px] rounded-xl'>GİRİŞ</button>
                    </div>
                </div>
            </div>
            <Footer/>
        </>
    );
};
