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


public class LinkedinController {

    @ApiModelProperty(notes = "Linkedin's Token")
    private String code;
    public int id = 0;
    public String clientId = "86yu19zq37j60p";
    public String clientSecret = "gMX9J4ZTlsmtsDtG";

    public LinkedinController(int Userid, String code, Connection c, PreparedStatement stmt) {

        String accessToken = getAccesTokenAuth(code);

        System.out.println("mon acces token linkedin : " + accessToken);

        User.updateTokenUser(Userid, accessToken, "linkedin", c, stmt);
    }

    public String getAccesTokenAuth(String code)
    {
        String accessToken = null;
        try {
            String body = post("https://www.linkedin.com/oauth/v2/accessToken", ImmutableMap.<String, String>builder()
                    .put("grant_type", "authorization_code")
                    .put("code", code)
                    .put("redirect_uri", "http://localhost:8080/oauth2/callback/linkedin")
                    .put("client_id", clientId)
                    .put("client_secret", clientSecret).build());
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