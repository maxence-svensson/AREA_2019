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

public class RegisterController {

    @ApiModelProperty(notes = "Name of the user")
    private String name;
    public int state = 0;
    public int id = 0;

    public RegisterController(String name, String password, Connection c, PreparedStatement stmt) {
        if (name != null && password != null) {
            this.name = name;
            state = User.addUser(name, password, c, stmt);
            this.id = User.getUserIdByName(name, c, stmt);
        }
    }

    public String getUserName() {
        return name;
    }
}