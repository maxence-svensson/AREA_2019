package com.server.Area;


import java.sql.*;
import java.util.*;

import java.io.*;
import java.lang.*;

import com.server.Area.User;
import org.apache.http.client.methods.HttpPut;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.ApiModelProperty;

import java.net.URL;
import java.nio.charset.Charset;

//import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Base64Utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.ImmutableMap;
import twitter4j.Twitter;
import twitter4j.TwitterException;



public class Reactions {
    static String apiKeyOpenWhether = "f22fcf91b5ea6b50c3f3510082393fbd";
    static String twitchClientId = "riddoiwsiud1uyk92zkzwrdgipurqp";
    static String ApiKeyGoogle = "AIzaSyBnWft8Xhjk1T5Y4oyf2A5RhgbnSKzHv18";


    public static String getAccesTokenById(int userId, String type, Connection c, PreparedStatement stmt) {
        String accesToken = null;
        try {
            stmt = c.prepareStatement("SELECT " + type + "_token FROM  user_service_token WHERE id_user = '" + userId + "'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accesToken = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accesToken;
    }


    //// Return le dernier commits d'un Repo
    public static String githubGetLastCommitsRepo(int userId, String value, Connection c, PreparedStatement stmt) {
        String access_token = "token "+ getAccesTokenById(userId, "github", c, stmt);
        int count = 0;
        String sha = null;
        try {
            HttpGet url = new HttpGet("https://api.github.com/repos/"+ value + "/commits");
            url.addHeader("Authorization", access_token);
            JSONArray reponse = new JSONArray(execute(url));
            count = reponse.length();
            sha = reponse.getJSONObject(0).getString("sha");

        }  catch (IOException e) {
            System.out.println(e);
        }
        return sha;
    }

    /// REACTION add comment to a commit
    public static void githubPostComment(int userId, String value, Connection c, PreparedStatement stmt) {
        String[] value_split = value.split(":");
        String access_token = "token "+ getAccesTokenById(userId, "github", c, stmt);
        try {
            HttpPost url = new HttpPost("https://api.github.com/repos/" + value_split[0] + "/commits/" + githubGetLastCommitsRepo(userId, value, c, stmt) + "/comments");
            url.addHeader("Authorization", access_token);
            JSONObject countryObj = new JSONObject();
            countryObj.put("body", "Bon travail :)");
            StringEntity entity = new StringEntity(countryObj.toString());
            url.setEntity(entity);
            JSONObject reponse = new JSONObject(execute(url));
        }  catch (IOException e) {
            System.out.println(e);
        }
    }

    /// Get Device Id Spotify
    public static String spotifyGetDevice(int userId, Connection c, PreparedStatement stmt) {
        String access_token = "Bearer "+ getAccesTokenById(userId, "spotify", c, stmt);
        String id = null;
        try {
            HttpGet url = new HttpGet("https://api.spotify.com/v1/me/player/devices");
            url.addHeader("Authorization", access_token);
            JSONObject reponse = new JSONObject(execute(url));
            id = reponse.getJSONArray("devices").getJSONObject(0).getString("id");

        }  catch (IOException e) {
            System.out.println(e);
        }
        return id;
    }

    /// REACTION Augmenter
    public static void spotifyVolumeMax(int userId,  Connection c, PreparedStatement stmt) {
        String access_token = "Bearer "+ getAccesTokenById(userId, "spotify", c, stmt);
        try {
            HttpPut url = new HttpPut("https://api.spotify.com/v1/me/player/volume?volume_percent=100&device_id=" + spotifyGetDevice(userId, c, stmt));
            url.addHeader("Authorization", access_token);
            execute(url);
        }  catch (IOException e) {
            System.out.println(e);
        }
    }

    /// REACTION Pause
    public static void spotifyPause(int userId,  Connection c, PreparedStatement stmt) {
        String access_token = "Bearer "+ getAccesTokenById(userId, "spotify", c, stmt);
        try {
            HttpPut url = new HttpPut("https://api.spotify.com/v1/me/player/pause?device_id=" + spotifyGetDevice(userId, c, stmt));
            url.addHeader("Authorization", access_token);
            execute(url);
        }  catch (IOException e) {
            System.out.println(e);
        }
    }

    /// REACTION Next Track
    public static void spotifyNext(int userId,  Connection c, PreparedStatement stmt) {
        String access_token = "Bearer "+ getAccesTokenById(userId, "spotify", c, stmt);
        try {
            HttpPut url = new HttpPut("https://api.spotify.com/v1/me/player/next?device_id=" + spotifyGetDevice(userId, c, stmt));
            url.addHeader("Authorization", access_token);
            execute(url);
        }  catch (IOException e) {
            System.out.println(e);
        }
    }



    /// REACTION create Repo
    public static void githubCreateRepo(int userId, String repoName,  Connection c, PreparedStatement stmt) {
        String access_token = "token "+ getAccesTokenById(userId, "github", c, stmt);
        try {
            HttpPost url = new HttpPost("https://api.github.com/user/repos");
            url.addHeader("Authorization", access_token);
            JSONObject countryObj = new JSONObject();
            countryObj.put("name", repoName);
            StringEntity entity = new StringEntity(countryObj.toString());
            url.setEntity(entity);
            JSONObject reponse = new JSONObject(execute(url));
        }  catch (IOException e) {
            System.out.println(e);
        }
    }

    //// Return le dernier commits id
    public static int githubGetLastRepo(int userId, String usernameReponame, Connection c, PreparedStatement stmt) {
        String access_token = "token "+ getAccesTokenById(userId, "github", c, stmt);
        int count = 0;
        int Id = 0;
        try {
            HttpGet url = new HttpGet("https://api.github.com/repos/"+ usernameReponame + "/comments");
            url.addHeader("Authorization", access_token);
            JSONArray reponse = new JSONArray(execute(url));
            count = reponse.length();
            JSONObject test = (JSONObject) reponse.get(count - 1);
            Id = test.getInt("id");
        }  catch (IOException e) {
            System.out.println(e);
        }
        return Id;
    }

    /// REACTION add heart Emoji in comment
    public static void githubReactionComments(int userId, String value, Connection c, PreparedStatement stmt) {
        String access_token = "token "+ getAccesTokenById(userId, "github", c, stmt);
        try {
            HttpPost url = new HttpPost("https://api.github.com/repos/" + value + "/comments/" + githubGetLastRepo(userId, value, c, stmt) + "/reactions");
            url.addHeader("Authorization", access_token);
            url.addHeader("Accept", "application/vnd.github.squirrel-girl-preview+json");
            JSONObject countryObj = new JSONObject();
            countryObj.put("content", "heart");
            StringEntity entity = new StringEntity(countryObj.toString());
            url.setEntity(entity);
            JSONObject reponse = new JSONObject(execute(url));
        }  catch (IOException e) {
            System.out.println(e);
        }
    }

    /// REACTION add friend
    public static void youtubeReactionNewFriend(int userId, String channelId, Connection c, PreparedStatement stmt) {
        String access_token = "Bearer "+ getAccesTokenById(userId, "google", c, stmt);
        try {
            HttpPost url = new HttpPost("https://www.googleapis.com/youtube/v3/subscriptions?part=snippet");
            url.addHeader("Authorization", access_token);
            url.addHeader("Content-Type", "application/json");
            String json = "{\"snippet\": {\"resourceId\": {\"kind\": \"youtube#channel\",\"channelId\": \"" + channelId +"\"}}}";
            StringEntity entity = new StringEntity(json);
            url.setEntity(entity);
            JSONObject reponse = new JSONObject(execute(url));
        }  catch (IOException e) {
            System.out.println(e);
        }
    }

    // REACTION create tweet
    public static void twitterNewPost(Twitter twitter, String message) {
		try {
			twitter.updateStatus(message);
		} catch (TwitterException e) {
			System.out.println("Failed to post a tweet: " + e.getMessage());
		}
    }

    public static String getGmailCurrentEmailUser(String accessToken) {
        String data = null;
        JSONObject datauser = null;
        try {
            data = get(new StringBuilder("https://www.googleapis.com/oauth2/v1/userinfo?access_token=").append(accessToken).toString());

            // get the json
            try {
                datauser = new JSONObject(data);
            } catch (JSONException e) {
                throw new RuntimeException("Unable to parse json " + data);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return (String) datauser.get("email");
    }

    public static void gmailSendMail(int userId, String valueReaction, Connection c, PreparedStatement stmt) {
        String accessToken = getAccesTokenById(userId, "google", c, stmt);
        String userEmail = getGmailCurrentEmailUser(accessToken);

        String bytes2 = "From: maxence.svensson06@gmail.com\r\n" +
                "To: "+ valueReaction+"\r\n" +
                "Subject: Subject Example\r\n" +
                "This is content: hope you got it\r\n";

        String b642 = Base64Utils.encodeToString(String.format("%s", bytes2).getBytes());
        System.out.println("b642 "+b642);
        try {
            HttpPost url = new HttpPost("https://www.googleapis.com/gmail/v1/users/"+ userEmail +"/messages/send?key=" + ApiKeyGoogle +"");
            url.addHeader("Authorization", "Bearer " + accessToken);
            url.addHeader("Accept", "application/json");
            url.addHeader("Content-Type", "application/json");
            String json = "{\"raw\": \"" + b642 + "\"}";
            StringEntity entity = new StringEntity(json);
            url.setEntity(entity);
            JSONObject reponse = new JSONObject(execute(url));
        }  catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void putValue(int userId, int value, int idAction, Connection c, PreparedStatement stmt) {
        String accesToken = null;
        try {
            stmt = c.prepareStatement("INSERT INTO user_actions_reactions VALUES ('"+ userId + "','" + idAction + "','" + value + "','1','1')");
            ResultSet rs = stmt.executeQuery();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // makes a GET request to url and returns body as a string
    public static String get(String url) throws ClientProtocolException, IOException {
        return execute(new HttpGet(url));
    }

    // makes request and checks response code for 200
    private static String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() == 204)
            return "NULL";

        System.out.println("MA REQUETE : " + request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
            throw new RuntimeException("Expected 200 or 201 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
        }

        return body;
    }

}
