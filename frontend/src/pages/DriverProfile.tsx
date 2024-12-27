import React, { useState, useEffect } from 'react';
import { fetchDriverData } from '../services/profileService';
import '../styles/profile.css';

interface DriverProfileProps {
    onLogout: () => void;
}

const DriverProfile: React.FC<DriverProfileProps> = ({onLogout}) => {
    const [driverName, setDriverName] = useState<string>("");
    const [balance, setBalance] = useState<number>(0);
    const [plateNumber, setPlateNumber] = useState<string>("");
    const [paymentMethod, setPaymentMethod] = useState<string>("");
    const [licenseNumber, setLicenseNumber] = useState<string>("");

    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchData = async () => {
            const driverData = await fetchDriverData(token!);
            setDriverName(driverData.username);
            setBalance(driverData.balance);
            setPlateNumber(driverData.plate_number);
            setPaymentMethod(driverData.payment_method);
            setLicenseNumber(driverData.license_number);
        };

        fetchData();
    }, []);

    return (
        <div className="profile-container">
            <nav className="navbar">
                <div className="navbar-title">Sayes</div>
                <div className="navbar-links">
                <a href="/">Home</a>
                <a href="/login" onClick={onLogout}> Logout</a>
                </div>
            </nav>
            <h2 className="profile-title">Driver Profile</h2>
            <div className="profile-info">
                <p><strong>Name:</strong> {driverName}</p>
                <p><strong>Balance:</strong> ${balance.toFixed(2)}</p>
                <p><strong>Plate Number:</strong> {plateNumber}</p>
                <p><strong>Payment Method:</strong> {paymentMethod}</p>
                <p><strong>License Number:</strong> {licenseNumber}</p>
            </div>
        </div>
    );
};

export default DriverProfile;
