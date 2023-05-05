import { TabContent, TabPane } from 'react-bootstrap';
import './styles/EmployeeHomePage.css';
import {NewReservation} from './NewReservation';
import React, { useEffect, useState } from "react";
import { Table, Tab, Nav } from "react-bootstrap";
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
import moment from 'moment'

export const EmployeeHomePage = () => {

    const MySwal = withReactContent(Swal)

    const [activeTab, setActiveTab] = useState("new");
    const [requestHistory, setRequestHistory] = useState([]);

    useEffect(() => {

    }, []);

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                const response = await fetch(`http://localhost:8080/requests/employee/${sessionStorage.getItem("userId")}`);
                const data = await response.json();

                setRequestHistory(data);
            }
            catch (error) {
                console.error(error);
            }
        }

        if (!requestHistory.length) {
            fetchRequests();
        }
        
        const today = new Date();
        const upcomingRequests = [];
        requestHistory.forEach((request) => {
            const requestDate = new Date(request.id.date);
            const diffTime = Math.abs(requestDate-today);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 
            request.diffDays = diffDays;
            if (diffDays <= 50) {
                upcomingRequests.push(request);
            }
        })
        Swal.fire({
            position: 'top-end',
            title: 'Upcoming Reservations',
            html: `${upcomingRequests.map((request) => {
                return (
                    `<div>
                        In <b>${request.diffDays} days:</b>&nbsp;&nbsp;&nbsp;room ${request.id.roomId}&nbsp;&nbsp;&nbsp;${request.timeSlots[0].startTime}:00&nbsp;-&nbsp;${request.timeSlots[request.timeSlots.length-1].endTime}:00
                    </div>`
                );
            }).join('')}`,
            showConfirmButton: false,
            width: 450,
            timerProgressBar: true,
            timer: 20000,
            showCloseButton: true,
        })
    }, [requestHistory]);

    const handleSelectTab = (eventKey) => {
        setActiveTab(eventKey);
      };

    return (
        <div className="homepage">
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
