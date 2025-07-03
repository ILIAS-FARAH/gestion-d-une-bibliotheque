package Controllers;

import Modeles.User;
import java.io.*;
import java.util.*;

public class UserControllerWriter {
	
	private static String filePath="users.csv";

    public static boolean addUser(User user) throws IOException {
        try (
        		FileWriter fw = new FileWriter(filePath, true); 
        		BufferedWriter bw = new BufferedWriter(fw);
        		PrintWriter pw = new PrintWriter(bw)) 
        {
				User user1= UserControllerReader.readLastUser();
				int c=0;
				c=user1.getId()+1;
				user.setId(c);
            String line = userToCsvLine(user);
            pw.println(line);
            List<User> users = UserControllerReader.readAllUsers();
            writeAllUsers(users);
            return true;
        }
    }
    public static boolean editUser(int userId, User updatedUser) throws IOException {
        List<User> users = UserControllerReader.readAllUsers();
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userId) {
                users.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllUsers(users);
        }
        return found;
    }
    public static boolean deleteUser(int userId) throws IOException {
    	List<User> users =  UserControllerReader.readAllUsers();
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userId) {
                users.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllUsers(users);
        }
        return found;
    }
    private static void writeAllUsers(List<User> users) throws IOException {
        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println("id,name,cin,email,password,salary,job,admin");
            for (User user : users) {
                pw.println(userToCsvLine(user));
            }
        }
    }
    private static String userToCsvLine(User user) {
        return user.getId() + "," +
                user.getName() + "," +
                user.getCin() + "," +
                user.getEmail() + "," +
                user.getPassword() + "," +
                user.getSalary() + "," +
                user.getJob() + "," +
                user.getAdmin();
    }
}
