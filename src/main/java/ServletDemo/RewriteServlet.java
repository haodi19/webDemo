package ServletDemo;

import cn.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(value = "/rewriteServlet")
public class RewriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        User originalUser=new User(Integer.parseInt(id),username,password, gender.equals("MALE") ? User.Gender.MALE: User.Gender.FEMALE);
        HttpSession session = request.getSession();
        session.setAttribute("originalUser",originalUser);
        response.sendRedirect(request.getContextPath()+"/update.jsp");
    }
}
