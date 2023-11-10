package Bookmark;

import DTO.BookmarkGroupDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editBookmark")
public class EditBookmark extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookmarkGroupDTO newBookmark = new BookmarkGroupDTO();

        newBookmark.setName(req.getParameter("name"));
        newBookmark.setName(req.getParameter("order"));

        BookmarkService bookmark = new BookmarkService();
        int result =bookmark.addBookmark(newBookmark);

    }
}
