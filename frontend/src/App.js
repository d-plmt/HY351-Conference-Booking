import logo from './logo.svg';
import './styles/App.css';
import {Routes, Route, Link} from "react-router-dom";
import { Header } from './Header';
import { LoginPage } from './LoginPage';
import { useState } from 'react';
import { HomePage } from './HomePage';
import { NewReservation } from './NewReservation';

export const App = () => {

  const [userType, setUserType] = useState('');
  const [user, setUser] = useState(null);

  const handleLogin = (userType, user) => {
    setUser(user);
    setUserType(userType);
  }

  return (
    <div className="root">
      <Link to=".." relative="/" style={{ textDecoration: 'none', color: 'black'}}>
        <Header/>
      </Link>
        
      <Routes>
        <Route 
        path="/"
        element = {<HomePage/>} />
        <Route path="/login" element={<LoginPage onLogin={handleLogin}/>} />
        {/* <Route path="/new" element={<NewReservation/>}/> */}
      </Routes>
    </div>

  );
}