import { BrowserRouter, Routes, Route } from "react-router-dom";
import { HomePage } from './pages/HomePage'
import { Product } from './pages/Product'
import { Login } from './pages/Login'
import { Register } from './pages/Register'
import { Cart } from './pages/Cart'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/products/:id' element={<Product />}/>
        <Route path='/login' element={<Login/>} />
        <Route path='/register' element={<Register/>} />
        <Route path='/cart' element={<Cart/>} />
        <Route path='/' element={<HomePage />}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
