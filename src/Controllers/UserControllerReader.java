package Controllers;

import Modeles.User;
import java.io.*;
import java.util.*;

public class UserControllerReader {
	

	    
	public static String filePath="users.csv";

	    public static List<User> readAllUsers() throws IOException {
	        List<User> users = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                line = line.trim();
	                String[] fields = line.split(",");
	                if (line.isEmpty()||fields.length != 8) {
	                    continue; 
	                }
	                User user = parseCsvLineToUser(line);
	                users.add(user);
	            }
	        }
	        return users;
	    }

	    public static User findUserById(int id) throws IOException {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                line = line.trim();
	                String[] fields = line.split(",");
	                if (line.isEmpty()||fields.length != 8) {
	                    continue; 
	                }
	                User user = parseCsvLineToUser(line);
	                if (user.getId() == id) {
	                    return user;
	                }
	            }
	        }
	        
	        return null; 
	    }

	    public static User findUserByName(String name) throws IOException {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine();
	            while ((line = br.readLine()) != null) {
	                line = line.trim();
	                String[] fields = line.split(",");
	                if (line.isEmpty()||fields.length != 8) {
	                    continue; 
	                }
	                User user = parseCsvLineToUser(line);
	                if (user.getName().equalsIgnoreCase(name)) {
	                    return user;
	                }
	            }
	        }
	        return null; 
	    }
	    public static User findUserByCin(String cin) throws IOException {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                line = line.trim();
	                String[] fields = line.split(",");
	                if (line.isEmpty()||fields.length != 8) {
	                    continue; 
	                }
	                User user = parseCsvLineToUser(line);
	                if (user.getCin().equalsIgnoreCase(cin)) {
	                    return user;
	                }
	            }
	        }
	        return null; 
	    }
	    
	    public static List<User> findUserByCin2(String cin) throws IOException {
	        List<User> matchingUsers = new ArrayList<>(); 

	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                line = line.trim(); 
	                String[] fields = line.split(",");
	                if (line.isEmpty() || fields.length != 8) {
	                    continue; 
	                }
	                User user = parseCsvLineToUser(line);
	                if (user.getCin().toLowerCase().contains(cin.toLowerCase())) {
	                    matchingUsers.add(user); 
	                }
	            }
	        }

	        return matchingUsers; 
	    }
	    
	    public static List<User> findUserByName2(String name) throws IOException {
	        List<User> matchingUsers = new ArrayList<>(); // List to store matching users
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                line = line.trim(); 
	                String[] fields = line.split(",");
	                if (line.isEmpty() || fields.length != 8) {
	                    continue; 
	                }
	                User user = parseCsvLineToUser(line);
	                if (user.getName().toLowerCase().contains(name.toLowerCase())) {
	                    matchingUsers.add(user);
	                }
	            }
	        }
	        return matchingUsers;
	    }
	    public static User readLastUser() throws IOException {
	        User user = null;
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                line = line.trim();
	                String[] fields = line.split(",");
	                if (line.isEmpty()||fields.length != 8) {
	                    continue; 
	                }
	                user = parseCsvLineToUser(line);
	            }
	        }
			return user;
	    }
	    private static User parseCsvLineToUser(String line) {
	        String[] fields = line.split(",");
	        if (fields.length != 8) {
	            throw new IllegalArgumentException("Invalid CSV line format: " + line);
	        }
	        return new User(
	            Integer.parseInt(fields[0].trim()),  // id
	            fields[1].trim(),                   // name
	            fields[2].trim(),                   // cin
	            fields[3].trim(),                   // email
	            fields[4].trim(),                   // password
	            Float.parseFloat(fields[5].trim()), // salary
	            fields[6].trim(),                   // job
	            Integer.parseInt(fields[7].trim())  // admin
	        );
	    }
	}

