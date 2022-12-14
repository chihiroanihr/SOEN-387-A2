package com.soen387.daoImpl;

import com.soen387.dao.PersonDao;
import com.soen387.beans.Person;

import java.sql.*;

import static com.soen387.db.db_connection.*;

public class PersonDaoImpl implements PersonDao {
    // SQL Statements

    //Checks if user ID and password exist for login authentication
    private static final String FIND_BY_ID_AND_PASSWORD = "SELECT * FROM Person WHERE personID = ? AND password = ?;";

    //Finds a Person record from table Person
    private static final String FIND_BY_ID = "SELECT * FROM Person WHERE personID = ?;";

    //Inserts a record into Person table
    private static final String INSERT = "INSERT INTO Person (personID, password, firstName, lastName, dob, email, phoneNum, address) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String DELETE = "DELETE FROM user WHERE id=?;";
    private static final String FIND_ALL = "SELECT * FROM user ORDER BY id;";
    private static final String FIND_BY_NAME = "SELECT * FROM user WHERE name=?;";
    private static final String UPDATE = "UPDATE user SET name=?, tel=?, passwd=? WHERE id=?;";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    //Extract data from ResultSet object and instantiate a Person object
    private Person extractPersonFromResultSet(ResultSet rs) throws SQLException {
        long personId = rs.getLong("personID");
        String password = rs.getString("password");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        Date dob = rs.getDate("dob");
        String email = rs.getString("email");
        long phoneNum = rs.getLong("phoneNum");
        String address = rs.getString("address");

        return new Person(personId, password, firstName, lastName, dob, email, phoneNum, address);
    }

    //Verify that the user ID and password exist in the database
    //Used in Login form
    public Person authenticateUser(long userId, String password) {
        System.out.println("Executing authenticateUser method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_BY_ID_AND_PASSWORD);
            stmt.setLong(1, userId);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            //if record exists, extract data from ResultSet and create a Person object
            if (rs.next()) {
                return extractPersonFromResultSet(rs);
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return null;
    }

    //Check if user is already registered
    //Return true if a record is found
    public boolean checkUserRegistered(long userId) {
        System.out.println("Executing checkUserRegistered method...");
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

    //Find user info by searching Person table
    public Person findUserInfo(long userId) {
        System.out.println("Executing findUserInfo method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, userId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return extractPersonFromResultSet(rs);
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return null;
    }

    //Insert a Person record into Person table
    public boolean insert(Person person) {
        System.out.println("Executing insert(Person) method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(INSERT);
            stmt.setLong(1, person.getPersonId());
            stmt.setString(2, person.getPassword());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setDate(5, person.getDob());
            stmt.setString(6, person.getEmail());
            stmt.setLong(7, person.getPhoneNum());
            stmt.setString(8, person.getAddress());

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully inserted to Person table.");
                return true;
            }
        } catch (SQLException error) {
            error.printStackTrace();
        } finally {
            close(rs, stmt, conn);
        }

        return false;
    }
}
