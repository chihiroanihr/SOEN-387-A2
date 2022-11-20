package com.soen387.daoImpl;

import com.soen387.beans.CourseByAdmin;
import com.soen387.beans.Person;
import com.soen387.beans.Student;
import com.soen387.beans.StudentCourseEnrolled;
import com.soen387.dao.StudentCourseEnrolledDao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static com.soen387.db.db_connection.close;
import static com.soen387.db.db_connection.connect;

public class StudentCourseEnrolledDaoImpl implements StudentCourseEnrolledDao {

    //Drops student from a course
    private static final String DELETE = "DELETE FROM StudentCourseEnrolled WHERE studentID = ? AND courseCode = ? AND semester = ? AND adminID = ?;";

    //Inserts a record into StudentCourseEnrolled. This occurs when a student registers for a course
    private static final String INSERT = "INSERT INTO StudentCourseEnrolled(studentID, courseCode, semester, adminID) VALUES (?, ?, ?, ?);";

    //Deletes all students enrolled in a course
    private static final String DELETE_ALL = "DELETE FROM StudentCourseEnrolled WHERE adminID = ? AND courseCode = ? AND semester = ?;";

    // Finds all students enrolled in certain course and Get only student ID
    private static final String FIND_ALL_STUDENT_ID_IN_COURSE = "SELECT studentID FROM StudentCourseEnrolled WHERE courseCode = ? AND semester = ? AND adminID = ?;";
    // Finds all student enrolled in certain course and Get student-person info
    private static final String FIND_ALL_STUDENTS_IN_COURSE = "SELECT P.personID, P.password, P.firstName, P.lastName, P.dob, P.email, P.phoneNum, P.address " +
            "FROM Person AS P " +
            "INNER JOIN StudentCourseEnrolled AS S " +
            "ON S.studentID = P.personID " +
            "WHERE S.courseCode = ? AND S.semester = ? AND S.adminID = ?;";

    private static final String FIND_ALL_COURSES_ENROLLED_BY_STUDENT = "SELECT A.courseCode, A.courseTitle, A.semester, A.daysOfWeek, A.startTime, A.endTime, A.room, A.startDate, A.endDate, A.adminID " +
            "    FROM CourseByAdmin as A " +
            "    INNER JOIN StudentCourseEnrolled as S " +
            "    ON S.courseCode = A.courseCode AND S.semester = A.semester AND S.adminID = A.adminID " +
            "    WHERE S.studentID = ?;";

    private static final String FIND_ALL_COURSES_AVAILABLE_FOR_STUDENT = "SELECT A.courseCode, A.courseTitle, A.semester, A.daysOfWeek, A.startTime, A.endTime, A.room, A.startDate, A.endDate, A.adminID " + "" +
            "FROM CourseByAdmin as A " +
            "WHERE NOT EXISTS " +
            "(" +
            "SELECT studentID, courseCode, semester, adminID " +
            "FROM StudentCourseEnrolled AS S " +
            "WHERE S.studentID = ? AND S.courseCode = A.courseCode AND S.semester = A.semester AND S.adminID = A.adminID " +
            ") " +
            "ORDER BY A.courseCode, A.semester;";
    private static final String CHECK_CLASS_TIME_OVERLAP = "SELECT S.studentID, A.adminID, A.courseCode, A.semester, A.daysOfWeek, A.startTime, A.endTime, A.startDate, A.endDate " +
            "FROM StudentCourseEnrolled as S " +
            "INNER JOIN CourseByAdmin as A " +
            "ON S.courseCode = A.courseCode AND S.semester = A.semester AND S.adminID = A.adminID " +
            "WHERE S.studentID = ? " +
            "AND A.semester = ? " +
            "AND (A.startTime <= ? AND ? <= A.endTime) " +
            "AND (A.startDate <= ? AND ? <= A.endDate);";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    // Drop student from a course by deleting a record from the StudentCourseEnrolled table
    public boolean delete(StudentCourseEnrolled studentCourseEnrolled) {
        System.out.println("Executing delete(Student) method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(DELETE);
            stmt.setLong(1, studentCourseEnrolled.getStudentID());
            stmt.setString(2, studentCourseEnrolled.getCourseCode());
            stmt.setString(3, studentCourseEnrolled.getSemester());
            stmt.setLong(4, studentCourseEnrolled.getAdminID());

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully deleted from StudentCourseEnrolled table.");
                return true;
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

    // Enroll student to a course by inserting a record to StudentCourseEnrolled table
    public boolean insert(StudentCourseEnrolled studentCourseEnrolled) {
        System.out.println("Executing insert(Student) method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(INSERT);
            stmt.setLong(1, studentCourseEnrolled.getStudentID());
            stmt.setString(2, studentCourseEnrolled.getCourseCode());
            stmt.setString(3, studentCourseEnrolled.getSemester());
            stmt.setLong(4, studentCourseEnrolled.getAdminID());

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully inserted to StudentCourseEnrolled table.");
                return true;
            }
        } catch (SQLException error) {
            error.printStackTrace();
        } finally {
            close(rs, stmt, conn);
        }

        return false;
    }

    //Delete all students enrolled in a certain course
    public boolean deleteAll(long adminId, String courseCode, String semester) {
        System.out.println("Executing deleteAll method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(DELETE_ALL);
            stmt.setLong(1, adminId);
            stmt.setString(2, courseCode);
            stmt.setString(3, semester);

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("All data successfully deleted from StudentCourseEnrolled table.");
                return true;
            } else if (i == 0) {
                System.out.println("No data available to delete from StudentCourseEnrolled table.");
                return true;
            }
        } catch (SQLException error) {
            error.printStackTrace();
        } finally {
            close(rs, stmt, conn);
        }

        return false;
    }

