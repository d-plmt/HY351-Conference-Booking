import './styles/Header.css'
import logo from './images/logo.png'

export const Header = () => {
    return (
        <div className="header">
            <div className="header-mid">
                <img className="logo" alt="logo" src={logo} >

                </img>
                <div className="title">
                    Conference Room Booking System
                </div>
            </div>
            {/* <div className="header-right">
                <button className="btn logout-btn">
                    Logout
                </button>
            </div> */}
        </div>
    )
}