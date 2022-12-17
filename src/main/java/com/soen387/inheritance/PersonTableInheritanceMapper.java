package com.soen387.inheritance;

import java.sql.SQLException;
import java.sql.*;

import static com.soen387.db.db_connection.close;
import static com.soen387.db.db_connection.connect;

public class PersonTableInheritanceMapper {
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private static final String FIND_BY_ID = "SELECT * FROM Person WHERE personID = ?";
    private static final String FIND_STUDENT = "SELECT * FROM Student WHERE studentID = ?";
    private static final String FIND_ADMIN = "SELECT * FROM Admin WHERE adminID = ?";

    private static final String FIND_BY_ID_AND_PASSWORD = "SELECT * FROM Person WHERE personID = ? AND password = ?";
    private static final String INSERT_PERSON = "INSERT INTO Person (personID, password, firstName, lastName, dob, email, phoneNum, address) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_STUDENT = "INSERT INTO Student (studentID) VALUES(?)";
    private static final String INSERT_ADMIN = "INSERT INTO Admin (studentID) VALUES(?)";

    private static final String UPDATE_PERSON = "UPDATE Person SET personID = ?, password = ?, firstName = ?, lastName = ?, dob = ?, email = ?, phoneNum = ?, address = ? WHERE personID = ?";
    private static final String UPDATE_ADMIN = "UPDATE Admin SET adminID = ? WHERE adminID = ?";
    private static final String UPDATE_STUDENT = "UPDATE Student SET studentID = ? WHERE studentID = ?";

    private static final String DELETE_PERSON = "DELETE FROM Person WHERE personID = ?";
    private static final String DELETE_STUDENT = "DELETE FROM Student WHERE studentID = ?";
    private static final String DELETE_ADMIN = "DELETE FROM Admin WHERE adminID = ?";


    private Person extractPersonFromResultSet(ResultSet rs) throws SQLException {
        long ID = rs.getLong("personID");
        String password = rs.getString("password");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        Date dob = rs.getDate("dob");
        String email = rs.getString("email");
        long phoneNum = rs.getLong("phoneNum");
        String address = rs.getString("address");

        return new Person(ID, password, firstName, lastName, address, email, phoneNum, dob);
    }