    //Find all students enrolled in a specific course
    //Returns a list of students
    public List<Student> findAllStudentID(long adminId, String courseCode, String semester) {
        List<Student> students = new ArrayList<>();

        System.out.println("Executing findAllStudentID method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_ALL_STUDENT_ID_IN_COURSE);
            stmt.setString(1, courseCode);
            stmt.setString(2, semester);
            stmt.setLong(3, adminId);
            rs = stmt.executeQuery();

            // If result set is empty
            while (rs.next()) {
                Student student = extractStudentFromResultSet(rs);
                students.add(student);
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return students;
    }

    //Find all students enrolled in a specific course
    //Returns a list of students, with all their personal info
    public List<Person> findAllStudentInfo(long adminId, String courseCode, String semester) {
        List<Person> students = new ArrayList<>();

        System.out.println("Executing findAllStudentInfo method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_ALL_STUDENTS_IN_COURSE);
            stmt.setString(1, courseCode);
            stmt.setString(2, semester);
            stmt.setLong(3, adminId);
            rs = stmt.executeQuery();

            // If result set is empty
            while (rs.next()) {
                Person student = extractPersonFromResultSet(rs);
                students.add(student);
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return students;
    }

    //Find courses enrolled by a certain student
    //Returns a list of courses, each with course info
    public List<CourseByAdmin> findAllCoursesEnrolledByStudent(long studentId) {
        List<CourseByAdmin> courses = new ArrayList<>();

        System.out.println("Executing findAllCoursesEnrolledByStudent method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_ALL_COURSES_ENROLLED_BY_STUDENT);
            stmt.setLong(1, studentId);
            rs = stmt.executeQuery();

            // If result set is not empty
            while (rs.next()) {
                CourseByAdmin course = extractCourseFromResultSet(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, stmt, conn);
        }

        return courses;
    }

    //Find courses available for students to enroll
    //Returns a list of courses created by admins and that the student has not enrolled yet
    public List<CourseByAdmin> findAllCoursesAvailableForStudent(long studentId) {
        List<CourseByAdmin> courses = new ArrayList<>();

        System.out.println("Executing findAllCoursesAvailableForStudent method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_ALL_COURSES_AVAILABLE_FOR_STUDENT);
            stmt.setLong(1, studentId);
            rs = stmt.executeQuery();

            // If result set is not empty
            while (rs.next()) {
                CourseByAdmin course = extractCourseFromResultSet(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, stmt, conn);
        }

        return courses;
    }

    //Extract data from ResultSet object and instantiate a Student object
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        long studentId = rs.getLong("studentId");

        return new Student(studentId);
    }

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

    //Extract data from ResultSet object and instantiate a CourseByAdmin object
    private CourseByAdmin extractCourseFromResultSet(ResultSet rs) throws SQLException {
        long adminId = rs.getLong("adminID");
        String courseCode = rs.getString("courseCode");
        String courseTitle = rs.getString("courseTitle");
        String semester = rs.getString("semester");
        String daysOfWeek = rs.getString("daysOfWeek");
        Time startTime = rs.getTime("startTime");
        Time endTime = rs.getTime("endTime");
        String room = rs.getString("room");
        Date startDate = rs.getDate("startDate");
        Date endDate = rs.getDate("endDate");

        return new CourseByAdmin(adminId, courseCode, courseTitle, semester, daysOfWeek, startTime, endTime, room, startDate, endDate);
    }

    public boolean checkClassTimeOverlap(Student student, CourseByAdmin courseByAdmin) {
        System.out.println("Executing checkClassTimeOverlap method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(CHECK_CLASS_TIME_OVERLAP);
            stmt.setLong(1, student.getStudentId());
            stmt.setString(2, courseByAdmin.getSemester());
            stmt.setTime(3, courseByAdmin.getEndTime());
            stmt.setTime(4, courseByAdmin.getStartTime());
            stmt.setDate(5, courseByAdmin.getEndDate());
            stmt.setDate(6, courseByAdmin.getStartDate());

            rs = stmt.executeQuery();

            // If row exists --> Duplicates found
            while (rs.next()) {
                // Check daysOfWeek overlap as well
                String daysOfWeekThis = courseByAdmin.getDaysOfWeek();
                String daysOfWeekOther = rs.getString("daysOfWeek");

                // If daysOfWeek value is null --> no duplicates found
                if (daysOfWeekThis == null || daysOfWeekOther == null)
                    return false;

                // Convert string literal list into list of type string
                List<String> daysOfWeekThisLst = convertStrListToList(daysOfWeekThis);
                List<String> daysOfWeekOtherLst = convertStrListToList(daysOfWeekOther);

                // If daysOfWeek duplicates also found
                if (checkClassDaysOfWeekOverlap(daysOfWeekThisLst, daysOfWeekOtherLst)) {
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

    private boolean checkClassDaysOfWeekOverlap(List<String> lst1, List<String> lst2) {
        // create hashsets
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        // Adding elements from array1
        for (String i : lst1) {
            set1.add(i);
        }
        // Adding elements from array2
        for (String i : lst2) {
            set2.add(i);
        }
        // Use retainAll() method to find common elements between two daysOfWeek arrays
        set1.retainAll(set2);

        // Return false if no common elements found, otherwise true
        return !set1.isEmpty();
    }

    private List<String> convertStrListToList(String daysOfWeekStr) {
        daysOfWeekStr = daysOfWeekStr
                .replace("[", "")
                .replace("]", "");
        String[] array = daysOfWeekStr.split(",");
        List<String> daysOfWeekLst = Arrays.asList(array).stream().map(String::trim).collect(Collectors.toList());

        return daysOfWeekLst;
    }

}
