package com.server.Area;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Map;

import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

public class User {

    private String name;
    private String email;
    private String token_google;

    private static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static void addTokenToUser(int idUser, String accessToken, String type, Connection c,  PreparedStatement stmt) {
        try {
            stmt = c.prepareStatement("INSERT INTO user_service_token (id_user, " + type + "_token) VALUES (?, ?);");
            stmt.setString(1, Integer.toString(idUser));
            stmt.setString(2, accessToken);
            stmt.execute();
            System.out.println("Utilisateur: " + idUser + " s'ajoute a la table des token pour le type -> " + type + "");
        } catch (Exception e) {
            System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );
        }
        return;
    }

    public static void updateTokenUser(int idUser, String accessToken, String type, Connection c,  PreparedStatement stmt) {
        try {
            stmt = c.prepareStatement("UPDATE user_service_token SET " + type + "_token = '" + accessToken + "' WHERE id_user = '" + idUser + "';");
            stmt.execute();
            System.out.println("Utilisateur: " + idUser + " from  " + type + " vient de s'update dans la table des tokens");
        } catch (Exception e) {
            System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );
        }
    }

    public static void addUserService(String email, String accesToken, String type, Connection c,  PreparedStatement stmt) {
        try {
            // check si il existe deja dans la table users
            stmt = c.prepareStatement("SELECT name, name FROM users WHERE name = '" + email + "' AND type = '"+ type +"';");
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                try {
                    // cree dans la table users
                    stmt = c.prepareStatement("INSERT INTO users (name, password, type) VALUES (?, ?, ?);");
                    stmt.setString(1, email);
                    stmt.setString(2, "null");
                    stmt.setString(3, type);
                    stmt.execute();
                    System.out.println("Utilisateur: " + email + " from  " + type + "  vient de s'inscrire");

                    // ajoute l'user dans la table des token et add le toekn correspondant
                    int id = getUserIdByName(email, c, stmt);
                    addTokenToUser(id, accesToken, type, c, stmt);
                } catch (Exception e) {
                    System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );
                }
            } else {
                // met a jour le token si il existe deja
                try {
                    int id = getUserIdByName(email, c, stmt);

                    updateTokenUser(id, accesToken, type, c, stmt);

                } catch (Exception e) {
                    System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );

                }
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );
        }

    }

    public static String getUserNameById(int Id, Connection c, PreparedStatement stmt) {
        String name = null;
        try {
            stmt = c.prepareStatement("SELECT name from users where id = '" + Id + "'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                name = rs.getString(1);
        } catch (Exception e) {System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );}
        return name;
    }

    public static int getUserIdByName(String name, Connection c, PreparedStatement stmt) {
        int id = 0;
        try {
            stmt = c.prepareStatement("SELECT id from users where name = '" + name + "'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                id = rs.getInt(1);
        } catch (Exception e) {System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );}
        return id;
    }

    public static int addUser(String name, String password, Connection c, PreparedStatement stmt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            //check si l'utilisateur existe deja
            stmt = c.prepareStatement("SELECT name, name FROM users WHERE name = '" + name + "' AND type = 'basic';");
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return 1;
            // l'ajoute en db si il n'existe pas
            stmt = c.prepareStatement("INSERT INTO users (name, password, type) VALUES (?, ?, ?);");
            stmt.setString(1, name);
            stmt.setString(2, toHexString(hash));
            stmt.setString(3, "basic");
            stmt.execute();
            System.out.println("Utilisateur: " + name + " vient de s'inscrire");

            // add user to table token
            int id = getUserIdByName(name, c, stmt);
            stmt = c.prepareStatement("INSERT INTO  user_service_token (id_user) VALUES (?);");
            stmt.setString(1, Integer.toString(id));
            stmt.execute();
            System.out.println("Utilisateur: " + name + " s'ajoute a la table des token");
        } catch (Exception e) {System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );}
        return 0;
    }

    public static int logUser(String name, String password, Connection c, PreparedStatement stmt) {

        String check_name = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            stmt = c.prepareStatement("SELECT name, name FROM users WHERE name = '" + name + "' AND password = '" + toHexString(hash) + "' and type = 'basic';");
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                return 0;
            return 1;
        } catch (Exception e) {System.out.println(e.getClass().getName()+": " + e.getLocalizedMessage() );}
        return 1;
    }
}
