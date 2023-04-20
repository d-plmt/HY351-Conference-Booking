import { useEffect } from 'react';
import loginPageCss from './styles/LoginPage.css'
import {useState} from 'react';
import { useNavigate } from 'react-router-dom';

export const LoginPage = (props) => {
    const navigate = useNavigate();
    const [email, setEmail]         = useState('');
    const [password, setPassword]   = useState('');
    const [error, setError]         = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            sessionStorage.setItem('userType', 'admin');
            sessionStorage.setItem('user', 'user1');
            navigate('/');
            // const response = await fetch('', {
            //     method: 'POST',
            //     headers: {
            //         'Content-type': 'application/json'
            //     },
            //     body: JSON.stringify({email, password})
            // });
            // const data = response.json();
            // if (data.success) {


            //     //redirect page here
            // }
            // else {
            //     setError(data.message);
            // }
        }
        catch (error) {
            console.error(error);
            setError('An error occured during the authentication process');
        }
    }

    return (
        <div className="loginpage">
            <form className="login-form" onSubmit={handleSubmit}>
                <h3>Login Here</h3>

                <label htmlFor="email" className="login-label">Email</label>
                <input className="login-input" type="text" placeholder="Email" id="email" value={email} onChange={e => setEmail(e.target.value)}/>

                <label htmlFor="password" className="login-label">Password</label>
                <input className="login-input" type="password" placeholder="Password" id="password" value={password} onChange={e => setPassword(e.target.value)}/>

                <button className="btn login-btn">
                    Log In
                </button>
            </form>

        </div>
    )
}