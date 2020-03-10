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


public class GoogleController {

    @ApiModelProperty(notes = "Google's Token")
    private String code;
    public int id = 0;

    public GoogleController(int Userid, String code, Connection c, PreparedStatement stmt) {
        String clientId = "377968007025-013sa07vehs51n1rau6qfmplp7esq964.apps.googleusercontent.com";
        String clientSecret = "dXw6n2fh3lNh6URBVW_0P6xO";

        String accessToken = getAccesTokenAuth(code, clientId, clientSecret);


        JSONObject datauser = getUserData(accessToken);

        String emailUser = (String) datauser.get("email");
        if (Userid == 0) {
            User.addUserService(emailUser, accessToken, "google", c, stmt);
            this.id = User.getUserIdByName(emailUser, c, stmt);
        } else
            User.updateTokenUser(Userid, accessToken, "google", c, stmt);
    }

    public JSONObject getUserData(String accessToken)
    {
        String data = null;
        JSONObject datauser = null;
        try {
            data = get(new StringBuilder("https://www.googleapis.com/oauth2/v1/userinfo?access_token=").append(accessToken).toString());

            // get the json
            try {
                datauser = (JSONObject) new JSONParser().parse(data);
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
            String body = post("https://accounts.google.com/o/oauth2/token", ImmutableMap.<String, String>builder()
                    .put("code", code)
                    .put("client_id", clientId)
                    .put("client_secret", clientSecret)
                    .put("redirect_uri", "http://localhost:8080/oauth2/callback/google")
                    .put("grant_type", "authorization_code").build());

            System.out.println(body);

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