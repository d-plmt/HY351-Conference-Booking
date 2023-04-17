import { useNavigate } from "react-router-dom";
import { Calendar, DateLocalizer, momentLocalizer} from 'react-big-calendar'
import DatePicker from "react-datepicker";
import { useEffect, useState } from 'react';
import moment from 'moment'
import ReactDataGrid from 'react-data-grid';
import "react-big-calendar/lib/css/react-big-calendar.css";
import './styles/NewReservation.css';
import "react-datepicker/dist/react-datepicker.css";
export const NewReservation = () => {
    const navigate = useNavigate();
    const localizer = momentLocalizer(moment);
    const [rooms, setRooms] = useState([]);
    const [activeRequests, setActiveRequests] = useState([]);
    const [selectedDate, setSelectedDate] = useState("");
    const [roomSize, setRoomSize] = useState(null);
    const [accessibility, setAccessibility] = useState(false);
    const [purpose, setPurpose] = useState("");
    const [showAvailableRooms, setShowAvailableRooms] = useState(false);
    const [timeSlots, setTimeSlots] = useState([]);

    useEffect(() => {
        const fetchRooms = async () => {
            try {
                const response = await fetch('http://localhost:8080/rooms');
                const data = await response.json();
                for (let i = 0; i < data.length; i++) {
                    let roomId = i+1;
                    data[i].name = "Room "+roomId;
                    data[i].key = roomId;
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
                let slots = [];
                for (let slot of data) {
                    slots.push({
                        id: slot.id,
                        title: `${slot.startTime} - ${slot.endTime}`,
                        count: 7
                    })
                }
                setTimeSlots(slots);
            }
            catch (error) {
                console.error(error)
            }
        };

        if (timeSlots.length === 0) {
            fetchTimeSlots();      
        }
        console.log(timeSlots);

    }, [timeSlots]);

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                const response = await fetch('http://localhost:8080/requests/active');
                const data = await response.json();
                // let events = [];
                // for (let i = 0; i< data.length; i++) {
                //     let startTime = data[i].timeSlots[0].startTime;
                //     if (startTime.toString().length === 1) startTime = "0" + startTime.toString();
                //     let endTime = data[i].timeSlots[data[i].timeSlots.length-1].endTime;
                //     if (endTime.toString().length === 1) endTime = "0" + endTime.toString();
                //     events.push({
                //         id: i,
                //         title: data[i].purpose,
                //         allDay: false,
                //         resource: data[i].id.roomId,
                //         employee: data[i].id.employeeId,
                //         status: data[i].status,
                //         start: new Date(`${data[i].id.date}T${startTime}:00:00`),
                //         end: new Date(`${data[i].id.date}T${endTime}:00:00`)
                //     });
                // }
                setActiveRequests(data);
            }
            catch (error) {
                console.error(error);
            }
        };


        if (activeRequests.length === 0) {
            fetchRequests();
        }
        console.log(activeRequests);
    }, [activeRequests])

    const handleDateSelect = (selected) => {
        setSelectedDate(selected);
    }

    const isWeekday = (date) => {
        const day = date.getDay();
        return day !== 0 && day !== 6;
    };

    const searchAvailability = (e) => {
        e.preventDefault();
        setShowAvailableRooms(true);
        console.log(rooms);
        console.log(timeSlots);
    }

    return (
        <div className="homepage">
            New Reservation
            <button onClick={() => navigate(-1)}>Go back</button>
            <div className="steps">
                <div className="date-picker step" id="step1">
                    <div className="date-text">Select reservation day</div>
                    <DatePicker
                        dateFormat="yyyy/MM/dd"
                        showIcon
                        isClearable
                        startDate={new Date("2023-06-01")}
                        minDate={new Date("2023-06-01")}
                        selected={selectedDate}
                        onChange={(date) => handleDateSelect(date)}
                        withPortal
                        filterDate={isWeekday}
                    />
                </div>
                {!!selectedDate && (<div className="step" id="step2">
                    Select options
                    <form className="reservation-options" onSubmit={searchAvailability}>
                        <div>
                            <label htmlFor="room-size" className="label size-label">Room Size</label>
                            <input type="number" className="room-size" onChange={e => setRoomSize(e.target.value)}/>
                        </div>
                            <input type="checkbox" className="accessibility" onClick={e => setAccessibility(e.target.checked)}/>
                            <label htmlFor="accessibility" className="label accessibility-label">Wheelchair accessible</label>
                        <div>
                            <label htmlFor="purpose" className="label purpose-label">Purpose</label>
                            <input type="text" className="purpose purpose-input" onChange={e => setPurpose(e.target.value)}/>
                        </div>
                        <button className="btn search-btn">Search Availability</button>
                    </form>
                </div>)}
                {showAvailableRooms && (<div className="step" id="step3">
                    Select Room and Timeslots
                    
                    <div className="container">
                        <div className="row">
                            <div className="col-2"></div>
                            {rooms.map((room) => (
                            <div className="col" key={room.name}>{room.name}</div>
                            ))}
                        </div>
                        {timeSlots.map((time) => (
                            <div className="row" key={time}>
                            <div className="col-2">{time}</div>
                            {rooms.map((room) => (
                                <div className="col" key={`${room.name}-${time}`}></div>
                            ))}
                            </div>
                        ))}
                        </div>
                    </div>)}
            </div>

            {/* <Calendar
                localizer={localizer}
                startAccessor="start"
                endAccessor="end"
                defaultView="day"
                views={{day: true, work_week: true}}
                style={{ height: 500 }}
                resources={rooms}
                min={new Date(2023, 6, 1, 8, 0, 0, 0)}
                max={new Date(2023, 6, 30, 21, 0, 0, 0)}
                events={activeRequests}
            /> */}
        </div>
    )
}