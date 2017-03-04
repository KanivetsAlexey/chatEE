import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Alexey on 01.03.2017
 */
public class LoginToChat extends HttpServlet {
    private UserList userList = UserList.getInstance();
    private MessageList msgList = MessageList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(userList.check(login, password)){
            Cookie cookieOne = new Cookie(Constants.USER, login);
            cookieOne.setMaxAge(60 * 60 * 8);
            resp.addCookie(cookieOne);
            Cookie cookieTwo = new Cookie(Constants.ROOM, Constants.MAIN_ROOM);
            cookieTwo.setMaxAge(60 * 60 * 8);
            resp.addCookie(cookieTwo);

            System.out.println("USER - " + login + " connected to chat");
            userList.getUserbyLogin(login).setOnline(true);
            HttpSession session = req.getSession(true);
            msgList.add(new Message(Constants.SERVER, login + " connected"));
            session.setAttribute(Constants.USER, userList.getUserbyLogin(login));
            resp.setContentType("Welcome to Chat server" + login);
            return;
        }else{
            if(!userList.isUser(login)){
                User usr = new User(login, password);
                userList.setUser(usr);

                Cookie cookieOne = new Cookie(Constants.USER, login);
                cookieOne.setMaxAge(60 * 60 * 8);
                resp.addCookie(cookieOne);
                Cookie cookieTwo = new Cookie(Constants.ROOM, Constants.MAIN_ROOM);
                cookieTwo.setMaxAge(60 * 60 * 8);
                resp.addCookie(cookieTwo);

                System.out.println("USER - " + login + " connected to chat");
                userList.getUserbyLogin(login).setOnline(true);
                HttpSession session = req.getSession(true);
                msgList.add(new Message(Constants.SERVER, login + " connected"));
                session.setAttribute(Constants.USER, userList.getUserbyLogin(login));
                resp.setContentType("Welcome to Chat server" + login);
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
