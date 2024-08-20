import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Assurez-vous d'importer useNavigate
import RoomResult from "../common/RoomResult";
import RoomSearch from "../common/RoomSearch";

const HomePage = () => {
    const [roomSearchResults, setRoomSearchResults] = useState([]);
    const navigate = useNavigate();  // Initialisez navigate ici

    const handleSearchResult = (results) => {
        setRoomSearchResults(results);
    };

    return (
        <div className="home">
            {/* HEADER / BANNER ROOM SECTION */}
            <section>
                <header className="header-banner">
                    <img src="./assets/images/hotel.webp" alt="Hotel Booking" className="header-image" />
                    <div className="overlay"></div>
                    <div className="animated-texts overlay-content">
                        <h1>
                            Welcome to <span className="hotel-booking">Hotel Booking</span>
                        </h1><br />
                        <h3>Step into a haven of comfort and care</h3>
                    </div>
                </header>
            </section>

            {/* SEARCH/FIND AVAILABLE ROOM SECTION */}
            <RoomSearch handleSearchResult={handleSearchResult} />
            <RoomResult roomSearchResults={roomSearchResults} />

            {/* Transformation du lien en bouton */}
            <h4>
                <button 
                    className="view-rooms-home-button" 
                    onClick={() => navigate('/rooms')}
                >
                    All Rooms
                </button>
            </h4>
        

            <h2 className="home-services">Services at <span className="hotel-booking">Hotel Booking</span></h2>

            {/* SERVICES SECTION */}
            <section className="service-section">
                <div className="service-card">
                    <img src="./assets/images/restaurant.jpg" alt="Restaurant" />
                    <div className="service-details">
                        <h3 className="service-title">Restaurant</h3>
                        <p className="service-description">Savor exquisite culinary delights at our restaurant, offering a diverse menu of local and international cuisine and best services and elegant setting.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/gardpar.jpg" alt="Garden" />
                    <div className="service-details">
                        <h3 className="service-title">Garden Partys</h3>
                        <p className="service-description">Relax and unwind in our beautifully landscaped garden, a peaceful oasis with All Fresh drinks and Party Music.</p>
                    </div>
                </div>

                <div className="service-card">
                    <img src="./assets/images/aircondit.png" alt="Air Conditioning" />
                    <div className="service-details">
                        <h3 className="service-title">Air Conditioning</h3>
                        <p className="service-description">Stay cool and comfortable throughout your stay with our individually controlled in-room air conditioning.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/Bardrinks.jpg" alt="Mini Bar" />
                    <div className="service-details">
                        <h3 className="service-title">Mini Bar</h3>
                        <p className="service-description">Enjoy a convenient selection of beverages and snacks stocked in your room's mini bar with no additional cost.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/parking.PNG" alt="Parking" />
                    <div className="service-details">
                        <h3 className="service-title">Parking</h3>
                        <p className="service-description">We offer on-site parking for your convenience. Please inquire about valet parking options if available.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/wifi.jpg" alt="WiFi" />
                    <div className="service-details">
                        <h3 className="service-title">WiFi</h3>
                        <p className="service-description">Stay connected throughout your stay with complimentary high-speed Wi-Fi access available in all guest rooms and public areas.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/spa.jpg" alt="SPA" />
                    <div className="service-details">
                        <h3 className="service-title">Relaxing</h3>
                        <p className="service-description">Relax and get your energy back in our beautiful and peaceful oasis.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/swimming.jpg" alt="Swimming Pool" />
                    <div className="service-details">
                        <h3 className="service-title">Swimming Pool</h3>
                        <p className="service-description">Take a refreshing dip in our sparkling outdoor swimming pool, surrounded by comfortable loungers and offering stunning views of the surroundings.</p>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default HomePage;
