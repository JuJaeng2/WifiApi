package Bookmark;

import DTO.BookmarkGroupDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/addBookmarkGroup")
public class AddBookmarkGroup extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        BookmarkGroupDTO newBookmark = new BookmarkGroupDTO();

        newBookmark.setName(req.getParameter("name"));
        newBookmark.setOrder(Integer.parseInt(req.getParameter("order")));
        LocalDateTime curTime = LocalDateTime.now();
        newBookmark.setRegister_date(curTime);

        BookmarkService bookmark = new BookmarkService();
        int result = bookmark.addBookmark(newBookmark);

        if (result == 1){
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("새로운 북마크 저장 성공");
        }else {
            resp.sendError(404, "새로운 북마크 저장 실패");
        }

    }
}
