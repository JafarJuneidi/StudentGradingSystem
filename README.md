# Project Documentation

## Part 1: Command-line / Sockets and JDBC Backend Implementation

### Design
Data flows from the Socket Client to the Socket Server via Serialized Packets. The Server queries the MySQL database through a DB Module layer that abstracts JDBC.

- **MySQL Docker Container**: 
  - Exposed a port from this container and used the MySQL database as my development database.
  - Mainly for the sake of using Docker, which made it easier to reproduce and manage.

- **DB Module**: 
  - A shared module between all parts, abstracting away logic and implementation concerning connecting to the MySQL database and all SQL queries.
  - Aiming for a light ORM-like structure.

- **Socket Server**: 
  - Receives requests from the user, leverages the DB Module to query the database, and sends responses back to the client.

- **Request/Response Communication**: 
  - Defines the communication protocol between the client and the server.

- **Socket Client**: 
  - A simple Socket Client that connects to the server with a console-based view model for different user roles.

### Implementation
Detailed documentation of the code, highlighting key functionalities, algorithms used, and important design decisions made during the development.

- **MySQL Docker Container**: 
  - Used WSL to run the container on my Windows machine, executed bash on the container, and wrote raw SQL to create the database and tables.
  - Database contains the following Tables:
    - `Users`: id, name, password, role_id.
    - `Courses`: id, name, instructor_id.
    - `Roles`: id, name. (Admin, Instructor, Student)
    - `Grades`: student_id, course_id, grade.

- **DB Module**: 
  - Used Gradle to download JDBC and JDBC driver.
  - The DB class constructor connects to the database and includes methods representing all CRUD operations (e.g., `addUser`, `getGrades`).
  - Defines types like `User`, `Course`, `StudentGrade`, which resemble a DAO.

- **Socket Server**: 
  - Creates a DB object on construction, receives serialized objects from the client, processes them, and returns DAO’s. It then creates a response, serializes it, and sends it back to the client.

- **Request/Response Communication**: 
  - Implemented using the Strategy pattern in a Packet package with all requests and responses implementing the main Request and Response interfaces (e.g., `GetUsersRequest`, `GetUsersResponse`).

- **Socket Client**: 
  - Establishes a connection to the server upon construction, requests the user to log in, and based on the user type, offers a list of commands. The client creates request objects, serializes them, and sends them to the server.

### Analytical Thinking
Critical evaluation of the strengths and weaknesses of each implementation stage.

- **MySQL Docker Container**: 
  - Dockerization allows for easy reusability and simplifies deployment preparation.
  - Initial setup was time-consuming due to lack of prior experience but proved worthwhile.

- **DB Module**: 
  - Reusable across different parts of the project, saving time and centralizing database access logic.
  - DAO classes could be improved, and table/relationship design could be more optimized.

- **Socket Server**: 
  - Initial challenges with Request/Response casting, but it offers type safety and flexibility.

- **Request/Response Communication**: 
  - Type safety and extensibility are strong points. The custom protocol offers learning opportunities, though potential faults may exist.

- **Socket Client**: 
  - Views are not modular or reusable, highlighting a weakness in CLI application design.

### Challenges and Solutions
Challenges faced during the development process and solutions to overcome them.

- **MySQL Docker Container**: 
  - Initial difficulties due to lack of Docker experience, but focusing on containerizing MySQL only saved time.

- **DB Module**: 
  - Writing SQL was tedious but straightforward.

- **Socket Server**: 
  - No significant challenges encountered.

- **Request/Response Communication**: 
  - Considered using JSON with Google’s GSON library but opted to develop a custom protocol for a deeper learning experience.

- **Socket Client**: 
  - Settling on a view model was challenging and resulted in a less modular approach.

### Security Considerations
Analysis of the security measures implemented in the system.

- **Dockerized MySQL**: 
  - Docker provides isolation, enhancing security, but exposed ports need secure configurations to prevent vulnerabilities.

- **Database Access**: 
  - JDBC abstracts direct SQL queries, but SQL injection remains a threat without proper input sanitization and validation.

