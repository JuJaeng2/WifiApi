package Bookmark;

import DTO.BookmarkDTO;
import DTO.BookmarkGroupDTO;

import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class BookmarkService{

    protected int addBookmark(BookmarkGroupDTO newBookmark){

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
        int result;

        try{
            connection = DriverManager.getConnection(url, userId, password);
            String sql = "insert into bookmark_list(name, `order`, register_date, update_date) VALUES (?, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newBookmark.getName());
            preparedStatement.setInt(2, newBookmark.getOrder());
            preparedStatement.setObject(3, newBookmark.getRegister_date());
            preparedStatement.setObject(4,newBookmark.getUpdate_date());

            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow > 0) {
                System.out.println("새로운 북마크 저장 성공");
            }else {
                System.out.println("새로운 북마크 저장 실패");
            }

            result = 1;

        }catch(SQLException e){
            result = 0;
            e.printStackTrace();
        }finally {
            try{
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try{
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }

    protected int deleteBookmarkGroup(int id){
        int result = 0;

        String url = "jdbc:mariadb://localhost:3306/mission1db";
        String userId = "mission1";
        String password = "mission1";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        Statement statement = null;

        try{
            connection = DriverManager.getConnection(url, userId, password);
            statement = connection.createStatement();
            String sql1= "delete from bookmark_list where id='" + id + "'";
            String sql2 = "delete from bookmark where bl_id='" + id + "'";
            statement.executeUpdate(sql2);
            result = statement.executeUpdate(sql1);

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(statement != null && !statement.isClosed()){
                    statement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            
            try{
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


        return result;
    }

    protected int updateBookmarkGroup(BookmarkGroupDTO bookmarkGroupDTO, int id){
        int result = 0;

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

        try{
            connection = DriverManager.getConnection(url, userId, password);
            String sql = "update bookmark_list set name=?, `order`=?, update_date=? " +
                    "where id=" + id;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookmarkGroupDTO.getName());
            preparedStatement.setInt(2, bookmarkGroupDTO.getOrder());
            preparedStatement.setObject(3, bookmarkGroupDTO.getUpdate_date());

            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow > 0) {
                System.out.println("북마크 업데이트 성공");
                result = 1;
            }else {
                System.out.println("북마크 업데이트 실패");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try{
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


        return result;
    }

    protected int addToBookmark(BookmarkDTO bookmarkDTO){

        int result = 0;

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

        try {
            connection = DriverManager.getConnection(url, userId, password);
            String sql = "insert into bookmark(bl_id, X_SWIFI_MGR_NO, register_date) " +
                    "values(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookmarkDTO.getBookmarkGroupId());
            preparedStatement.setString(2, bookmarkDTO.getWifiId());
            preparedStatement.setObject(3, bookmarkDTO.getRegisterDate());

            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow > 0){
                result = 1;
                System.out.println("북마크 성공");
            }else {
                System.out.println("북마크 실패");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(preparedStatement != null & !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try{
                if(preparedStatement != null & !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


        return result;
    }

    protected int deleteBookmarkList(int blId){
        int result = 0;

        String url = "jdbc:mariadb://localhost:3306/mission1db";
        String userId = "mission1";
        String password = "mission1";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        Statement statement = null;

        try{
            connection = DriverManager.getConnection(url, userId, password);
            statement = connection.createStatement();
            String sql = "delete from bookmark where id = " + blId;
            int affetedRow = statement.executeUpdate(sql);

            if (affetedRow > 0){
                System.out.println("북마크 삭제 완료");
                result = 1;
            }else{
                System.out.println("북마크 삭제 실패");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(statement != null && !statement.isClosed()){
                    statement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try{
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }
}
