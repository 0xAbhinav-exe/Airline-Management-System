# Airline Management System

The **Airline Management System** is a desktop-based application developed to streamline airline operations. It automates core functionalities such as flight scheduling, ticket booking, and customer management, reducing manual errors and enhancing overall efficiency for both users and administrators.

---

## 🚀 Project Overview

This GUI-based application is built using Java Swing and MySQL. It allows users to:

* Add and manage passenger records during ticket reservation.
* Retrieve and update customer and flight details.
* View flight schedules and passenger manifests.
* Process and track air travel transactions.

The backend utilizes a MySQL database to store and retrieve data, supporting scalable operations for large numbers of flights and passengers.

---

## ✈️ System Components

The application is structured around the following core entities:

* **Airlines**
* **Airline Employees**
* **Customers/Travellers**

Each entity maps to corresponding tables in the MySQL database.

---

## 🔧 Key Features

| Class Name            | Description                                                               |
| --------------------- | ------------------------------------------------------------------------- |
| `AddCustomer.java`    | Handles customer registration and adds passenger details to the database. |
| `BoardingPass.java`   | Generates boarding passes based on booking information.                   |
| `BookFlight.java`     | Facilitates flight reservations and stores relevant data.                 |
| `CancelFlight.java`   | Manages flight cancellations and updates records.                         |
| `FlightInfo.java`     | Displays flight schedules, availability, and other flight data.           |
| `Home.java`           | Acts as the main dashboard for navigating the application.                |
| `JourneyDetails.java` | Shows journey-specific information such as source, destination, and date. |
| `Login.java`          | Provides secure access to the system via login credentials.               |

---

## 🛠️ Technologies Used

* **Programming Language:** Java (Core Java)
* **GUI Framework:** Swing
* **Database:** MySQL
* **IDE:** IntelliJ

---

## 💻 System Requirements

* **Java JDK:** Version 8 or higher
* **IntelliJ:** latest version
* **MySQL Server:** Version 8.0 or higher

---

## ⚙️ Installation & Setup

1. **Clone the Repository:**

   ```bash
   git clone https://gh repo clone 0xAbhinav-exe/Airline-Management-System
   ```

2. **Open the Project:**

   Launch NetBeans IDE and open the cloned project.

3. **Configure the Database:**

   * Import the provided SQL script into your MySQL server.
   * Update database connection details (hostname, username, password) in the Java files.

4. **Build & Run the Application:**

   Use NetBeans to build the project and run `Home.java`.

---

## 🧭 How to Use

1. **Start the application** by running `Home.java`.
2. **Login** using the credentials via the `Login.java` interface.
3. **Use the dashboard** to access various features:

   * Add customer details
   * Book or cancel flights
   * Generate boarding passes
   * View flight and journey information

All operations interact with the MySQL database in real-time for dynamic data handling.

---

## 🗃️ Database Schema

The database consists of several tables, including:

* Airlines
* Airline Employees
* Customers/Travellers
* Flight Information
* Ticket Bookings

Make sure your database matches the provided SQL schema to ensure proper functionality.

---

## 🚧 Future Enhancements

* Real-time flight status integration
* Payment gateway support
* Advanced search and filtering for flights

---
