# CivilProtection-Application
My BSc thesis project: an Application for the Civil Protection that has been designed to provide the population with timely warnings about potential weather or natural emergencies.


## Description
The Civil Protection Application is a BSc thesis project aimed at developing an alert system for civil protection purposes. This application provides timely warnings to the population about potential weather or natural emergencies. It features a search system allowing users to filter emergencies by Postal Code (CAP), severity level, and occurrence date. Additionally, it includes a searchable archive of past emergencies, enhancing awareness and preparedness. Developed entirely in Java with a robust database system, the application efficiently manages information from entities distributed across the national territory. An algorithm to classify emergency severity levels based on received information was also designed and implemented, alongside a preliminary analysis and system architecture design.

## Key Features
- Storage system management for measurements from third-party entities or sensors.
- Decision logic for emergency alert levels.
- Historical management of previous alerts.
- Search functionality with filters based on CAP, date, and type of event.
- Subscription process management for platform users.
- Push notification system for alert dissemination.

## Technologies
- Java for application development.
- SQL for database management.

## Installation
The project is contained within the `Java>ProgettoLaurea` folder and was developed using the Eclipse framework. To set up the project, follow these steps:
1. Ensure you have Java and Eclipse installed on your machine.
2. Clone the repository or download the project folder to your local machine.
3. Open Eclipse and import the project by selecting `File > Import > General > Existing Projects into Workspace`.
4. Navigate to the `Java>ProgettoLaurea` directory as the project root.

## How to Use
The application features a graphical user interface for interaction. After launching the application, users can:
- Search for emergencies by CAP, severity, and date.
- Browse the archive of past emergencies.
- Subscribe to the platform for real-time alerts.
- Receive push notifications for relevant emergencies.

## Dependencies
For testing, an instance of the database containing sample records is integrated but not essential for operation. Ensure Java and Eclipse are installed to compile and run the application.

# Project Documentation

## Overview
This project is structured into several key folders, each serving a distinct purpose in the development and documentation process. Below is a guide to what you can find in each folder.

### Design Folder
The `Design` folder is where we've placed our comprehensive UML design analysis. Within this folder, you will find detailed reports on various diagrams that are crucial for understanding the architecture and functionality of our application. These include:

- **Class Diagram:** Outlines the structure of the system's classes.
- **Activities Diagram:** Depicts the flow of operations.
- **Sequence Diagram:** Illustrates the interaction between objects over time.
- **Deployment Diagram:** Shows the physical arrangement of components.
- **Collaboration Diagram:** Details the relationships and interactions between components.

### Requirements Folder
The `Requirements` folder houses the iStar project files, alongside PDF documents that detail the requirements analysis and the feasibility study conducted at the project's outset. These documents offer insights into the decision-making process, highlighting how specific requirements and features were determined to be necessary or feasible.

### Final Folder
The `Final` folder serves as a comprehensive repository for the entirety of our project's analysis. It encapsulates all insights and findings from the Design and Requirements phases and contains the actual code of the project.



# Contributing
This project was developed as a BSc thesis and is primarily for academic demonstration. While it is not actively seeking contributions, feedback and suggestions are welcome.
Special thanks to my colleagues and team mates Maria Chiara di Falco and Mirko Bonanomi.

## Support
For support or further information, please contact me at [lorenzo.bancale@gmail.com].
