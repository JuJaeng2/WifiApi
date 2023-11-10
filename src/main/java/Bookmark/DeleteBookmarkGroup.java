package Bookmark;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteBookmarkGroup")
public class DeleteBookmarkGroup extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        BookmarkService service = new BookmarkService();

        int result = service.deleteBookmarkGroup(Integer.parseInt(id));

        if (result > 0){
            System.out.println("북마크 그룹 삭제 성공");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("북마크 그룹 삭제 성공");
        }else {
            System.out.println("북마크 그룹 삭제 실패");
            resp.sendError(404, "북마크 그룹 삭제 실패");
        }

    }
}
