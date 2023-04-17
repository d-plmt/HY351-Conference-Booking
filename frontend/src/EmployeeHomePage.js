import './styles/EmployeeHomePage.css';
import {Routes, Route, Link} from 'react-router-dom';

export const EmployeeHomePage = () => {

    return (
        <div className="homepage">
            Employee Homepage
            <Link to="/new">
                <button className="btn new-reservation-btn">New Reservation</button>
            </Link>
        </div>
    )
}
