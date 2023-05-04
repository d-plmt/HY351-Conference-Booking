import { useNavigate } from "react-router-dom";
import DatePicker from "react-datepicker";
import { useEffect, useState } from 'react';
import { Table } from "react-bootstrap";
import moment from 'moment'
import './styles/NewReservation.css';
import "react-datepicker/dist/react-datepicker.css";
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'

export const NewReservation = () => {

    const MySwal = withReactContent(Swal)

    const navigate = useNavigate();

    const [rooms, setRooms] = useState([]);
    const [activeRequests, setActiveRequests] = useState([]);
    const [timeSlots, setTimeSlots] = useState([]);

    const [showAvailableRooms, setShowAvailableRooms] = useState(false);
    const [availableRooms, setAvailableRooms] = useState([]);
    
    const [selectedDate, setSelectedDate] = useState("");
    const [selectedTimeSlots, setSelectedTimeSlots] = useState({});
    const [roomSize, setRoomSize] = useState(null);
    const [accessibility, setAccessibility] = useState(false);
    const [purpose, setPurpose] = useState("");
    const [selectedRoom, setSelectedRoom] = useState();

    useEffect(() => {
        const fetchRooms = async () => {
            try {
                const response = await fetch('http://localhost:8080/rooms');
                const data = await response.json();
                for (let i = 0; i < data.length; i++) {
                    let roomId = i+1;
                    data[i].name = "Room "+roomId;
                }
                setRooms(data);
            }
            catch (error) {
                console.error(error)
            }
        };

        if (rooms.length === 0) {
            fetchRooms();    
        }

    }, [rooms]);

    useEffect(() => {
        const fetchTimeSlots = async () => {
            try {
                const response = await fetch('http://localhost:8080/timeslots');
                const data = await response.json();
                setTimeSlots(data);
            }
            catch (error) {
                console.error(error)
            }
        };

        if (timeSlots.length === 0) {
            fetchTimeSlots();      
        }

    }, [timeSlots]);

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                const response = await fetch('http://localhost:8080/requests/active');
                const data = await response.json();
                setActiveRequests(data);
            }
            catch (error) {
                console.error(error);
            }
        };


        if (activeRequests.length === 0) {
            fetchRequests();
        }
    }, [activeRequests])

    const isWeekday = (date) => {
        const day = date.getDay();
        return day !== 0 && day !== 6;
    };

    const handleDuration = (duration) => {
        let lastSlotEnd;
        if (duration < 60) {
            lastSlotEnd = selectedDate.getHours()+1;
        }
        else {
            lastSlotEnd = selectedDate.getHours() + Math.ceil(duration/60);
        }
        const slots = {
            startTimeSlot: timeSlots.find((timeSlot) => timeSlot.startTime === selectedDate.getHours()),
            endTimeSlot: timeSlots.find((timeSlot) => timeSlot.endTime === (lastSlotEnd))
        };
        setSelectedTimeSlots(slots);
    }

    const findValidRooms = () => {
        let validRooms = rooms.filter((room) => 
            room.capacity >= roomSize &&
            (accessibility? room.accessibility : true)
        );
        const unavailableRooms = new Set();
        activeRequests.forEach((request) => {
            if (request.id.date === moment(selectedDate).format('YYYY-MM-DD') && validRooms.some((room => room.id === request.id.roomId))) {
                if (request.timeSlots.some((timeSlot) => (
                    ((timeSlot.id === selectedTimeSlots.startTimeSlot.id) || (timeSlot.id === selectedTimeSlots.endTimeSlot.id))
                ))) {
                    unavailableRooms.add(request.id.roomId);
                }
            }
        });
        validRooms = validRooms.filter((room) => !unavailableRooms.has(room.id));
        return validRooms;
    }

    const searchAvailability = () => {
        console.log("test");
        setShowAvailableRooms(true);
        const validRooms = findValidRooms();
        setAvailableRooms(validRooms);
    }

    const sendRequest = async () => {
        if (purpose === "") {
            MySwal.fire({
                title: 'Reservation purpose',
                text: 'You need to provide a purpose for your reservation request',
                icon: 'error',
                confirmButtonText: 'OK',
                showCancelButton: false
            });
            return;
        }
        MySwal.fire({
            title: 'Make New Reservation Request?',
            text: `Room ${selectedRoom.id} will be reserved on ${selectedDate} from ${timeSlots[0].startTime}:00 to ${timeSlots[timeSlots.length-1].endTime}:00`,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Accept' 
        }).then (async (result) => {
            if (result.isConfirmed) {
                const request = {
                    id: {
                        date: moment(selectedDate).format("YYYY-MM-DD"),
                        employee: {id: sessionStorage.getItem("userId")},
                        room: {id: selectedRoom.id.toString()}
                    },
                    purpose: purpose,
                    timeSlots: timeSlots,
                    status: "pending",
                    admin: null
                };
        
                const postRequest = {
                    method: 'post',
                    headers: {'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': 'http://localhost:3000'},
                    body: JSON.stringify(request)
                };
                const postResponse = await fetch(`http://localhost:8080/requests`, postRequest);
                if (postResponse.status === 200) {
                    const response = await fetch(`http://localhost:8080/requests`);
                    const responseData = await response.json();
                    setActiveRequests(await responseData);
                    const validRooms = findValidRooms();
                    setAvailableRooms(validRooms);

                    MySwal.fire(
                        'Done!',
                        'The Reservation Request has been sent',
                        'success'
                    )
                }
                else {
                    MySwal.fire(
                        'Rejected!',
                        'The Reservation Request could not be sent',
                        'error'
                    )
                }

            }
        })
        
    }

    return (
        <div>
            <div className="page-title">Submit a New Reservation Request</div>
            <div className="steps">
                <div className="date-picker step" id="step1">
                    <div className="step-text">
                        <div className="step-no">1.</div>
                        <div className="date-text"> Select reservation day and time</div>
                    </div>
                    <div className="step-content">
                        <DatePicker
                            className="calendar"
                            dateFormat="yyyy-MM-dd HH:mm aa"
                            showIcon
                            isClearable
                            startDate={new Date("2023-06-01")}
                            minDate={new Date("2023-06-01")}
                            selected={selectedDate}
                            onChange={(date) => setSelectedDate(date)}
                            withPortal
                            filterDate={isWeekday}
                            showTimeSelect
                            timeIntervals={60}
                            minTime={new Date(20223, 1, 1, 8, 0, 0, 0)}
                            maxTime={new Date(20223, 1, 1, 20, 0, 0, 0)}
                        />
                    </div>
                </div>
                {!!selectedDate && (
                <div className="step" id="step2">
                    <div className="step-text" id="step-text-2">
                        <div className="step-no">2.</div>
                        <div>Select options</div>
                    </div>
                    <div className="reservation-options">
                        <div className="step-content">
                            <label htmlFor="duration" className="label duration-label">Reservation Duration (in mins):</label>
                            <input type="number" className="num-input" placeholder="0" onBlur={e => handleDuration(e.target.value)}/>
                        </div>
                        <div className="step-content">
                            <label htmlFor="room-size" className="label size-label">Required Capacity:</label>
                            <input type="number" className="num-input" placeholder="0" onChange={e => setRoomSize(e.target.value)}/>
                        </div>
                        <div className="step-content">
                            <label htmlFor="accessibility" className="label accessibility-label">Wheelchair accessible:</label>
                            <input type="checkbox" className="check-input" onClick={e => setAccessibility(e.target.checked)}/>

                        </div>
                        <div className="step-content">
                            <label htmlFor="purpose" className="label purpose-label">Purpose</label>
                            <textarea type="text" className="purpose purpose-input" onChange={e => setPurpose(e.target.value)}/>
                        </div>
                        <button className="btn search-btn btn-ordinary"><span className="btn-text" onClick={() => searchAvailability()}>Search Availability</span></button>
                    </div>
                </div>)}
                {showAvailableRooms && (
                    !availableRooms.length ? 
                    (<div className="results"><div className="no-rooms step">No rooms available</div></div>) :
                    (<div className="results"><div className="rooms step" id="step3">
                        <div className="step-text">
                        <div className="step-no">3.</div>
                        <div id="select-room">Select an available Room from the list:</div>
                    </div>
                        {
                            <Table bordered hover style={{borderColor: 'rgb(198, 218, 255)'}}>
                            <thead>
                              <tr>
                                <th>Name</th>
                                <th>Capacity</th>
                                <th>Accessibility</th>
                                <th>Floor</th>
                              </tr>
                            </thead>
                            <tbody>
                              {availableRooms.map((room) => (
                                <tr key={room.id}
                                    onClick={() => setSelectedRoom(room)}
                                    className ={selectedRoom && selectedRoom.id === room.id ? "selected-row" : ""}
                                >
                                  <td>{room.name}</td>
                                  <td>{room.capacity}</td>
                                  <td>{room.accessibility ? "Yes" : "No"}</td>
                                  <td>{room.floor}</td>
                                </tr>
                              ))}
                            </tbody>
                          </Table>
                        }
                        {!!selectedRoom && (<button className="btn btn-ordinary send-req-btn" onClick={() => sendRequest()}><span className="btn-text">Make Reservation</span></button>)}
                    </div></div>))}
            </div>
        </div>
    )
}