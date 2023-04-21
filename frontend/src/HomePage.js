import './styles/HomePage.css'
import { AdminHomePage } from './AdminHomePage'
import { EmployeeHomePage } from './EmployeeHomePage'
import {useNavigate} from 'react-router-dom';

export const HomePage = () => {
    const navigate = useNavigate();
    return (
        <>
            {
                sessionStorage.getItem('userType') === "admin" ? <AdminHomePage/> :
                sessionStorage.getItem('userType') === "employee" ? <EmployeeHomePage/>:
                (<button className="btn homepage-login-btn" onClick={() => navigate("/login")}>Login</button>)
            }
        </>

    )
}