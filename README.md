# Hotel Booking System

A comprehensive Java Swing-based desktop application for managing hotel room bookings with MySQL database integration.

## 📋 Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

- **Room Management**: View and manage different types of hotel rooms with pricing
- **Real-time Booking**: Book available rooms with customer details
- **Checkout System**: Process guest checkouts and update room availability
- **Database Integration**: Persistent data storage using MySQL
- **User-friendly GUI**: Intuitive Swing-based interface with tabbed navigation
- **Room Status Tracking**: Real-time availability status (Available/Booked)
- **Customer Information**: Store guest details including name and contact information
- **Date Management**: Handle check-in and check-out dates
- **Cost Calculation**: Automatic total cost computation based on room rates

## 🛠 Technologies Used

- **Language**: Java (Swing GUI)
- **Database**: MySQL
- **JDBC**: MySQL Connector/J
- **Architecture**: DAO (Data Access Object) Pattern
- **Build Tool**: Standard Java compilation
- **GUI Framework**: Java Swing

## 📋 Prerequisites

Before running the application, ensure you have:

- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- MySQL JDBC Driver (mysql-connector-java)
- IDE (IntelliJ IDEA, Eclipse, or similar) - optional but recommended

## 🗄 Database Setup

1. **Create Database**:
   ```sql
   CREATE DATABASE hotel_booking;
   USE hotel_booking;
   ```

2. **Create Tables**:
   ```sql
   -- Rooms table
   CREATE TABLE rooms (
       id INT AUTO_INCREMENT PRIMARY KEY,
       room_number INT NOT NULL UNIQUE,
       type VARCHAR(50) NOT NULL,
       price DECIMAL(10,2) NOT NULL,
       status VARCHAR(20) DEFAULT 'Available'
   );

   -- Bookings table
   CREATE TABLE bookings (
       id INT AUTO_INCREMENT PRIMARY KEY,
       room_id INT,
       customer_name VARCHAR(100) NOT NULL,
       contact VARCHAR(20) NOT NULL,
       check_in_date DATE NOT NULL,
       check_out_date DATE NOT NULL,
       total_cost DECIMAL(10,2) NOT NULL,
       FOREIGN KEY (room_id) REFERENCES rooms(id)
   );
   ```

3. **Insert Sample Data**:
   ```sql
   INSERT INTO rooms (room_number, type, price, status) VALUES
   (101, 'Single', 1500.00, 'Available'),
   (102, 'Double', 2500.00, 'Available'),
   (103, 'Suite', 4000.00, 'Available'),
   (104, 'Single', 1500.00, 'Available'),
   (105, 'Double', 2500.00, 'Available');
   ```

## 🚀 Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/abhishekgupta028/Hotel_Booking_System.git
   cd Hotel_Booking_System1
   ```

2. **Configure Database Connection**:
   Update the database credentials in `src/dao/DBConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/hotel_booking";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

3. **Add MySQL JDBC Driver**:
   - Download MySQL Connector/J from [MySQL Official Site](https://dev.mysql.com/downloads/connector/j/)
   - Add the JAR file to your project classpath

4. **Compile the Project**:
   ```bash
   javac -cp ".:mysql-connector-java-8.0.33.jar" src/**/*.java
   ```

5. **Run the Application**:
   ```bash
   java -cp ".:mysql-connector-java-8.0.33.jar:src" Main
   ```

## 💻 Usage

### Starting the Application
1. Launch the application using the command above
2. The main window will open with two tabs: "Booking" and "Checkout"

### Making a Booking
1. Navigate to the **Booking** tab
2. Select an available room from the dropdown
3. Enter customer details:
   - Customer name
   - Contact information
   - Check-in date (YYYY-MM-DD format)
   - Check-out date (YYYY-MM-DD format)
4. Click "Book Room" to confirm the reservation
5. The room status will automatically update to "Booked"

### Processing Checkout
1. Navigate to the **Checkout** tab
2. Select the booking to be checked out
3. Process the checkout to make the room available again

### Refreshing Data
- Use the "Refresh Available Rooms" button to update the room list
- The booking list automatically refreshes after each operation

## 📁 Project Structure

```
Hotel_Booking_System1/
├── src/
│   ├── Main.java                    # Application entry point
│   ├── dao/                         # Data Access Objects
│   │   ├── BookingDAO.java         # Booking database operations
│   │   ├── DBConnection.java       # Database connection utility
│   │   └── RoomDAO.java            # Room database operations
│   ├── model/                       # Data models
│   │   ├── Booking.java            # Booking entity
│   │   └── Room.java               # Room entity
│   ├── ui/                          # User Interface components
│   │   ├── AddRoomPanel.java       # Room addition interface
│   │   ├── BookingPanel.java       # Booking management interface
│   │   └── CheckoutPanel.java      # Checkout processing interface
│   └── util/                        # Utility classes
│       ├── DatabaseUtil.java       # Alternative DB utility
│       └── DBUtil.java             # Database helper methods
├── Hotel_Booking_System1.iml       # IntelliJ IDEA module file
└── README.md                        # Project documentation
```

## 🏗 Architecture

The application follows the **Model-View-Controller (MVC)** pattern with **DAO (Data Access Object)** pattern:

- **Model**: `Room.java`, `Booking.java` - Data entities
- **View**: UI package - Swing GUI components
- **Controller**: DAO package - Business logic and database operations
- **Database Layer**: MySQL with JDBC connectivity

## 🔧 Key Classes

- **Main.java**: Application launcher with GUI initialization
- **Room.java**: Room entity with properties (id, number, type, price, status)
- **Booking.java**: Booking entity with customer and date information
- **RoomDAO.java**: Room database operations (CRUD)
- **BookingDAO.java**: Booking database operations
- **BookingPanel.java**: Main booking interface
- **CheckoutPanel.java**: Checkout processing interface

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Future Enhancements

- [ ] Add room type management
- [ ] Implement booking search and filtering
- [ ] Add payment processing integration
- [ ] Generate booking reports
- [ ] Add customer management system
- [ ] Implement email notifications
- [ ] Add booking cancellation feature
- [ ] Multi-user authentication system

## 🐛 Known Issues

- Database credentials are hardcoded (should use configuration files)
- Limited error handling for invalid date formats
- No input validation for negative prices or invalid room numbers

## 📞 Support

For support, email abhishekgupta028@example.com or create an issue in the GitHub repository.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Developed by**: Abhishek Gupta  
**Repository**: [Hotel_Booking_System](https://github.com/abhishekgupta028/Hotel_Booking_System)  
**Last Updated**: October 2025