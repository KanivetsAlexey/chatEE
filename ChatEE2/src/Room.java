import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alexey on 28.02.2017
 */
public class Room extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();

        if (cookies != null){
            for (Cookie cookie: cookies){
                if (cookie.getName().equals(Constants.USER)){
                    String room = req.getParameter(Constants.ROOM);
                    if (room != null){
                        if (room.equals("exit")){
                            Cookie cook = new Cookie(Constants.ROOM, Constants.MAIN_ROOM);
                            resp.addCookie(cook);
                            return;
                        }else{
                            Cookie cook = new Cookie(Constants.ROOM, room);
                            resp.addCookie(cook);
                            return;
                        }
                    }
                }
            }
        }
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
