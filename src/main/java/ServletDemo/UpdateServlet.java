package ServletDemo;

import cn.database.UserService;
import cn.database.UserServiceImpl;
import cn.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/updateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String update_id = request.getParameter("update_id");
        String update_username = request.getParameter("update_username");
        String update_password = request.getParameter("update_password");
        String update_gender = request.getParameter("update_gender");
        String currentPage = request.getParameter("currentPage");
        User user = new User(Integer.parseInt(update_id), update_username, update_password, "male".equals(update_gender) ? User.Gender.MALE : User.Gender.FEMALE);
        UserService userService = new UserServiceImpl();
        userService.updateUser(user);
        HttpSession session = request.getSession();
        Map<String, String[]> condition = (Map<String, String[]>) session.getAttribute("condition");
        String id = condition.get("id") == null ? "" : condition.get("id")[0];
        String username = condition.get("username") == null ? "" : condition.get("username")[0];
        String password = condition.get("password") == null ? "" : condition.get("password")[0];
        String gender = condition.get("gender") == null ? "" : condition.get("gender")[0];
        request.getRequestDispatcher("/showUsersWithPagingServlet?currentPage=" + currentPage + "&itemsPerPage=5&id=" + id + "&username=" + username + "&password=" + password + "&gender=" + gender).forward(request, response);
    }
}
