import './styles/AdminHomePage.css';
import React, { useEffect, useState } from "react";
import { Tab, Nav } from "react-bootstrap";

export const AdminHomePage = () => {

    const [activeTab, setActiveTab] = useState("pending");
    const [pendingRequests, setPendingRequests] = useState([]);
    const [reviewedRequests, setReviewedRequests] = useState([]);
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                const response = await fetch('http://localhost:8080/requests');
                const data = await response.json();
                setPendingRequests(data.filter((request) => request.status === 'pending'));
                setReviewedRequests(data.filter((request) => request.status !== 'pending'));
            }
            catch (error) {
                console.error(error);
            }

        }

        fetchRequests();
    }, []);

    useEffect(() => {
        const fetchReservations = async () => {
            try {
                const response = await fetch('http://localhost:8080/reservations');
                const data = await response.json();

                setReservations(data);
            }
            catch (error) {
                console.error(error);
            }
        }

        fetchReservations();
    }, []);

    const handleSelectTab = (eventKey) => {
      setActiveTab(eventKey);
    };

    return (
        <div className="homepage">
        <Tab.Container activeKey={activeTab} onSelect={handleSelectTab}>
            <Nav variant="tabs" className="mb-4">
                <Nav.Item>
                <Nav.Link eventKey="pending">Pending Requests</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                <Nav.Link eventKey="history">Request History</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                <Nav.Link eventKey="cancel">Cancel Reservation</Nav.Link>
                </Nav.Item>
            </Nav>
            <Tab.Content>
                <Tab.Pane eventKey="pending">
                <h2>Pending Requests</h2>
                {/* insert code for pending requests */}
                </Tab.Pane>
                <Tab.Pane eventKey="history">
                <h2>Request History</h2>
                {/* insert code for request history */}
                </Tab.Pane>
                <Tab.Pane eventKey="cancel">
                <h2>Cancel Reservation</h2>
                {/* insert code for request history */}
                </Tab.Pane>
            </Tab.Content>
        </Tab.Container>
        </div>
    )
}