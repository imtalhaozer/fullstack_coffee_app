import React, { useState, useEffect } from 'react';
import axios from 'axios';
import coffeeImage from '../assets/coffee.png';
import { useParams } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

export const Product = () => {
    const [data, setData] = useState([]);
    const [userId, setUserId] = useState(null);
    const { id } = useParams();
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const productResponse = await axios.get("http://localhost:8080/product/all");
                setData(productResponse.data);
                
                const token = localStorage.getItem('token');
                if (token) {
                    const userResponse = await axios.get("http://localhost:8080/get/currentUserId", {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    });
                    setUserId(userResponse.data);
                }
            } catch (error) {
                console.error("Data fetch error", error);
            }
        };

        fetchData();
    }, []); 

    const handleSelect = async () => {
        if (!userId) {
            console.log("Kullanıcı bilgisi yüklenmemiş.");
            return;
        }

        const firstSelectValue = document.querySelector('select[name="firstSelect"]').value;
        const secondSelectValue = document.querySelector('select[name="secondSelect"]').value;
        const thirdSelectValue = document.querySelector('input[name="thirdSelect"]').value;

        try {
            const response = await axios.post("http://localhost:8080/cart/add",
                {
                    userId: userId,
                    productId: id, // Product ID
                    quantity: thirdSelectValue,
                    size: secondSelectValue,
                    weight: firstSelectValue,
                },
                {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}` 
                }}
            );

            if (response.data) {
                console.log("Ürün sepete eklendi");
            } else {
                console.log("Sepete ekleme başarısız");
            }
        } catch (error) {
            console.error("Sepete ekleme hatası", error);
        }
    };

    const product = data.find((item) => item.productId === Number(id));

    return (
        <>
            <Navbar />
            <div className='pt-[250px] pb-[50px] flex justify-center items-center space-x-4'>
                <div className='flex-shrink-0 w-1/3'>
                    <img src={coffeeImage} alt="image" />
                </div>
                <div className='shadow-lg p-4 rounded-lg bg-primary'>
                    {product ? (
                        <div>
                            <h1 className='text-2xl font-bold mb-2'>{product.productName}</h1>
                            <p className='text-lg font-semibold mb-2'>Fiyat: {product.productPrice} ₺</p>
                            <p className='text-lg font-semibold'>Gramaj</p>
                            <select className='mb-2 border-[0.5px] border-green-900' name='firstSelect'>
                                <option value="250 gr">250 GR</option>
                                <option value="1 kg">1 KG</option>
                            </select>
                            <p className='text-lg font-semibold'>Öğütme Derecesi</p>
                            <select className='mb-2 border-[0.5px] border-green-900' name='secondSelect'>
                                <option value="çekirdek kahve">Çekirdek</option>
                                <option value="filtre kahve">Filtre Kahve</option>
                                <option value="Türk kahvesi">Türk Kahvesi</option>
                            </select>
                            <p className='text-lg font-semibold'>Adet</p>
                            <input type='number' onChange={(e) => setQuantity(e.target.value)} className='mb-2 w-8 text-center border-[0.5px] border-green-900' name='thirdSelect' value={quantity} />
                            <button onClick={handleSelect} className='bg-fourth hover:bg-orange-900 hover:text-cyan-50 w-full rounded-xl h-[44px]'>SEPETE EKLE</button>
                        </div>
                    ) : (
                        <p className='text-red-500'>Ürün bulunamadı.</p>
                    )}
                </div>
            </div>
            <div className='text-center'>
                <p className='text-2xl font-bold mb-2'>Hakkında</p>
                {product && <p className='text-gray-700 mb-4'>{product.productDescription}</p>}
            </div>
            <Footer />
        </>
    );
};
