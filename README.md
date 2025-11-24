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


### Step 1: Ensure MySQL Database is Set Up

Your application relies on a MySQL database. You need to make sure the database is running and the schema is imported.

1.  **Start your MySQL Server**: Ensure your MySQL database server (e.g., MySQL Community Server, XAMPP, WAMP) is running.
2.  **Create the Database and Import Schema**:
    *   Open a MySQL client (like MySQL Workbench, phpMyAdmin, or the MySQL command-line client).
    *   Execute the SQL script provided in your project: `AirlineManagementSystem [SQL].sql`. This script will:
        *   Create the `airlinemanagementsystem1` database if it doesn't exist.
        *   Use that database.
        *   Create the `login`, `passenger`, `flight`, `flight_schedule`, `reservation`, and `cancel` tables.
        *   Insert initial data into the `login` and `flight` tables.

    A quick way to do this from the command line (if MySQL is in your PATH and you have `root` access without a password, or adjust as needed):
    ```bash
    mysql -u root -p < "C:\Users\adams\OneDrive\Documents\Project2\AirlineManagementSystem [SQL].sql"
    ```
    (You'll be prompted for your MySQL root password).

### Step 2: Verify MySQL JDBC Driver

Your Java application needs the MySQL Connector/J JDBC driver to communicate with the MySQL database.

1.  **Check `lib` directory**: Ensure that the `mysql-connector-java-8.0.28.jar` file (or a similar version) is located in your project's `lib` directory.
    *   You should have a folder structure like: `Project2/lib/mysql-connector-java-8.0.28.jar`
    *   If it's missing, you'll need to download it from the official MySQL website and place it in the `lib` folder.

### Step 3: Open Command Prompt/Terminal

You'll need to use the command line to compile and run your Java application.

1.  **Open Command Prompt (Windows)**: Search for "cmd" in the Start menu and open it.
2.  **Navigate to your Project Directory**: Use the `cd` command to change your current directory to your project's root folder.
    ```bash
    cd C:\Users\adams\OneDrive\Documents\Project2
    ```
    (Make sure you are in the directory that contains all your `.java` files and the `lib` folder).

### Step 4: Compile the Java Source Code

Now you will compile all the `.java` files into `.class` files. It's crucial to include the JDBC driver in the classpath.

1.  **Execute the Compile Command**:
    ```bash
    javac -cp ".;lib/*" *.java
    ```
    *   `javac`: The Java compiler command.
    *   `-cp ".;lib/*"`: This is the classpath argument.
        *   `.`: Tells the compiler to look for `.java` files and `.class` files in the current directory.
        *   `lib/*`: Tells the compiler to include all JAR files (`.jar`) found directly inside the `lib` directory. This is how your program finds the MySQL JDBC driver.
    *   `*.java`: Tells the compiler to compile all files ending with `.java` in the current directory.

2.  **Check for Compilation Errors**: If there are any syntax errors in your Java code, the compiler will display them. You'll need to fix these errors before proceeding. If compilation is successful, you will see no output.

### Step 5: Run the Program

Once compiled, you can run the application. Again, the JDBC driver needs to be in the classpath during runtime.

1.  **Execute the Run Command**: Your main class (the one with the `main` method that starts the application) is `Loginpage`.
    ```bash
    java -cp ".;lib/*" Loginpage
    ```
    *   `java`: The Java Virtual Machine (JVM) command to run a Java application.
    *   `-cp ".;lib/*"`: The classpath for runtime, identical to the compile command. This ensures the JVM can find all your application's classes and the necessary JDBC driver.
    *   `Loginpage`: The fully qualified name of the class containing the `main` method you want to execute (do not include `.java` or `.class`).

2.  **Interact with the Application**: The `Loginpage` window should appear. You can now interact with your Airline Management System.
    *   The default login credentials are `username: admin`, `password: admin` (as set in `AirlineManagementSystem [SQL].sql`).

### Troubleshooting Common Issues:

*   **"Database driver not found" or `ClassNotFoundException`**:
    *   Double-check that `mysql-connector-java-8.0.28.jar` is indeed in your `lib` directory.
    *   Ensure your `javac` and `java` commands precisely use `lib/*` for the classpath.
*   **`SQLException: Could not connect to database`**:
    *   Verify your MySQL server is running.
    *   Check the database name, username (`root`), and password (`akiraded`) in `Conn.java` and ensure they match your MySQL server configuration.
    *   Make sure the database `airlinemanagementsystem1` has been created and the schema imported (refer to Step 1).
*   **`java.lang.NullPointerException` related to `Conn.s`**: This likely means the database connection failed, and the `s` (Statement) object was not initialized. Check the `SQLException` details preceding the NullPointerException in your console output.

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
