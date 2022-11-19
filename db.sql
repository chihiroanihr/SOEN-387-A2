/* Select Database */
USE soen387;


/* ------------ CREATE PROCEDURES ------------ */
DELIMITER $$

-- /* This login procedure will call ID and Password from Person table and return the result */
-- CREATE DEFINER=`root`@`localhost` PROCEDURE Login (IN userID BIGINT, IN password VARCHAR(32))  BEGIN
--  SELECT * 
--  FROM Person
--  WHERE userID = Person.personID and password = Person.password;
--  END$$

-- CREATE DEFINER=`root`@`localhost` PROCEDURE UserExist (IN userID BIGINT)  BEGIN
--  SELECT * 
--  FROM Person
--  WHERE userID = Person.personID;
--  END$$

DELIMITER ;


/* ------------ DROP TABLES ------------ */
DROP TABLE IF EXISTS StudentCourseEnrolled;
DROP TABLE IF EXISTS CourseByAdmin;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Person;


/* ------------ CREATE TABLES ------------ */

/* Table for Personal Info */
CREATE TABLE Person (
    personID BIGINT PRIMARY KEY,
    password VARCHAR(32) NOT NULL,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(75) NOT NULL,
    phoneNum BIGINT NOT NULL,
    address VARCHAR(200) NOT NULL
);

/* Table for Student */
CREATE TABLE Student (
    studentID BIGINT NOT NULL,
    CONSTRAINT fk_student_person_id
    FOREIGN KEY (studentID) REFERENCES Person(personID) ON UPDATE CASCADE
    /* A foreign key with cascade delete means that if a record in the parent table is deleted,
    then the corresponding records in the child table will automatically be deleted.
    This is called a cascade delete in SQL Server. */
);

/* Table for Admin(teacher) */
CREATE TABLE Admin (
    adminID BIGINT NOT NULL,
    CONSTRAINT fk_admin_person_id
    FOREIGN KEY (adminID) REFERENCES Person(personID) ON UPDATE CASCADE
);

/* Table for courses created by admin */
CREATE TABLE CourseByAdmin (
    adminID BIGINT NOT NULL,
    courseCode VARCHAR(30) NOT NULL,
    courseTitle VARCHAR(100) NOT NULL,
    semester VARCHAR(30) NOT NULL,
    daysOfWeek VARCHAR(30),
    startTime TIME,
    endTime TIME,
    room VARCHAR(100),
    startDate DATE,
    endDate DATE,
    PRIMARY KEY (adminID, courseCode, semester),
    CONSTRAINT fk_course_admin_id 
    FOREIGN KEY (adminID) REFERENCES Admin(adminID) ON UPDATE CASCADE
);

/* Table for student course enroll */
CREATE TABLE StudentCourseEnrolled (
    studentID BIGINT NOT NULL,
    courseCode VARCHAR(30) NOT NULL,
    semester VARCHAR(30) NOT NULL,
    adminID BIGINT NOT NULL,
    PRIMARY KEY (studentID, courseCode, semester),
    CONSTRAINT fk_enrolled_student_id 
    FOREIGN KEY (studentID) REFERENCES Student(studentID) ON UPDATE CASCADE,
    CONSTRAINT fk_student_course_code_semester
    FOREIGN KEY (adminID, courseCode, semester) REFERENCES CourseByAdmin(adminID, courseCode, semester) ON UPDATE CASCADE
);


/* ------------- INSERT DATA ------------- */
INSERT INTO Person (personID, password, firstName, lastName, dob, email, phoneNum, address) VALUES
('12345678', 'asdfasdf', 'John', 'Doe', '1999-08-23', 'john@gmail.com', '5147829384', '1-4535 Grand Boulevard, Montreal, QC, CA, H4B2Y1'),
('12345679', 'asdfasdf', 'Mary', 'Johnson', '1999-05-20', 'mary@gmail.com', '5143759200', '5-4535 Grand Boulevard, Montreal, QC, CA, H4B2Y1'),
('12345680', 'aagdafd', 'Jared', 'Rodriguez', '1997-03-20', 'jared@gmail.com', '5146539301', '453 Av. McGill, Montreal, QC, CA, H7J9K8'),
('12345681', 'faefas', 'Mia', 'Smith', '1995-01-13', 'mia@gmail.com', '5143456711', '1122 St. Guy, Montreal, QC, CA, J8H7G6'),
('12345682', 'asdgfds', 'Henry', 'Miller', '1993-05-30', 'henry@gmail.com', '5146748291', '4533 St. Maple, Toronto, ON, CA, J9K8F5'),
('00742123', 'asgdsafeawe', 'Samantha', 'Garcia', '1993-05-30', 'sam@gmail.com', '5146748291', '3-4422 Av. Walkley, Montreal, QC, CA, H4V2M9'),
('00291454', 'agfdssa', 'Jason', 'Chen', '1993-05-30', 'jason@gmail.com', '5146748291', '4453 Parc Avenue, Montreal, QC, CA, H2V3M2'),
('00148203', 'asdgrwa', 'Amanda', 'Davis', '1993-05-30', 'amanda@gmail.com', '5146748291', '2243 St. Patrick, Vancouver, BC, CA, K9J6G3'),
('00839283', 'awefwag', 'Jeffrey', 'Williams', '1993-05-30', 'jeffrey@gmail.com', '5146748291', '1-4535 Grand Boulevard, Montreal, QC, CA, H4B2Y1'),
('10001000', 'asdfasdf', 'Lyan', 'Lee', '1980-08-23', 'lyan@gmail.com', '5145554545', '101-1990 Rue St-Henri, Montreal, QC, CA, H9J8G5'),
('00829792', 'asgasfag', 'Jones', 'Brown', '1993-05-30', 'jones@gmail.com', '5146748291', '10-1122 Rue Candillac, Montreal, QC, CA, H0K9J7');

