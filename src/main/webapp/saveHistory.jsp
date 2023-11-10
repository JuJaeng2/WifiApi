<%@ page import="java.sql.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String lnt = request.getParameter("lnt");
    String lat = request.getParameter("lat");

    String url = "jdbc:mariadb://localhost:3306/mission1db";
    String userId = "mission1";
    String password = "mission1";

    try {
        Class.forName("org.mariadb.jdbc.Driver");
    }catch (ClassNotFoundException e){
        e.printStackTrace();
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    LocalDateTime curTime = LocalDateTime.now();

    try {
        connection = DriverManager.getConnection(url, userId, password);
        String sql = "insert into location_history " +
                " (X_coordinate, Y_coordinate, search_date) " +
                " values (?,?,?) ";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, lat);
        preparedStatement.setString(2, lnt);
        preparedStatement.setString(3, String.valueOf(curTime));

        int affectedRow = preparedStatement.executeUpdate();

        if(affectedRow > 0) {
            System.out.println("history 저장 성공");
        }else{
            System.out.println("history 저장 실패");
        }

    }catch (SQLException e){
        e.printStackTrace();
    }finally {
        try {
            if (preparedStatement != null && !preparedStatement.isClosed()){
                preparedStatement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

%>






















