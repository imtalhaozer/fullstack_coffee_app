import React from 'react'
import Footer from '../components/Footer'
import HomeProduct from '../components/HomeProduct'
import  Middle  from '../components/Middle'
import Navbar from '../components/Navbar'

export const HomePage = () => {
  return (
    <>
        <Navbar/>
        <HomeProduct/>
        <Middle/>
        <Footer/> 
    </>
  )
}
