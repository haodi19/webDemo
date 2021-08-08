package ServletDemo;

import cn.database.UserService;
import cn.database.UserServiceImpl;
import cn.domain.Page;
import cn.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/showUsersWithPagingServlet")
public class ShowUsersWithPagingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        UserService userService = new UserServiceImpl();
        String currentPage = request.getParameter("currentPage");
        String itemsPerPage = request.getParameter("itemsPerPage");
        if (currentPage == null || "".equals(currentPage)) {
            currentPage = "1";
        }
        if (itemsPerPage == null || "".equals(itemsPerPage)) {
            itemsPerPage = "5";
        }
        HttpSession session = request.getSession();
        String referer = request.getHeader("referer");
        boolean isFromLogin = referer != null && referer.contains("/login.jsp");
        Map<String, String[]> condition = isFromLogin ? null : request.getParameterMap();
        Map<String, String[]> _condition = new HashMap<>();
        if (condition != null) {
            for (Map.Entry<String, String[]> entry : condition.entrySet()) {
                _condition.put(entry.getKey(), entry.getValue());
            }
        }
        session.setAttribute("condition", _condition);
        Page<User> usersToShow = userService.getUsersWithPaging(Integer.parseInt(currentPage), Integer.parseInt(itemsPerPage), condition);
        session.setAttribute("UsersToShow", usersToShow);
        if (isFromLogin) {
            User user = (User) request.getAttribute("user");
            session.setAttribute("loginUser", user);
        }
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");
    }

}
