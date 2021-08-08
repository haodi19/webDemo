package ServletDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = this.getServletContext();
        String filename = request.getParameter("filename");
        FileInputStream is=new FileInputStream(sc.getRealPath("/image/"+filename));
        response.setContentType(sc.getMimeType(filename));
        response.setHeader("content-disposition","attachment;filename="+filename);
        ServletOutputStream os = response.getOutputStream();
        int len=0;
        byte[]buff=new byte[1024];
        while ((len=is.read(buff))!=-1){
            os.write(buff,0,len);
        }
        is.close();


    }
}
