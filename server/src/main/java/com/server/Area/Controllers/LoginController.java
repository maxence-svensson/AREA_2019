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

public class LoginController {
    @ApiModelProperty(notes = "State of loging")
    private int log;
    @ApiModelProperty(notes = "Name of the user")
    private String name;
    public int id = 0;

    public LoginController(String name, String password, Connection c, PreparedStatement stmt) {
        System.out.println("Je me login ta mere --- " +name + " -- " + password);
        if (name != null && password != null) {
            this.log = User.logUser(name, password, c, stmt);
            if (this.log == 1)
                this.name = name;
            this.id = User.getUserIdByName(name, c, stmt);
        }
    }
    public int getLog() {
        return log;
    }
    public String getName() {
        return name;
    }
}