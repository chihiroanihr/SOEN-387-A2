package com.soen387.daoImpl;

import com.soen387.dao.AdminDao;
import com.soen387.beans.Admin;

import java.sql.*;

import static com.soen387.db.db_connection.*;

public class AdminDaoImpl implements AdminDao {
    // SQL Statements

    //Finds an Admin record from Admin table
    private static final String FIND_BY_ID = "SELECT * FROM Admin WHERE adminID = ?;";

    //Inserts an Admin record into Admin table
    private static final String INSERT = "INSERT INTO Admin (adminID) VALUES(?);";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        long adminId = rs.getLong("adminId");

        return new Admin(adminId);
    }

    //Checks if an admin exists in the Admin table
    public boolean checkIsAdmin(long adminId) {
        System.out.println("Executing checkIsAdmin method...");
        try {
            conn = connect();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, adminId);

            rs = stmt.executeQuery();
            return rs.next(); // this returns true if row exists else false

        } catch (SQLException error) {
            throw new RuntimeException(error);
        } finally {
            close(rs, stmt, conn);
        }
    }

    //Inserts an admin record into Admin table
    public boolean insert(Admin admin) {
        System.out.println("Executing insert(Admin) method..");
        try {
            conn = connect();
            stmt = conn.prepareStatement(INSERT);
            stmt.setLong(1, admin.getAdminId());

            int i = stmt.executeUpdate();

            if (i == 1) {
                System.out.println("Data successfully inserted to Admin table.");
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
