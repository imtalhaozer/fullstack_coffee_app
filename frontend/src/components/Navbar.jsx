import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png';
import { FaUserCircle } from "react-icons/fa";
import { IoSearch } from "react-icons/io5";
import { BiBasket } from "react-icons/bi";

const Navbar = () => {
  const [isScrolled, setIsScrolled] = useState(false);
  const [menuIndex, setMenuIndex] = useState(null);

  useEffect(()=>{
    
  })

  // Scroll section
  const handleScroll = () => {
    if (window.scrollY > 50) {
      setIsScrolled(true);
    } else {
      setIsScrolled(false);
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  // Menu section
  const handleMouseOver = (index) => {
    setMenuIndex(index);
  };

  const handleMouseOut = () => {
    setMenuIndex(null);
  };

  const links = [
    { href: '', text: 'KAHVE', menu: [{ href: '', text: 'YEŞİL KAHVE-ÇİĞ' }, { href: '', text: 'KAVRULMUŞ KAHVE' }, { href: '', text: 'TOPTAN KAHVE' }] },
    { href: '', text: 'ÇİKOLATA', menu: [{ href: '', text: 'PREMIUM' }, { href: '', text: 'STANDART' }] },
    { href: '', text: 'TATLI' },
    { href: '', text: 'EKİPMAN' },
    { href: '', text: 'HEDİYELİK' }
  ];

  return (
    <div>
      <nav className={`fixed w-full flex items-center justify-between bg-primary transition-all duration-300 ${isScrolled ? "h-[120px]" : "h-[160px]"} z-10`}>
        <div className="flex-1 flex justify-center pl-[150px]">
          <Link to="/">
            <img src={logo} alt="logo" className={`transition-all duration-300 ease-in-out ${isScrolled ? "h-[100px]" : "h-[150px]"} w-auto`} />
          </Link>
        </div>
        <div className="flex-none flex items-center space-x-4 mr-4">
          <Link to="/login">
            <FaUserCircle className='h-6 w-6' />
          </Link>
          <Link to="/search">
            <IoSearch className='h-6 w-6' />
          </Link>
          <Link to="/cart">
            <BiBasket className='h-6 w-6' />
          </Link>
        </div>
      </nav>
      <nav className='fixed'>
        <div className={`fixed bg-primary border-t-2 z-0 border-secondary h-[50px] w-full transition-all duration-300 ease-in-out ${isScrolled ? "mt-[120px]" : "mt-[160px]"}`}>
          <div className='flex items-center justify-center space-x-4 h-full'>
            <ul className="none flex space-x-4">
              {links.map((link, index) => (
                <li
                  key={index}
                  className="box-border h-[50px] flex items-center justify-center"
                  onMouseOver={() => handleMouseOver(index)}
                  onMouseOut={handleMouseOut}
                >
                  <Link
                    to={link.href}
                    className="hover:font-bold flex"
                  >
                    {link.text}
                  </Link>
                  {link.menu && menuIndex === index && (
                    <div
                      className="absolute top-full left-1/2 transform -translate-x-1/2 flex items-center justify-center w-full h-auto bg-secondary"
                      onMouseOver={() => handleMouseOver(index)}
                      onMouseOut={handleMouseOut}
                    >
                      <ul className="flex flex-wrap space-x-4 items-center py-1">
                        {link.menu.map((submenu, subIndex) => (
                          <li key={subIndex} className='none'>
                            <Link to={submenu.href} className="flex items-center px-4 py-2 hover:bg-primary">
                              {submenu.text}
                            </Link>
                          </li>
                        ))}
                      </ul>
                    </div>
                  )}
                </li>
              ))}
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Navbar;
