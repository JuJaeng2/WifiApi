package Bookmark;

import DTO.BookmarkDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/addToBookmark")
public class AddToBookmark extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String bookmarkGroupId = req.getParameter("bgId");
        String wifiId = req.getParameter("wifiId");
        LocalDateTime curTime = LocalDateTime.now();

        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setBookmarkGroupId(Integer.parseInt(bookmarkGroupId));
        bookmarkDTO.setWifiId(wifiId);
        bookmarkDTO.setRegisterDate(curTime);


        BookmarkService service = new BookmarkService();
        int result = service.addToBookmark(bookmarkDTO);

        if (result == 1){
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("북마크 완료");
        }else {
            resp.sendError(404, "북마크 실패");
        }
    }
}

