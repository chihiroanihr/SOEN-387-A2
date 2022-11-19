package com.soen387.daoImpl;

import com.soen387.beans.CourseByAdmin;
import com.soen387.dao.CourseByAdminDao;

import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.soen387.db.db_connection.close;
import static com.soen387.db.db_connection.connect;

public class CourseByAdminDaoImpl implements CourseByAdminDao {
    // SQL Statements
    private static final String FIND_BY_ADMIN_ID = "SELECT * FROM CourseByAdmin WHERE adminID = ?";
    private static final String INSERT = "INSERT INTO CourseByAdmin (adminID, courseCode, courseTitle, semester, daysOfWeek, startTime, endTime, room, startDate, endDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE CourseByAdmin SET courseTitle = ?, daysOfWeek = ?, startTime = ?, endTime = ?, room = ?, startDate = ?, endDate = ? WHERE adminID = ? AND courseCode = ? AND semester = ?";
    private static final String DELETE = "DELETE FROM CourseByAdmin WHERE adminID =? AND courseCode = ? AND semester = ?";
    private static final String CHECK_CLASS_TIME_OVERLAP = "SELECT * FROM CourseByAdmin WHERE adminID = ? AND semester = ? AND (startTime <= ? AND ? <= endTime) AND (startDate <= ? AND ? <= endDate)";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

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

    public List<CourseByAdmin> findAllCoursesByAdmin(long adminId) {
        List<CourseByAdmin> courses = new ArrayList<>();

        System.out.println("Executing statement...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_BY_ADMIN_ID);
            stmt.setLong(1, adminId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CourseByAdmin course = extractCourseFromResultSet(rs);
                courses.add(course);
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }

        return courses;
    }

    public boolean delete(long adminId, String courseCode, String semester) {
        System.out.println("Executing statement...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(DELETE);
            stmt.setLong(1, adminId);
            stmt.setString(2, courseCode);
            stmt.setString(3, semester);

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully deleted from CourseByAdmin table.");
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

    public boolean insert(CourseByAdmin courseByAdmin) {
        System.out.println("Executing statement..");
        try {
            conn = connect();
            stmt = conn.prepareStatement(INSERT);
            stmt.setLong(1, courseByAdmin.getAdminID());
            stmt.setString(2, courseByAdmin.getCourseCode());
            stmt.setString(3, courseByAdmin.getCourseTitle());
            stmt.setString(4, courseByAdmin.getSemester());
            stmt.setString(5, courseByAdmin.getDaysOfWeek());
            stmt.setTime(6, courseByAdmin.getStartTime());
            stmt.setTime(7, courseByAdmin.getEndTime());
            stmt.setString(8, courseByAdmin.getRoom());
            stmt.setDate(9, courseByAdmin.getStartDate());
            stmt.setDate(10, courseByAdmin.getEndDate());

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully inserted to CourseByAdmin table.");
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

    public boolean update(CourseByAdmin courseByAdmin) {
        System.out.println("Executing statement..");
        try {
            conn = connect();
            stmt = conn.prepareStatement(UPDATE);
            stmt.setString(1, courseByAdmin.getCourseTitle());
            stmt.setString(2, courseByAdmin.getDaysOfWeek());
            stmt.setTime(3, courseByAdmin.getStartTime());
            stmt.setTime(4, courseByAdmin.getEndTime());
            stmt.setString(5, courseByAdmin.getRoom());
            stmt.setDate(6, courseByAdmin.getStartDate());
            stmt.setDate(7, courseByAdmin.getEndDate());
            stmt.setLong(8, courseByAdmin.getAdminID());
            stmt.setString(9, courseByAdmin.getCourseCode());
            stmt.setString(10, courseByAdmin.getSemester());

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully updated to CourseByAdmin table.");
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

    public boolean checkClassTimeOverlap(CourseByAdmin courseByAdmin) {
        System.out.println("Executing statement...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(CHECK_CLASS_TIME_OVERLAP);
            stmt.setLong(1, courseByAdmin.getAdminID());
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
