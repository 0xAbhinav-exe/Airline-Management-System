CREATE DATABASE IF NOT EXISTS airlinemanagementsystem1;

USE airlinemanagementsystem1;

CREATE TABLE IF NOT EXISTS login (
    username VARCHAR(20) PRIMARY KEY,
    password VARCHAR(20) NOT NULL
);

INSERT INTO login VALUES('admin', 'admin');

CREATE TABLE IF NOT EXISTS passenger (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    nationality VARCHAR(30) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(100) NOT NULL,
    aadhar VARCHAR(20) UNIQUE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    date_of_birth DATE,
    passport_number VARCHAR(20) UNIQUE,
    emergency_contact VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS flight (
    f_code VARCHAR(20) PRIMARY KEY,
    f_name VARCHAR(20) NOT NULL,
    source VARCHAR(40) NOT NULL,
    destination VARCHAR(40) NOT NULL,
    capacity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS flight_schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_code VARCHAR(20),
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    available_seats INT NOT NULL,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    FOREIGN KEY (flight_code) REFERENCES flight(f_code)
);

INSERT INTO flight VALUES
("1001", "AI-1212", "Delhi", "Mumbai", 180, 5000.00, "ACTIVE"),
("1002", "AI-1453", "Delhi", "Goa", 180, 6000.00, "ACTIVE"),
("1003", "AI-1112", "Mumbai", "Chennai", 180, 4500.00, "ACTIVE"),
("1004", "AI-3222", "Delhi", "Amritsar", 180, 4000.00, "ACTIVE"),
("1005", "AI-1212", "Delhi", "Ayodhya", 180, 3500.00, "ACTIVE"),
("1006", "AI-2345", "Mumbai", "Bangalore", 180, 5500.00, "ACTIVE"),
("1007", "AI-3456", "Delhi", "Kolkata", 180, 4500.00, "ACTIVE"),
("1008", "AI-4567", "Chennai", "Hyderabad", 180, 4000.00, "ACTIVE"),
("1009", "AI-5678", "Bangalore", "Kochi", 180, 6500.00, "ACTIVE"),
("1010", "AI-6789", "Mumbai", "Pune", 180, 3000.00, "ACTIVE");

CREATE TABLE IF NOT EXISTS reservation (
    PNR VARCHAR(15) PRIMARY KEY,
    TICKET VARCHAR(20) UNIQUE NOT NULL,
    passenger_id INT,
    flight_code VARCHAR(20),
    schedule_id INT,
    booking_date DATE NOT NULL,
    travel_date DATE NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    flight_price DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    payment_method VARCHAR(20),
    payment_date DATETIME,
    booking_class VARCHAR(10) DEFAULT 'ECONOMY',
    special_requests TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (passenger_id) REFERENCES passenger(id),
    FOREIGN KEY (flight_code) REFERENCES flight(f_code),
    FOREIGN KEY (schedule_id) REFERENCES flight_schedule(schedule_id)
);

CREATE TABLE IF NOT EXISTS cancel (
    cancel_id INT AUTO_INCREMENT PRIMARY KEY,
    pnr VARCHAR(15),
    cancel_date DATE NOT NULL,
    refund_amount DECIMAL(10,2),
    reason VARCHAR(100),
    cancellation_charges DECIMAL(10,2),
    refund_status VARCHAR(20) DEFAULT 'PENDING',
    refund_date DATETIME,
    processed_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pnr) REFERENCES reservation(PNR)
);

CREATE INDEX idx_flight_route ON flight(source, destination);
CREATE INDEX idx_reservation_dates ON reservation(booking_date, travel_date);
CREATE INDEX idx_passenger_aadhar ON passenger(aadhar);

show tables;