const { google } = require('googleapis');
const express = require('express');
const OAuth2Data = require('./google_key.json');

const app = express();

const CLIENT_ID = "377968007025-013sa07vehs51n1rau6qfmplp7esq964.apps.googleusercontent.com";
const CLIENT_SECRET = "dXw6n2fh3lNh6URBVW_0P6xO";
const REDIRECT_URL = "http://localhost:8080/oauth2/callback/google";

const oAuth2Client = new google.auth.OAuth2(CLIENT_ID, CLIENT_SECRET, REDIRECT_URL);
var authed = false;
var my_id = null;

app.use(express.static('static'));

app.get('/home', (req, res) => {
    console.log("home page");
    my_id = req.query.id;
    console.log("du coup mon id -> " + my_id);
    res.sendFile('home.html', { root: __dirname});
});

app.get('/logout', (req, res) => {
    console.log("je suis logout");
    my_id = null;
    const url = "http://localhost:8080/logout";
    res.redirect(url);
});

app.get('/login', (req, res) => {
    console.log(Object.keys(req.query).length);
    if (Object.keys(req.query).length === 0)
        res.sendFile('login.html', { root: __dirname });
    else {
        console.log(req.query);
        var username = req.query.username;
        var pwd = req.query.pass;
        const url = "http://localhost:8080/login?name=" + username + "&pwd=" + pwd + "";
        res.redirect(url);
    }
});

app.get('/signup', (req, res) => {
    console.log(Object.keys(req.query).length);
    if (Object.keys(req.query).length === 1)
        res.sendFile('signup.html' /*+ res.query.value*/, { root: __dirname });
    else if (Object.keys(req.query).length === 0)
        res.sendFile('signup.html', { root: __dirname });
      else {
        console.log(req.query);
        var username = req.query.username;
        var pwd = req.query.pass;
        const url = "http://localhost:8080/register?name=" + username + "&pwd=" + pwd + "";
        res.redirect(url);
    }
});

app.get('/login/linkedin', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/linkedin";
    res.redirect(url);
});

app.get('/login/github', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/github";
    res.redirect(url);
});

app.get('/login/spotify', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/spotify";
    res.redirect(url);
});

app.get('/login/reddit', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/reddit";
    res.redirect(url);
});

app.get('/login/twitch', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/twitch";
    res.redirect(url);
});

app.get('/login/twitter', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/twitter";
    res.redirect(url);
});

app.get('/login/facebook', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/facebook";
    res.redirect(url);
});

app.get('/login/discord', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/discord";
    res.redirect(url);
});

app.get('/login/google', (req, res) => {
    const url = "http://localhost:8080/oauth2/autorize/google";
    res.redirect(url);
});

const port = process.env.port || 8081;
app.listen(port, () => console.log(`Server running at ${port}`));
