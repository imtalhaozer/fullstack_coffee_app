import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import coffeeImage from '../assets/coffee.png';

const HomeProduct = () => {
    const [data, setData] = useState(null);

    useEffect(() => {
        axios.get("http://localhost:8080/product/all")
            .then(response => {
                setData(response.data);
            })
            .catch(error => {
                console.log("data error", error);
            });
    }, []);

    return (
        <>
            <div className='pt-[250px] pb-[40px] flex justify-center items-center'>
                <p className='font-bold text-[40px]'>YENİ KAHVELER</p>
            </div>
            <div className="container mx-auto px-4 sm:px-6 lg:px-40">
                {data ? (
                    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                        {data.map(item => (
                            <div key={item.productId} className="w-full  p-4 ">
                                <Link to={`/products/${item.productId}`}>
                                    <img src={coffeeImage} alt="coffee" className="w-full h-auto mb-4" />
                                    <h3 className="text-xl font-semibold mb-2">{item.productName}</h3>
                                    <p className="font-bold mb-2">{item.productPrice} ₺</p>
                                    <p>{item.productDescription}</p>
                                </Link>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>Loading products...</p>
                )}
            </div>
        </>
    );
};

export default HomeProduct;
