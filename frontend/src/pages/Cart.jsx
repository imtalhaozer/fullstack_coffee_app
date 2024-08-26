import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar.jsx';
import Footer from '../components/Footer';

export const Cart = () => {
  const [data, setData] = useState([]);

  const fetchData = async () => {
    try {
      const token = localStorage.getItem('token');
      const userResponse = await axios.get("http://localhost:8080/get/currentUserId", {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (userResponse.data) {
        const response = await axios.get("http://localhost:8080/cart/get", {
          headers: {
            'Authorization': `Bearer ${token}`
          },
          params: {
            userId: userResponse.data
          }
        });
        setData(response.data);
      }
    } catch (error) {
      console.log("get error", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const deleteItem = async (itemId) => {
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`http://localhost:8080/cart/delete/${itemId}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      fetchData();
    } catch (error) {
      console.log("Error deleting item:", error);
    }
  };

  return (
    <>
      <Navbar className="z-10" />
      <div className="ml-auto mr-auto max-w-[1200px] pb-[80px]">
        <div className="pt-[280px] pb-[50px] text-center font-bold text-[30px] z-0">
          <h1>SEPET DETAYI</h1>
        </div>
        <div className='border-b-2'>
          <ul className="list-none">
            <li className="flex border-b-2 pb-2">
              <div className="w-[50px] text-left">Ürün</div>
              <div className="flex-grow text-left"></div>
              <div className="w-[100px] text-right">Fiyat</div>
              <div className="w-[50px] text-right">Adet</div>
              <div className="w-[150px] text-right">Toplam</div>
            </li>
            {data.map(item => {
              const totalPrice = item.product.productPrice * item.productQuantity; 
              return (
                <li key={item.itemId} className="flex pt-2">
                  <div className="w-[50px] text-left">{item.product.productName}</div>
                  <div className="flex-grow text-center">{item.productWeight + " " + item.productSize}</div>
                  <div className="w-[100px] text-right">{item.product.productPrice}₺</div>
                  <div className="w-[50px] text-center">{item.productQuantity}</div>
                  <div className="w-[150px] text-right">{totalPrice}₺</div>
                  <div className='pl-[10px]'>
                    <button onClick={() => deleteItem(item.itemId)} className='bg-red-500 w-5 text-white'>x</button>
                  </div>
                </li>
              );
            })}
          </ul>
        </div>
      </div>
      <Footer />
    </>
  );
};
