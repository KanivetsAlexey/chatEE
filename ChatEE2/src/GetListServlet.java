import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetListServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();
    private UserList userList = UserList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String user = getCookiesbyName(Constants.USER, req);
        if((user == null)){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if(userList.getUserbyLogin(user) != null){
            String room=getCookiesbyName(Constants.ROOM, req);
            String fromStr = req.getParameter("from");
            int from = 0;
            try{
                from = Integer.parseInt(fromStr);
            }catch(Exception ex){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            String json = msgList.toJSON(from, user, room);
            if(json != null){
                OutputStream os = resp.getOutputStream();
                byte[] buf = json.getBytes(StandardCharsets.UTF_8);
                os.write(buf);
            }
            return;
        }
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    public String getCookiesbyName(String param, HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if(cookies == null){
            return null;
        }
        for(Cookie cookie: cookies){
            if(cookie.getName().equals(param)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
