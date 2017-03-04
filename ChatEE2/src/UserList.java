import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 28.02.2017
 */
public class UserList {
    private static UserList userList = new UserList();
    private static List<User> users = new ArrayList<>();
    static {
        users.add(new User("Alexey", "Kanevets"));
        users.add(new User("test", "test"));
    }

    private UserList() {
    }

    public static UserList getInstance() {
        return userList;
    }

    public boolean check(String login, String password){
        for (User user: users){
            if (user.getLogin().equals(login)&& user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public boolean isUser(String login){
        for (User user: users){
            if (user.getLogin().equals(login)) return true;
        }
        return false;
    }

    public void setUser(User user){
        UserList.users.add(user);
    }

    public User getUserbyLogin(String login){
        for (User user: users){
            if (user.getLogin().equals(login)){
                return user;
            }
        }
        return null;
    }

    public String getOnlineUsersStr(){
        String result = "";
        for (User user: users){
            if (user.isOnline())result+=user.getLogin() + " ";
        }
        if (result.equals("")){
            return null;
        }else{
            return result;
        }
    }
}
