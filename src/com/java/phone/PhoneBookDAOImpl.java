package com.java.phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PhoneBookDAOImpl implements PhoneBookDAO {

    @Override
    public List<PhoneBookVO> getList() {
        List<PhoneBookVO> phoneBookVOList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM phone_book ORDER BY id ASC";
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                phoneBookVOList.add(new PhoneBookVO(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
            }


        }
        return phoneBookVOList;
    }

    @Override
    public List<PhoneBookVO> searchPhoneBook(String name) {
        List<PhoneBookVO> phoneBookVOList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM PHONE_BOOK WHERE name LIKE ? OR name LIKE ? OR name LIKE ?";

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "%" + name);
            preparedStatement.setString(3,  name + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                phoneBookVOList.add(new PhoneBookVO(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("hp"),
                        resultSet.getString("tel")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
            }
        }
        return phoneBookVOList;
    }

    @Override
    public boolean insert(PhoneBookVO phoneBookVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int insertedCount = 0;

        String sqlQuery = "INSERT INTO PHONE_BOOK (id, name, hp, tel) VALUES (seq_phone_book.NEXTVAL, ?, ?, ?)";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, phoneBookVO.getName());
            pstmt.setString(2, phoneBookVO.getHp());
            pstmt.setString(3, phoneBookVO.getTel());

            insertedCount = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
        return insertedCount == 1;
    }

    @Override
    public boolean delete(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int deletedCount = 0;

        String sqlQuery = "DELETE FROM PHONE_BOOK WHERE id = ?";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, id);

            deletedCount = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
        return deletedCount == 1;
    }

//    @Override
//    public boolean update(PhoneBookVO phoneBookVO) {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        int updatedCount = 0;
//
//        String sqlQuery = "UPDATE PHONE_BOOK SET name = ?, hp = ?, tel = ? WHERE id = ?";
//
//        try {
//            conn = DBConnection.getConnection();
//            pstmt = conn.prepareStatement(sqlQuery);
//            pstmt.setString(1, phoneBookVO.getName());
//            pstmt.setString(2, phoneBookVO.getHp());
//            pstmt.setString(3, phoneBookVO.getTel());
//            pstmt.setInt(4,phoneBookVO.getId());
//
//            updatedCount = pstmt.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (pstmt != null) pstmt.close();
//                if (conn != null) conn.close();
//            } catch (Exception e) {
//            }
//        }
//        return updatedCount == 1;
//    }
}
