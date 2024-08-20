import { faBed, faHome, faSignInAlt, faSignOutAlt, faUser, faUserPlus, faUserShield } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

function Navbar() {
    const isAuthenticated = ApiService.isAuthenticated();
    const isAdmin = ApiService.isAdmin();
    const isUser = ApiService.isUser();
    const navigate = useNavigate();

    const handleLogout = () => {
        const isLogout = window.confirm('Are you sure you want to logout this user?');
        if (isLogout) {
            ApiService.logout();
            navigate('/home');
        }
    };

    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <NavLink to="/home">Hotel Booking</NavLink>
            </div>
            <ul className="navbar-ul">
                <li>
                    <NavLink to="/home" activeclassname="active">
                        <FontAwesomeIcon icon={faHome} /> Home
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/rooms" activeclassname="active">
                        <FontAwesomeIcon icon={faBed} /> Rooms
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/find-booking" activeclassname="active">
                        <FontAwesomeIcon icon={faUser} /> My Booking
                    </NavLink>
                </li>

                {isUser && (
                    <li>
                        <NavLink to="/profile" activeclassname="active">
                            <FontAwesomeIcon icon={faUser} /> Profile
                        </NavLink>
                    </li>
                )}
                {isAdmin && (
                    <li>
                        <NavLink to="/admin" activeclassname="active">
                            <FontAwesomeIcon icon={faUserShield} /> Admin
                        </NavLink>
                    </li>
                )}

                {!isAuthenticated && (
                    <>
                        <li>
                            <NavLink to="/login" activeclassname="active">
                                <FontAwesomeIcon icon={faSignInAlt} /> Login
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/register" activeclassname="active">
                                <FontAwesomeIcon icon={faUserPlus} /> Register
                            </NavLink>
                        </li>
                    </>
                )}
                {isAuthenticated && (
                    <li onClick={handleLogout}>
                        <FontAwesomeIcon icon={faSignOutAlt} /> Logout
                    </li>
                )}
            </ul>
        </nav>
    );
}

export default Navbar;