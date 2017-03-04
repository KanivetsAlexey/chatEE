package ChatClient;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    private static CookieManager cm;
    static{
        cm = new CookieManager();
        CookieHandler.setDefault(cm);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String login;
        String password;
        String room = Constants.MAIN_ROOM;
        try{
            do{
                System.out.println("Enter login: ");
                login = scanner.nextLine();
                System.out.println("Enter password: ");
                password = scanner.nextLine();
            }while(sendToServer("/login?login=" + login + "&password=" + password) != 200);

            Thread th = new Thread(new GetThread(cm));
            th.setDaemon(true);
            th.start();

            System.out.println("Enter message: ");

            while(true){
                String text = scanner.nextLine();

                if(text.isEmpty()){
                    break;
                }

                if(text.equals("-getUsers")){
                    int result =sendToServer("/getusers");
                    if(result != 200){
                        System.out.println("Error. code: "+result);
                    }
                    continue;
                }

                if(text.equals("-private")){
                    System.out.println("Enter user to direct:");
                    String to = scanner.nextLine();
                    System.out.println("Enter message:");
                    text = scanner.nextLine();
                    Message m = new Message(login, to, Constants.MAIN_ROOM, text);
                    int res = m.send(Utils.getURL() + "/add");
                    if(res != 200){
                        System.out.println("HTTP error occured: " + res);
                        return;
                    }
                    System.out.println("Message Send to user " + to);
                    continue;
                }

                if(text.equals("-enterRoom")){
                    System.out.println("Enter room Name: ");
                    room = scanner.nextLine();
                    int result =sendToServer("/room?room=" + room);
                    if(result != 200){
                        System.out.println("Error. code: " + result);
                    }else{
                        System.out.println("Enter to room: " + room);
                    }
                    continue;
                }

                if(text.equals("-exitRoom")){
                    int result =sendToServer("/room?room=exit");
                    if(result != 200){
                        System.out.println("Error. code: " + result);
                    }else{
                        System.out.println("Exit from room: " + room);
                    }
                    room = Constants.MAIN_ROOM;
                    continue;
                }

                Message m = new Message(login, Constants.TO_ALL, room, text);
                int res = m.send(Utils.getURL() + "/add");
                if(res != 200){
                    System.out.println("HTTP error occured: " + res);
                    return;
                }
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            scanner.close();
        }
    }

    public static int sendToServer(String param){
        try{
            URL url = new URL(Utils.getURL() + param);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            return http.getResponseCode();
        }catch(IOException e){
            e.printStackTrace();
        }
        return -1;
    }
}
