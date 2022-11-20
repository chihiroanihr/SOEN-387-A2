package com.soen387.daoImpl;

import com.soen387.beans.Person;
import com.soen387.dao.StudentDao;
import com.soen387.beans.Student;

import java.sql.*;

import static com.soen387.db.db_connection.*;

public class StudentDaoImpl implements StudentDao {
    // SQL Statements

    //Checks if student exists
    private static final String FIND_BY_ID = "SELECT * FROM Student WHERE studentID = ?;";

    //Inserts student into Student table
    private static final String INSERT = "INSERT INTO Student (studentID) VALUES(?)";

   //Find student's personal info
    private static final String GET_INFO = "SELECT personID, password, firstName, lastName, dob, email, phoneNum, address " +
            "FROM Person " +
            "INNER JOIN Student " +
            "ON Person.personID = Student.studentID " +
            "WHERE Person.personID = ?;";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    //Extract data from ResultSet object and instantiate a Student object
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        long studentId = rs.getLong("studentID");

        return new Student(studentId);
    }

    //Check if student ID exists in the database
    public boolean checkIsStudent(long studentId) {
        System.out.println("Executing checkIsStudent method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, studentId);

            rs = stmt.executeQuery();
            return rs.next(); // this returns true if row exists else false

        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }
    }

    //Insert a Student record into Student table
    public boolean insert(Student student) {
        System.out.println("Executing insert(Student) method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(INSERT);
            stmt.setLong(1, student.getStudentId());

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully inserted to Student table.");
                return true;
            }
        } catch (SQLException error) {
            error.printStackTrace();
        } finally {
            close(rs, stmt, conn);
        }

        return false;
    }

    //Extract data from ResulSet object and instantiate a Person object
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
}
