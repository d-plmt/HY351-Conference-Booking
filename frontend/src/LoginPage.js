import {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './styles/LoginPage.css';

export const LoginPage = () => {
    const navigate = useNavigate();
    const [email, setEmail]         = useState('');
    const [password, setPassword]   = useState('');
    const [error, setError]         = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            sessionStorage.clear();
            const userResponse = await fetch(`http://localhost:8080/users/email/${email}`);
            if (userResponse.status === 200) {
                const userData = await userResponse.json();
                if (password === userData.password) {
                    const adminResponse = await fetch(`http://localhost:8080/admins/${userData.id}`);
                    
                    if (adminResponse.status === 200) {
                        
                        sessionStorage.setItem('userType', 'admin');
                        sessionStorage.setItem('userId', userData.id);
                        console.log(sessionStorage);
                        navigate('/');
                    }
                    else {
                        
                        const employeeResponse = await fetch(`http://localhost:8080/employees/${userData.id}`);
                        if (employeeResponse.status === 200) {
                            sessionStorage.setItem('userType', 'employee');
                            sessionStorage.setItem('userId', userData.id);
                            navigate('/');
                        }
                        else {
                            throw new Error('Error!');
                        }
                    }
                }
                else {
                    throw new Error('Incorrect password!');
                }
            }
            else {
                throw new Error('This email does not correspond to any account!');
            }
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