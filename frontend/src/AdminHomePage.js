import './styles/AdminHomePage.css';
import React, { useEffect, useState } from "react";
import { Table, Tab, Nav } from "react-bootstrap";
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'

export const AdminHomePage = () => {

    const MySwal = withReactContent(Swal)

    const [activeTab, setActiveTab] = useState("pending");
    const [pendingRequests, setPendingRequests] = useState([]);
    const [reviewedRequests, setReviewedRequests] = useState([]);
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                const response = await fetch('http://localhost:8080/requests');
                const data = await response.json();
                console.log(data);
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

    const acceptRequest = async (request) => {
        MySwal.fire({
            title: 'Accept Reservation Request?',
            text: `Room ${request.id.roomId} will be reserved on ${request.id.date} 
            from ${request.timeSlots[0].startTime}:00 to ${request.timeSlots[request.timeSlots.length-1].endTime}:00
            for employee #${request.id.employeeId}`,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Accept'
        }).then(async (result) => {
            if (result.isConfirmed) {
                const reservation = {
                    id: {
                        date: request.id.date,
                        employee: {id: request.id.employeeId},
                        room: {id: request.id.roomId}
                    },
                    purpose: request.purpose,
                    timeSlots: request.timeSlots
                }
                const postRequest = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json'},
                    body: JSON.stringify(reservation)
                };
                const postResponse = await fetch(`http://localhost:8080/reservations`, postRequest);
                if (postResponse.status === 200) {
                    const postData = await postResponse.json();
        
                    const updatedRequest = {...request};
                    updatedRequest.id = {
                        date: request.id.date,
                        employee: {id: request.id.employeeId},
                        room: {id: request.id.roomId}
                    }
                    updatedRequest.status = "approved";
                    updatedRequest.admin = {id: sessionStorage.getItem('userId')};
                    const putRequest = {
                        method: 'put',
                        headers: { 'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': 'http://localhost:3000'},
                        body: JSON.stringify(updatedRequest)
                    };
                    const putResponse = await fetch(`http://localhost:8080/requests/${updatedRequest.id.date}-${updatedRequest.id.employee.id}-${updatedRequest.id.room.id}`, putRequest);
                    if (putResponse.status === 200) {
                        const putData = await putResponse.json();
                        
                        const reviewedReq = [...reviewedRequests];
                        reviewedReq.push(putData);
                        setReviewedRequests(reviewedReq);
        
                        let pendingReq = [...pendingRequests];
                        const index = pendingRequests.findIndex((item) => 
                            item.id === request.id
                        )
                        pendingReq.splice(index, 1);
                        setPendingRequests(pendingReq);
                    }
                }
                MySwal.fire(
                    'Accepted!',
                    'The Reservation has been accepted',
                    'success'
                )
            }
        })   
    }

    const rejectRequest = async (request) => {
        const {value: deniedReason} = await MySwal.fire({
            title: 'Reject Reservation Request?',
            text: `Please provide a reason for rejecting the request:`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Reject',
            input: 'textarea',
            inputPlaceholder: 'Rejection reason',
            inputAttributes: {
                autocapitalize: 'off'
            },
            inputValidator: (inputValue) => {
                return new Promise((resolve) => {
                    if (!inputValue) {
                        resolve('You need to write a reason')
                    } else {
                        resolve();
                    }
                })
            }
        })
        if (deniedReason) {
            console.log(deniedReason);
            const updatedRequest = {...request};
            updatedRequest.id = {
                date: request.id.date,
                employee: {id: request.id.employeeId},
                room: {id: request.id.roomId}
            }
            updatedRequest.status = "denied";
            updatedRequest.admin = {id: sessionStorage.getItem('userId')};
            updatedRequest.deniedReason = deniedReason;
            const putRequest = {
                method: 'put',
                headers: { 'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': 'http://localhost:3000'},
                body: JSON.stringify(updatedRequest)
            };
            const putResponse = await fetch(`http://localhost:8080/requests/${updatedRequest.id.date}-${updatedRequest.id.employee.id}-${updatedRequest.id.room.id}`, putRequest);
            if (putResponse.status === 200) {
                const putData = await putResponse.json();
                
                const reviewedReq = [...reviewedRequests];
                reviewedReq.push(putData);
                setReviewedRequests(reviewedReq);

                let pendingReq = [...pendingRequests];
                const index = pendingRequests.findIndex((item) => 
                    item.id === request.id
                )
                pendingReq.splice(index, 1);
                setPendingRequests(pendingReq);
            }
            MySwal.fire(
                'Rejected!',
                'The Reservation has been rejected',
                'error'
            )
        }
    }

    const cancelReservation = async (res) => {
        MySwal.fire({
            title: 'Cancel Reservation?',
            text: `Room ${res.id.roomId} will be freed on ${res.id.date} 
            from ${res.timeSlots[0].startTime}:00 to ${res.timeSlots[res.timeSlots.length-1].endTime}:00
            and employee #${res.id.employeeId} will be notified`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#cc0404',
            cancelButtonColor: 'lightgray',
            confirmButtonText: 'OK'
        }).then(async (result) => {
            if (result.isConfirmed) {
                
                // const reservation = {
                //     id: {
                //         date: request.id.date,
                //         employee: {id: request.id.employeeId},
                //         room: {id: request.id.roomId}
                //     },
                //     purpose: request.purpose,
                //     timeSlots: request.timeSlots
                // }
                // const postRequest = {
                //     method: 'POST',
                //     headers: { 'Content-Type': 'application/json'},
                //     body: JSON.stringify(reservation)
                // };
                // const postResponse = await fetch(`http://localhost:8080/reservations`, postRequest);
                // if (postResponse.status === 200) {
                //     const postData = await postResponse.json();
        
                //     const updatedRequest = {...request};
                //     updatedRequest.id = {
                //         date: request.id.date,
                //         employee: {id: request.id.employeeId},
                //         room: {id: request.id.roomId}
                //     }
                //     updatedRequest.status = "approved";
                //     updatedRequest.admin = {id: sessionStorage.getItem('userId')};
                //     const putRequest = {
                //         method: 'put',
                //         headers: { 'Content-Type': 'application/json',
                //         'Access-Control-Allow-Origin': 'http://localhost:3000'},
                //         body: JSON.stringify(updatedRequest)
                //     };
                //     const putResponse = await fetch(`http://localhost:8080/requests/${updatedRequest.id.date}-${updatedRequest.id.employee.id}-${updatedRequest.id.room.id}`, putRequest);
                //     if (putResponse.status === 200) {
                //         const putData = await putResponse.json();
                        
                //         const reviewedReq = [...reviewedRequests];
                //         reviewedReq.push(putData);
                //         setReviewedRequests(reviewedReq);
        
                //         let pendingReq = [...pendingRequests];
                //         const index = pendingRequests.findIndex((item) => 
                //             item.id === request.id
                //         )
                //         pendingReq.splice(index, 1);
                //         setPendingRequests(pendingReq);
                //     }
                // }
                MySwal.fire(
                    'Canceled!',
                    'The Reservation has been canceled',
                    'success'
                )
            }
        })   
    }
 

    return (
        <div className="homepage">
        <Tab.Container activeKey={activeTab} onSelect={handleSelectTab}>
            <Nav variant="tabs" className="mb-4">
                <Nav.Item>
                <Nav.Link eventKey="pending">Pending Requests</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                <Nav.Link eventKey="history" onClick={() => console.log(reviewedRequests)}>Request History</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                <Nav.Link eventKey="cancel"><span id="cancel-tab">Cancel Reservation</span></Nav.Link>
                </Nav.Item>
            </Nav>
            <Tab.Content>
                <Tab.Pane eventKey="pending">
                <h2>Pending Requests</h2>
                {
                    <Table bordered hover className="admin-table">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Employee ID</th>
                                <th>Room ID</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Purpose</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {pendingRequests.map((request) => (
                                <tr key={request.id.date+'-'+request.id.roomId+'-'+request.id.employeeId}>
                                    <td className="admin-td">{request.id.date}</td>
                                    <td className="admin-td td-num">{request.id.employeeId}</td>
                                    <td className="admin-td td-num">{request.id.roomId}</td>
                                    <td className="admin-td td-num">{request.timeSlots[0].startTime+':00'}</td>
                                    <td className="admin-td td-num">{request.timeSlots[request.timeSlots.length-1].endTime+':00'}</td>
                                    <td className="admin-td">{request.purpose}</td>
                                    <td className="admin-td action-btn-wrap">
                                        <button className="btn action-btn" onClick={async () => await acceptRequest(request)}>
                                            <i className="bi bi-check-circle" size='fa-2x'/>
                                        </button>
                                        <button className="btn action-btn" onClick={async () => await rejectRequest(request)}>
                                            <i className="bi bi-x-circle"/>
                                        </button>
                                    </td>
                                
                                </tr>
                            ))

                            }
                        </tbody>
                    </Table>
                }
                </Tab.Pane>
                <Tab.Pane eventKey="history">
                <h2>History</h2>
                {
                    <Table bordered hover className="admin-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Employee ID</th>
                            <th>Room ID</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Purpose</th>
                            <th>Admin ID</th>
                            <th>Outcome</th>
                        </tr>
                    </thead>
                    <tbody>
                        {reviewedRequests.map((req) => (
                            <tr key={req.id.date+'-'+req.id.roomId+'-'+req.id.employeeId}>
                                <td className="admin-td">{req.id.date}</td>
                                <td className="admin-td td-num">{req.id.employeeId}</td>
                                <td className="admin-td td-num">{req.id.roomId}</td>
                                <td className="admin-td td-num">{req.timeSlots[0].startTime+':00'}</td>
                                <td className="admin-td td-num">{req.timeSlots[req.timeSlots.length-1].endTime+':00'}</td>
                                <td className="admin-td">{req.purpose}</td>
                                <td className="admin-td">{req.adminId}</td>
                                <td className="admin-td">{req.status}</td>
                            
                            </tr>
                        ))

                        }
                    </tbody>
                </Table>
                }
                </Tab.Pane>
                <Tab.Pane eventKey="cancel">
                <h2>Cancel Reservation</h2>
                {
                    <Table bordered hover className="admin-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Employee ID</th>
                            <th>Room ID</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Purpose</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {reservations.map((res) => (
                            <tr key={res.id.date+'-'+res.id.roomId+'-'+res.id.employeeId}>
                                <td className="admin-td">{res.id.date}</td>
                                <td className="admin-td td-num">{res.id.employeeId}</td>
                                <td className="admin-td td-num">{res.id.roomId}</td>
                                <td className="admin-td td-num">{res.timeSlots[0].startTime+':00'}</td>
                                <td className="admin-td td-num">{res.timeSlots[res.timeSlots.length-1].endTime+':00'}</td>
                                <td className="admin-td">{res.purpose}</td>
                                <td className="admin-td action-btn-wrap">
                                        <button className="btn action-btn" onClick={async () => await cancelReservation(res)}>
                                            <i className="bi bi-x-circle"/>
                                        </button>
                                    </td>
                            </tr>
                        ))

                        }
                    </tbody>
                </Table>
                }
                </Tab.Pane>
            </Tab.Content>
        </Tab.Container>
        </div>
    )
}