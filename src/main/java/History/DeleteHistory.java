package History;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/deleteHistory")
public class DeleteHistory extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

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
            String sql = "delete from location_history where id = " + id;
            int result = statement.executeUpdate(sql);
            if (result > 0){
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("DELETE 요청 성공");
            }else {
                resp.sendError(404, "Delete 요청 실패");
            }

            System.out.println("히스토리 삭제 성 ");
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



    }
}
