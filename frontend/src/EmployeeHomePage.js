import { TabContent, TabPane } from 'react-bootstrap';
import './styles/EmployeeHomePage.css';
import {Routes, Route, Link} from 'react-router-dom';
import {NewReservation} from './NewReservation';
import React, { useEffect, useState } from "react";
import { Table, Tab, Nav } from "react-bootstrap";

export const EmployeeHomePage = () => {

    const [activeTab, setActiveTab] = useState("new");
    const [requestHistory, setRequestHistory] = useState([]);

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                const response = await fetch('http://localhost:8080/requests');
                const data = await response.json();

                setRequestHistory(data);
            }
            catch (error) {
                console.error(error);
            }
        }

        fetchRequests();
    }, []);

    const handleSelectTab = (eventKey) => {
        setActiveTab(eventKey);
      };

    return (
        <div className="homepage">
            {/* <div>Employee Homepage</div> */}
            {/* <Link to="/new">
                <button className="btn new-reservation-btn">New Reservation</button>
            </Link> */}
            <Tab.Container activeKey={activeTab} onSelect={handleSelectTab}>
                <Nav variant="tabs" className="mb-4">
                    <Nav.Item>
                    <Nav.Link eventKey="new">New Reservation</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                    <Nav.Link eventKey="history">Request History</Nav.Link>
                    </Nav.Item>
                </Nav>

                <TabContent>
                    <TabPane eventKey="new">
                        <NewReservation></NewReservation>
                    </TabPane>
                    <TabPane eventKey="history">
                        <Table bordered hover className="admin-table">
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Room ID</th>
                                    <th>Start Time</th>
                                    <th>End Time</th>
                                    <th>Purpose</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                {requestHistory.map((req) => (
                                    <tr key={req.id.date+'-'+req.id.roomId+'-'+req.id.employeeId}>
                                        <td className="admin-td">{req.id.date}</td>
                                        <td className="admin-td td-num">{req.id.roomId}</td>
                                        <td className="admin-td td-num">{req.timeSlots[0].startTime+':00'}</td>
                                        <td className="admin-td td-num">{req.timeSlots[req.timeSlots.length-1].endTime+':00'}</td>
                                        <td className="admin-td">{req.purpose}</td>
                                        <td className="admin-td">{req.status}</td>
                                    
                                    </tr>
                                ))

                                }
                            </tbody>
                        </Table>
                    </TabPane>
                </TabContent>
            </Tab.Container>
        </div>
    )
}
