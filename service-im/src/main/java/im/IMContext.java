package im;


import im.model.User;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 全局上下文对象
 */
public class IMContext {
    public static Hashtable<String, User> onlineUsers = new Hashtable<>();


    public static List<User> getUserList(){
        List<User> list = new ArrayList<>();
        onlineUsers.forEach((uid,user)->{
            list.add(user);
        });
        return list;
    }
}
