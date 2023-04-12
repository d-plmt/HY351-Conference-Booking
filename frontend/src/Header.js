import './styles/Header.css'
import logo from './images/logo.png'

export const Header = () => {
    return (
        <div className="header">
            <img className="logo" alt="logo" src={logo} >

            </img>
            <div className="title">
                Conference Room Booking System
            </div>
        </div>
    )
}