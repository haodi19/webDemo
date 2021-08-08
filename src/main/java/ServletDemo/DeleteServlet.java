package ServletDemo;

import cn.database.UserService;
import cn.database.UserServiceImpl;
import cn.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(value = "/deleteServlet")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int delete_id = Integer.parseInt(request.getParameter("delete_id"));
        String currentPage = request.getParameter("currentPage");
        UserService userService = new UserServiceImpl();
        boolean isDeleted = userService.deleteUser(delete_id);
        HttpSession session = request.getSession();
        Map<String, String[]> condition = (Map<String, String[]>) session.getAttribute("condition");
        String id = condition.get("id") == null ? "" : condition.get("id")[0];
        String username = condition.get("username") == null ? "" : condition.get("username")[0];
        String password = condition.get("password") == null ? "" : condition.get("password")[0];
        String gender = condition.get("gender") == null ? "" : condition.get("gender")[0];
        if (isDeleted) {
            request.getRequestDispatcher("/showUsersWithPagingServlet?currentPage=" + currentPage + "&itemsPerPage=5&id=" + id + "&username=" + username + "&password=" + password + "&gender=" + gender).forward(request, response);
        }
    }
}
