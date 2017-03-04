import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alexey on 28.02.2017
 */
public class GetListUserOnline extends HttpServlet {
    private  UserList userList = UserList.getInstance();
    private MessageList msgList = MessageList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals(Constants.USER)){
                    if(userList.getUserbyLogin(cookie.getValue()) != null){
                        String usersonline = userList.getOnlineUsersStr();
                        if(usersonline == null){
                            msgList.add(new Message(Constants.SERVER, cookie.getValue(), Constants.MAIN_ROOM, "No logined users"));
                        }else{
                            msgList.add(new Message(Constants.SERVER, cookie.getValue(), Constants.MAIN_ROOM, "Users online: " + usersonline));
                        }
                        return;
                    }
                }
            }
        }
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
