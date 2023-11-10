package Bookmark;

import DTO.BookmarkGroupDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/updateBookmarkGroup")
public class UpdateBookmarkGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String order = req.getParameter("order");
        LocalDateTime curTime = LocalDateTime.now();
        String id = req.getParameter("id");


        BookmarkGroupDTO bookmarkGroupDTO = new BookmarkGroupDTO();
        bookmarkGroupDTO.setName(name);
        bookmarkGroupDTO.setOrder(Integer.parseInt(order));
        bookmarkGroupDTO.setUpdate_date(curTime);

        BookmarkService bookmarkService = new BookmarkService();
        int result = bookmarkService.updateBookmarkGroup(bookmarkGroupDTO, Integer.parseInt(id));

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