-- studentID must start with 1 and contain 8 digits in total
INSERT INTO Student(studentID) VALUES
('12345678'),
('12345679'),
('12345680'),
('12345681'),
('12345682');

-- adminID must start with 00 and contain 8 digits in total
INSERT INTO Admin(adminID) VALUES
('00742123'),
('00291454'),
('00148203'),
('00839283'),
('10001000'),
('00829792');

INSERT INTO CourseByAdmin(adminID, courseCode, courseTitle, semester, daysOfWeek, startTime, endTime, room, startDate, endDate) VALUES
('00742123', 'COMP352', 'Data Structures & Algorithms', 'W2023', '["Tue", "Thu"]', '8:45', '11:30', 'MB340', '2023-01-09', '2023-04-30'),
('00742123', 'COMP233', 'Stats for Comp. Sci.', 'F2023', '["Mon", "Wed"]', '11:45', '13:00', 'EV849', '2023-09-07', '2023-12-20'),
('00291454', 'COMP248', 'OOP with Java', 'F2023', '["Wed", "Fri"]', '9:45', '11:30', 'MB124', '2023-09-07', '2023-12-20'),
('00291454', 'COMP249', 'OOP with Java II', 'W2023', '["Tue", "Thur"]', '13:15', '15:30', 'H584', '2023-01-09', '2023-04-30'),
('00291454', 'COMP228', 'System Hardware', 'S2023', '["Wed", "Fri"]', '16:45', '18:30', 'LB121', '2023-05-01', '2023-06-30'),
('00148203', 'COMP352', 'Database', 'S2023', '["Mon", "Wed"]', '19:30', '20:45', 'MB377', '2023-05-01', '2023-06-30'),
('00742123', 'COMP335', 'Theoretical Comp. Sci.', 'F2023', '["Tue", "Thur"]', '10:45', '11:30', 'H433', '2023-09-07', '2023-12-20'),
('00839283', 'COMP348', 'Principles of Programming Languages', 'W2023', '["Wed", "Fri"]', '11:45', '13:00', 'H462', '2023-01-09', '2023-04-30'),
('00839283', 'COMP345', 'Advanced Program Design with C++', 'S2023', '["Tue", "Thur"]', '9:45', '11:00', 'LB211', '2023-05-01', '2023-06-30'),
('00829792', 'COMP354', 'Intro to Software Engineering', 'W2023', '["Wed", "Fri"]', '8:45', '11:30', 'H912', '2023-01-09', '2023-04-30'),
('10001000', 'SOEN287', 'Intro to Software Engineering', 'F2022', '["Sun", "Tue"]', '8:45', '11:30', 'LB112', '2022-09-09', '2022-12-12'),
('10001000', 'SOEN387', 'Enterprise Web Application', 'F2022', NULL, NULL, NULL, NULL, NULL, NULL),
('10001000', 'COMP346', 'Operating Systems', 'F2022', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO StudentCourseEnrolled(studentID, courseCode, semester, adminID) VALUES
('12345678','COMP248', 'F2023', '00291454'),
('12345679','COMP233', 'F2023', '00742123'),
('12345679','COMP348', 'W2023', '00839283'),
('12345679','COMP345', 'S2023', '00839283'),
('12345679','COMP354', 'W2023', '00829792'),
('12345680','COMP248', 'F2023', '00291454'),
('12345680','COMP233', 'F2023', '00742123'),
('12345680','COMP352', 'S2023', '00148203'),
('12345680','COMP345', 'S2023', '00839283'),
('12345680','COMP335', 'F2023', '00742123'),
('12345681','COMP352', 'S2023', '00148203'),
('12345681','COMP233', 'F2023', '00742123'),
('12345681','COMP345', 'S2023', '00839283'),
('12345681','COMP354', 'W2023', '00829792'),
('12345681','COMP346', 'F2022', '10001000'),
('12345682','COMP248', 'F2023', '00291454'),
('12345678','COMP345', 'S2023', '00839283'),
('12345678','COMP346', 'F2022', '10001000'),
('12345678','SOEN287', 'F2022', '10001000'),
('12345678','COMP352', 'S2023', '00148203');


/* ------------- COUNT DATA -------------- */
SELECT COUNT(*) FROM Person;
SELECT COUNT(*) FROM Student;
SELECT COUNT(*) FROM Admin;
-- SELECT COUNT(*) FROM Course;
SELECT COUNT(*) FROM CourseByAdmin;
SELECT COUNT(*) FROM StudentCourseEnrolled;
