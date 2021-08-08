package ServletDemo;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int height = 50;
        int width = 100;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setColor(Color.MAGENTA);
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
        String codeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder checkCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(codeChars.length());
            char c = codeChars.charAt(index);
            checkCode.append(c);
            g.drawString(c + "", 15 + 20 * i, 30);
        }
        HttpSession session = request.getSession();
        session.setAttribute("checkCode", checkCode.toString());
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}