- **Authentication**: 
  - Users log in, and their info is saved on the client, but the authentication method details are missing.

- **Role-Based Access Control (RBAC)**: 
  - Implementing strong RBAC ensures that users can only perform actions appropriate to their roles.

### Future Enhancements
Suggestions for potential improvements or features.

- **Container Orchestration**: 
  - Expand Docker usage to include all parts of the project, making it composable and easier to run anywhere.

- **Extend Role-Based Features**: 
  - Introduce more granular permissions within roles and allow users to have multiple roles.

- **Improved Security**: 
  - Study Cookies/Sessions/JWTs for better understanding and implementation.

---

## Part 2: Web App / Traditional MVC Servlets and JSPs Implementation

### Design
The application uses Servlets and enforces a Model-View-Controller architecture, with 5 Servlets serving 7 JSP pages. TailwindCSS is used for styling.

- **MySQL Docker Container**: Same as Part 1.

- **DB Module**: Same as Part 1.

- **LoginServlet**: Handles login through the DB Module’s login method.

- **LogoutServlet**: Terminates the session and redirects the user to `login.jsp`.

- **index.jsp**: Renders the JSP when a user session exists and includes `navbar.jsp`.

- **navbar.jsp**: Conditionally renders different navbars based on user role.

- **StudentServlet**: Queries the DB for students and their information, calculates statistics, and sends a `StudentDTO` to `students.jsp`.

- **student.jsp**: Displays the returned `StudentDTO` in a styled table.

- **CoursesServlet**: Queries the DB for courses, calculates statistics, and sends a `CoursesDTO` to `courses.jsp`.

- **grades.jsp**: Displays the returned grades in a styled table.

- **no_data.jsp**: Displays no data if any table is empty.

### Implementation
Detailed documentation of the code, highlighting key functionalities, algorithms used, and important design decisions.

- **MySQL Docker Container**: Same as Part 1.

- **DB Module**: Same as Part 1.

- **Servlets**: Simple classes with decorators that take `HttpServletRequest`, query the DB, and return `HttpServletResponse`.

- **JSP**: Receives DTOs from the server, renders the page on the server, and returns HTML pages.

### Analytical Thinking
Critical evaluation of the strengths and weaknesses of each implementation stage.

- **MySQL Docker Container**: Same as Part 1.

- **DB Module**: Same as Part 1.

- **Servlets**: The structured approach with HTTP conventions adds structure and improves upon the Serialization approach in Part 1.

- **JSP**: Offers flexibility in rendering custom HTML pages but comes with the downside of requiring server requests and page refreshes for navigation.

### Challenges and Solutions
Challenges faced during the development process and solutions to overcome them.

- **Auth**: Initial challenges with cookies and GSON were resolved by using `HttpSession` for storing user info and authorizing them.

- **Setup**: Setting up the server (Tomcat), the Servlet package, and JSP was confusing and time-consuming but ultimately successful.

### Security Considerations
Analysis of the security measures implemented in the system.

- **Dockerized MySQL**: Same as Part 1.

- **Database Access**: Same as Part 1.

- **Input Validation**: Sanitized user inputs to prevent SQL injection attacks. Input validation techniques ensure only valid data is processed.

- **Session Management**: `HttpSession` is used for user information storage and authorization. Sessions are timed out to prevent hijacking.

- **Role-Based Access Control (RBAC)**: The `navbar.jsp` component checks user roles and conditionally renders different navbars, ensuring users only access authorized data.

### Future Enhancements
Suggestions for potential improvements or features.

- **Transition to SPAs**: Consider transitioning to a Single Page Application (SPA) architecture for smoother navigation and better state management.

- **Implement API Endpoints**: Create RESTful API or GraphQL endpoints for smoother data fetching and manipulation.

- **Responsive Design**: Ensure the web application is fully responsive and mobile-friendly.

- **Advanced User Management**: Introduce features like password recovery and profile customization for enhanced security and personalization.

---

## Part 3: Web App / Spring MVC and Spring REST Implementation

### Design
This part uses a Spring Boot REST API with the DB Module for the backend and a React.js frontend.

- **MySQL Docker Container**: Same as Part 1.

