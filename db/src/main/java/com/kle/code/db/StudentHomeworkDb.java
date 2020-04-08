package com.kle.code.db;

import com.kle.code.util.SpringContextUtil;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class StudentHomeworkDb {

    private DatabasePool databasePool;

    public StudentHomeworkDb(){
        databasePool = (DatabasePool) SpringContextUtil.getApplicationContext().getBean("databasePool");
    }

    public Boolean addStudentHomework(String sid, String hid) {
        Statement statement = null;
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
            statement = connection.createStatement();
            String insertSqlString = "INSERT INTO student_homework (sid, hid) VALUES ('" +
                        sid + "', '" + hid + "')";
            return statement.executeUpdate(insertSqlString) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean submitHomework(String sid, String hid, String submitContent, Date submitTime) {
        String sqlString = "UPDATE student_homework SET `submit_content`='" + submitContent +
                "', `submit_time`='" + new Timestamp(submitTime.getTime()) + "' WHERE sid=" + sid + " AND hid=" + hid;
        Statement statement = null;
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
            statement = connection.createStatement();
            return statement.executeUpdate(sqlString) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<String, String> selectStudentHomeworkById(String sid, String hid) {
        ResultSet resultSet = null;
        Statement statement = null;
        String sqlString = "SELECT title, content, create_time, update_time, submit_content, submit_time " +
                "FROM student_homework, homework WHERE sid=" + sid + " AND student_homework.hid=" + hid + " " +
                "AND homework.hid=" + hid;
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            Map<String, String> map = new HashMap<String,String>();
            while (resultSet.next()){
                map.put("sid", sid);
                map.put("hid", hid);
                map.put("title", resultSet.getString("title"));
                map.put("content", resultSet.getString("content"));
                map.put("create_time", resultSet.getTimestamp("create_time").toString().
                        substring(0, resultSet.getTimestamp("create_time").toString().length() - 2));
                map.put("update_time", resultSet.getTimestamp("update_time").toString().
                        substring(0, resultSet.getTimestamp("update_time").toString().length() - 2));
                Timestamp submitTimestamp = resultSet.getTimestamp("submit_time");
                String submitTime = (submitTimestamp == null ? "" : submitTimestamp.toString().
                        substring(0, submitTimestamp.toString().length() - 2));
                String submitContent = (resultSet.getString("submit_content") == null
                        ? "" : resultSet.getString("submit_content"));
                map.put("submit_time", submitTime);
                map.put("submit_content", submitContent);
                return map;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, String>> getStudentHomeworkByHid(String hid) {
        ResultSet resultSet = null;
        Statement statement = null;
        List<Map<String, String>> list = new ArrayList<>();
        String sqlString = "SELECT sid, title, content, create_time, update_time, submit_content, submit_time " +
                "FROM homework, student_homework " +
                "WHERE homework.hid=" + hid + " AND student_homework.hid=" + hid;
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                Map<String, String> map = new HashMap<String,String>();
                map.put("sid", String.valueOf(resultSet.getInt("sid")));
                map.put("hid", hid);
                map.put("title", resultSet.getString("title"));
                map.put("content", resultSet.getString("content"));
                map.put("create_time", resultSet.getTimestamp("create_time").toString().
                        substring(0, resultSet.getTimestamp("create_time").toString().length() - 2));
                map.put("update_time", resultSet.getTimestamp("update_time").toString().
                        substring(0, resultSet.getTimestamp("create_time").toString().length() - 2));
                Timestamp submitTimestamp = resultSet.getTimestamp("submit_time");
                String submitTime = (submitTimestamp == null ? "" : submitTimestamp.toString().
                        substring(0, submitTimestamp.toString().length() - 2));
                String submitContent = (resultSet.getString("submit_content") == null
                        ? "" : resultSet.getString("submit_content"));
                map.put("submit_time", submitTime);
                map.put("submit_content", submitContent);
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, String>> getStudentHomeworkOfStudent(String sid) {
        String sqlString = "SELECT homework.hid, homework.title, homework.content, homework.create_time, " +
                "homework.update_time, student_homework.submit_content, student_homework.submit_time " +
                "FROM student_homework, homework " +
                "WHERE homework.hid=student_homework.hid AND student_homework.sid=" + sid;
        List<Map<String, String>> list = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()){
                Map<String, String> map = new HashMap<String,String>();
                map.put("sid", sid);
                map.put("hid", String.valueOf(resultSet.getInt("hid")));
                map.put("title", resultSet.getString("title"));
                map.put("content", resultSet.getString("content"));
                map.put("create_time", resultSet.getTimestamp("create_time").toString().
                        substring(0, resultSet.getTimestamp("create_time").toString().length() - 2));
                map.put("update_time", resultSet.getTimestamp("update_time").toString().
                        substring(0, resultSet.getTimestamp("create_time").toString().length() - 2));
                Timestamp submitTimestamp = resultSet.getTimestamp("submit_time");
                String submitTime = (submitTimestamp == null ? "" : submitTimestamp.toString().
                        substring(0, submitTimestamp.toString().length() - 2));
                String submitContent = (resultSet.getString("submit_content") == null
                        ? "" : resultSet.getString("submit_content"));
                map.put("submit_time", submitTime);
                map.put("submit_content", submitContent);
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


//    public static List<Student> getOtherStudent(String tid) {
//        Connection connection = DbUtil.getInstance().getConnection();
//        Statement statement = null;
//        ResultSet resultSet = null;
//        List<Student> studentList = new ArrayList<>();
//        String sqlString = "SELECT * FROM student " +
//                "WHERE sid not in (SELECT sid FROM teach WHERE tid=" + tid + ")";
//        try{
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(sqlString);
//            while (resultSet.next()){
//                Student student = new Student();
//                student.setSid(resultSet.getInt("sid"));
//                student.setName(resultSet.getString("name"));
//                student.setPassword(resultSet.getString("password"));
//                student.setCreateTime(resultSet.getTimestamp("create_time"));
//                student.setUpdateTime(resultSet.getTimestamp("update_time"));
//                studentList.add(student);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            DbUtil.getInstance().close(connection, statement, resultSet);
//        }
//        return studentList;
//    }

}