    public <T extends Person> T find_template(long id) {
        // Query the database to find the object with the given ID
        // If found, determine the type of object and instantiate the appropriate subclass
        System.out.println("Executing statement...");
        Person person = null;
        try {
            conn = connect();

            // Retreiving the Person object first
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            // If person found in database
            if (rs.next()) {
                // Extract person info
                person = extractPersonFromResultSet(rs);
            }

            // Checking if person is a student. Returning a student object in that case.
            stmt = conn.prepareStatement(FIND_STUDENT);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Student student = new Student(person.getID(), person.getPassword(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getEmail(), person.getPhoneNumber(), person.getDateOfBirth());
                // Return student object with type student
                return (T)student;
            }

            // Checking if person is an Admin. Returning an admin object in that case.
            stmt = conn.prepareStatement(FIND_ADMIN);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin(person.getID(), person.getPassword(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getEmail(), person.getPhoneNumber(), person.getDateOfBirth());
                // Return admin object with type Administrator
                return (T)admin;
            }

        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        // If person neither assigned to Student nor Admin, then return with type Person
        return (T)person;
    }

    public <T extends Person> T authenticateUser(long userId, String password) {
        System.out.println("Executing authenticateUser() method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_BY_ID_AND_PASSWORD);
            stmt.setLong(1, userId);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            // if record exists, extract data from ResultSet and create a Person object
            if (rs.next()) {
                return find_template(userId);
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return null;
    }

    public boolean checkUserRegistered(long userId) {
        System.out.println("Executing checkUserRegistered() method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, userId);

            rs = stmt.executeQuery();

            return rs.next(); // this returns true if row exists else false
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }
    }

    // Method to update an object in the database
    public <T extends Person> boolean update(T person) {
        // Update the corresponding row in the database table
        System.out.println("Executing statement..");
        try {
            conn = connect();

            if (person.getType().equals("person")) {
                stmt = conn.prepareStatement(UPDATE_PERSON);
                stmt.setLong(1, person.getID());
                stmt.setString(2, person.getPassword());
                stmt.setString(3, person.getFirstName());
                stmt.setString(4, person.getLastName());
                stmt.setDate(5, person.getDateOfBirth());
                stmt.setString(6, person.getEmail());
                stmt.setLong(7, person.getPhoneNumber());
                stmt.setString(8, person.getAddress());

                int i = stmt.executeUpdate();

                if (i == 1) {
                    System.out.println("Data successfully updated from Person table.");
                    return true;
                }
            }
            // no need to update student or admin as all it shares same variables as person
//            if (person.getType().equals("student"))
//            {
//                stmt = conn.prepareStatement(UPDATE_STUDENT);
//                stmt.setLong(1, person.getID());
//                int i = stmt.executeUpdate();
//                if (i == 1) {
//                    System.out.println("Data successfully updated from Student table.");
//                    return true;
//                }
//            }
//            else if (person.getType().equals("admin"))
//            {
//                stmt = conn.prepareStatement(UPDATE_ADMIN);
//                stmt.setLong(1, person.getID());
//                int i = stmt.executeUpdate();
//                if (i == 1) {
//                    System.out.println("Data successfully updated from Admin table.");
//                    return true;
//                }
//            }
        } catch (SQLIntegrityConstraintViolationException error) {
            error.printStackTrace();
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return false;
    }

    // Method to insert a new object into the database
    public <T extends Person> boolean insert(T person, String userType) {
        // Insert a new row in the table
        // Save the object to the database
        System.out.println("Executing statement...");
        try {
            conn = connect();

            // First, insert into Person table
            stmt = conn.prepareStatement(INSERT_PERSON);
            stmt.setLong(1, person.getID());
            stmt.setString(2, person.getPassword());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setDate(5, person.getDateOfBirth());
            stmt.setString(6, person.getEmail());
            stmt.setLong(7, person.getPhoneNumber());
            stmt.setString(8, person.getAddress());

            int i = stmt.executeUpdate();

            // Successfully inserted to Person data
            if (i == 1) {
                System.out.println("Data successfully inserted to Person table.");
                // Insert into Student table if userType == student
                if (userType.equals("student"))
                {
                    stmt = conn.prepareStatement(INSERT_STUDENT);
                    stmt.setLong(1, person.getID());
                    int j = stmt.executeUpdate();
                    if (j == 1) {
                        System.out.println("Data successfully inserted to Person table.");
                        return true;
                    }
                }
                // Insert into Admin table if userType == student
                else if (userType.equals("admin"))
                {
                    stmt = conn.prepareStatement(INSERT_ADMIN);
                    stmt.setLong(1, person.getID());
                    int j = stmt.executeUpdate();
                    if (j == 1) {
                        System.out.println("Data successfully inserted to Person table.");
                        return true;
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        } finally {
            close(rs, stmt, conn);
        }

        return false;
    }

    // Method to delete an object from the database
    public <T extends Person> boolean delete(T person) {
        System.out.println("Executing statement...");
        try {
            conn = connect();

            if (person.getType().equals("student"))
            {
                stmt = conn.prepareStatement(DELETE_STUDENT);
                stmt.setLong(1, person.getID());
                int i = stmt.executeUpdate();
                if (i == 1) {
                    System.out.println("Data successfully deleted from Student table.");
                }
            }
            else if (person.getType().equals("admin"))
            {
                stmt = conn.prepareStatement(DELETE_ADMIN);
                stmt.setLong(1, person.getID());
                int i = stmt.executeUpdate();
                if (i == 1) {
                    System.out.println("Data successfully deleted from Admin table.");
                }
            }
            if (person.getType().equals("person")) {
                stmt = conn.prepareStatement(DELETE_PERSON);
                stmt.setLong(1, person.getID());

                int i = stmt.executeUpdate();

                if (i == 1) {
                    System.out.println("Data successfully deleted from Person table.");
                    return true;
                }
            }

        } catch (SQLIntegrityConstraintViolationException error) {
            error.printStackTrace();
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return false;
    }
}