- **DB Module**: Same as Part 1.

- **Controllers**: REST controllers handle frontend requests and send responses.

- **Models**: Models facilitate data communication and conversion.

- **React Frontend**: Displays JavaScript and makes API requests during runtime without the need to refresh the browser page.

### Implementation
Detailed documentation of the code, highlighting key functionalities, algorithms used, and important design decisions.

- **MySQL Docker Container**: Same as Part 1.

- **DB Module**: Same as Part 1.

- **Spring REST Controllers**: Handle HTTP requests and responses, providing a clean separation of concerns.

- **React.js**: JavaScript-based frontend that offers a smooth, responsive user experience.

### Analytical Thinking
Critical evaluation of the strengths and weaknesses of each implementation stage.

- **MySQL Docker Container**: Same as Part 1.

- **DB Module**: Same as Part 1.

- **Spring REST Controllers**: Improves upon the previous Servlet-based approach by offering a more modern, scalable way to build web applications.

- **React.js**: Provides a responsive, dynamic user interface, enhancing the user experience.

### Challenges and Solutions
Challenges faced during the development process and solutions to overcome them.

- **Spring Boot Setup**: Initial difficulties were resolved by extensive study and trial-and-error. Familiarity with Spring Boot was essential to the project's success.

- **React.js Integration**: Integrating React.js with Spring Boot required careful coordination, particularly in managing state and data flow.

### Security Considerations
Analysis of the security measures implemented in the system.

- **Dockerized MySQL**: Same as Part 1.

- **Database Access**: Same as Part 1.

- **Authentication and Authorization**: Implemented OAuth2 for secure user authentication and authorization.

- **Input Validation**: Same as Part 2.

- **Session Management**: Improved session management with JWTs for better security.

### Future Enhancements
Suggestions for potential improvements or features.

- **Enhanced API Features**: Introduce advanced filtering, pagination, and sorting capabilities for API endpoints.

- **Progressive Web App (PWA)**: Consider converting the application into a PWA for better performance and offline capabilities.

- **Cloud Deployment**: Deploy the application to cloud platforms like AWS or Azure for scalability and availability.

- **Real-Time Features**: Implement WebSockets for real-time communication and updates within the application.

---

## Part 4: Backend as a Service (BaaS) Firebase Implementation

### Design
This part is a Firebase-based implementation with Firebase Authentication and Firestore Database, using React for the frontend.

- **Firebase Authentication**: Manages user authentication with OAuth2 integration.

- **Firestore Database**: A NoSQL database to store user data and application state.

### Implementation
Detailed documentation of the code, highlighting key functionalities, algorithms used, and important design decisions.

- **Firebase Setup**: Initialized Firebase project, configured Firestore, and set up Firebase Authentication.

- **React.js**: Frontend integrates with Firebase SDKs for authentication and database interactions.

### Analytical Thinking
Critical evaluation of the strengths and weaknesses of each implementation stage.

- **Firebase Authentication**: Simplifies authentication management but relies heavily on third-party services.

- **Firestore Database**: Easy to set up and use but has limitations in terms of complex queries and relational data.

- **React.js**: Same as Part 3.

### Challenges and Solutions
Challenges faced during the development process and solutions to overcome them.

- **Firebase Quotas**: Managed usage within free tier limits, considering potential future costs.

- **Data Modeling**: Adjusted traditional relational data models to fit NoSQL schema design principles.

### Security Considerations
Analysis of the security measures implemented in the system.

- **Firebase Security Rules**: Configured Firestore Security Rules to ensure data integrity and privacy.

- **OAuth2 Integration**: Same as Part 3.

- **Input Validation**: Same as Part 2.

### Future Enhancements
Suggestions for potential improvements or features.

- **Advanced Security Features**: Implement role-based access controls within Firebase.

- **Serverless Functions**: Utilize Firebase Cloud Functions for backend logic and automation.

- **User Analytics**: Integrate Firebase Analytics for better user behavior insights.

- **Enhanced Offline Capabilities**: Implement Firestore’s offline persistence features for better user experience in low-connectivity environments.
