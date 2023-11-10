package Bookmark;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;

@WebServlet("/deleteBookmarkList")
public class DeleteBookmarkList extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blId = req.getParameter("blId");

        BookmarkService service = new BookmarkService();
        int result = service.deleteBookmarkList(Integer.parseInt(blId));

        if(result == 1) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("북마크 삭제 성공");
        }else{
            resp.sendError(404);
            resp.getWriter().write("북마크 삭제 실패");
        }
    }
}
