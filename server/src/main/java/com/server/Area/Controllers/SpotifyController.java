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

import org.springframework.util.Base64Utils;


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


public class SpotifyController {

    @ApiModelProperty(notes = "Google's Token")
    private String code;
    private int id = 0;
    public String clientId = "b348a012872f4fe78567e7cea9e20c7c";
    public String clientSecret = "d9b6729abb1047889404aa335869e36f";

    public SpotifyController(int Userid, String code, Connection c, PreparedStatement stmt) {

        String accessToken = getAccesTokenAuth(code, clientId, clientSecret);

        System.out.println("mon acces token spotify : " + accessToken);

        User.updateTokenUser(Userid, accessToken, "spotify", c, stmt);
    }

    public String getAccesTokenAuth(String code, String clientId, String clientSecret)
    {
        String accessToken = null;
        try {
            String body = post("https://accounts.spotify.com/api/token", ImmutableMap.<String, String>builder()
                    .put("code", code)
                    .put("grant_type", "authorization_code")
                    .put("redirect_uri", "http://localhost:8080/oauth2/callback/spotify").build());
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) new JSONParser().parse(body);
            } catch (ParseException e) {
                throw new RuntimeException("Unable to parse json " + body);
            }
            System.out.println(jsonObject);
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
        String authString = "Basic " + Base64Utils.encodeToString(String.format("%s:%s", clientId,clientSecret).getBytes());
        request.addHeader("Authorization", authString);


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