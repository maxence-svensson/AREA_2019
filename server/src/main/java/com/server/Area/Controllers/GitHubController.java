package com.server.Area;

import java.sql.*;
import java.util.Map;

import java.io.*;
import java.lang.*;

import com.server.Area.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.ImmutableMap;


public class GitHubController {

    @ApiModelProperty(notes = "Github's Token")
    private String code;
    public int id = 0;

    public GitHubController(int oldid, String code, Connection c, PreparedStatement stmt) {
        String clientId = "1b8ddffb28f26996c08f";
        String clientSecret = "6bd1a06369dc43d0a264847b8ab8ff4f11fb2a84";

        String accessToken = getAccesTokenAuth(code, clientId, clientSecret);

        System.out.println("mon acces token github : " + accessToken);

        JSONObject datauser = getUserData(accessToken);

        System.out.println(datauser);
        String emailUser = (String) datauser.get("email");
        System.out.println("mon old id " + oldid);
        if (oldid == 0) {
            User.addUserService(emailUser, accessToken, "github", c, stmt);
            this.id = User.getUserIdByName(emailUser, c, stmt);
        } else
            User.updateTokenUser(oldid, accessToken, "github", c, stmt);
    }

    public String getUserName(String accessToken)
    {
        String data = null;
        JSONObject datauser = null;
        String username = null;
        try {
            data = get(new StringBuilder("https://api.github.com/user?access_token=").append(accessToken).toString());
            try {
                datauser = (JSONObject) new JSONParser().parse(data);
                String type = (String) datauser.get("type");
                if (type.equals("User"))
                    username = (String) datauser.get("login");
            } catch (ParseException e) {
                throw new RuntimeException("Unable to parse json " + data);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return username;
    }

    public JSONObject getUserData(String accessToken)
    {
        String data = null;
        JSONObject datauser = null;
        String username = getUserName(accessToken);
        try {
            data = get(new StringBuilder("https://api.github.com/user/public_emails?&access_token=").append(accessToken).toString());
            String newstring = data.substring(data.indexOf("},{") + 2, data.length() - 1);
            // get the json
            try {
                datauser = (JSONObject) new JSONParser().parse(newstring);
            } catch (ParseException e) {
                throw new RuntimeException("Unable to parse json " + data);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return datauser;
    }

    public String getAccesTokenAuth(String code, String clientId, String clientSecret)
    {
        String accessToken = null;
        try {
            String body = post("https://github.com/login/oauth/access_token", ImmutableMap.<String, String>builder()
                    .put("client_id", clientId)
                    .put("client_secret", clientSecret)
                    .put("code", code)
                    .put("Accept", "application/json")
                    .put("redirect_uri", "http://localhost:8080/oauth2/callback/github").build());
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) new JSONParser().parse(body);
            } catch (ParseException e) {
                throw new RuntimeException("Unable to parse json " + body);
            }
            accessToken = (String) jsonObject.get("access_token");
        } catch (IOException e) {
            System.out.println(e);
        }
        return accessToken;
    }

    // makes a GET request to url and returns body as a string
    public String get(String url) throws ClientProtocolException, IOException {
        return execute(new HttpGet(url));
    }


    public String post(String url, Map<String,String> formParameters) throws ClientProtocolException, IOException {
        HttpPost request = new HttpPost(url);
        request.addHeader("Accept", "application/json");


        List <NameValuePair> nvps = new ArrayList <NameValuePair>();

        for (String key : formParameters.keySet()) {
            nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
        }

        request.setEntity(new UrlEncodedFormEntity(nvps));

        return execute(request);
    }

    // makes request and checks response code for 200
    private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        System.out.println("MA REQUETE : " + request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
        }

        return body;
    }

    public String getCode() { return code; }
    public int getId() { return id; }
}