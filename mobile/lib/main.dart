import 'package:mobile/Page/Home/home.dart';
import 'package:mobile/Page/Home/navigation_bar.dart';
import 'package:mobile/Page/Login/error.dart';
import 'package:mobile/Page/Login/login.dart';
import 'package:flutter/material.dart';
import 'package:mobile/Page/Login/register.dart';
import 'package:mobile/Page/Login/webview.dart';
import 'package:mobile/Page/Notification/notification.dart';
import 'package:mobile/global.dart';

import 'Page/Login/webview.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        primaryColor: Colors.blue,
        accentColor: Colors.white,
        hintColor: Colors.white,
      ),
      initialRoute: '/',
      routes: {
        '/login': (context) => LoginPage(),
        '/login/error': (context) => ErrorPage(),
        '/register': (context) => RegisterPage(),
        '/login/server': (context) => WebView('http://localhost:8080/login?name=${user.name}&pwd=${user.pass}', 'Connection AREA', ''),
        '/register/server': (context) => WebView('http://localhost:8080/register?name=${user.name}&pwd=${user.pass}', 'Connection AREA', ''),
        '/logout/server': (context) => WebView('http://localhost:8080/logout', 'Deconnection', '', root: '/login',),
        '/login/google': (context) => WebView('http://localhost:8080/oauth2/autorize/google', 'Connection GOOGLE', 'google'),
        '/logout/google': (context) => WebView('http://localhost:8080/oauth2/logout/google?userid=${user.id}', 'Deconnection GOOGLE', 'google', isNotLog: false,),
        '/login/github': (context) => WebView('http://localhost:8080/oauth2/autorize/github', 'Connection GITHUB', 'github'),
        '/logout/github': (context) => WebView('http://localhost:8080/oauth2/logout/github?userid=${user.id}', 'Deconnection GITHUB', 'github', isNotLog: false,),
        '/login/spotify': (context) => WebView('http://localhost:8080/oauth2/autorize/spotify', 'Connection SPOTIFY', 'spotify'),
        '/logout/spotify': (context) => WebView('http://localhost:8080/oauth2/logout/spotify?userid=${user.id}', 'Deconnection SPOTIFY', 'spotify', isNotLog: false,),
        '/login/linkedin': (context) => WebView('http://localhost:8080/oauth2/autorize/linkedin', 'Connection LINKEDIN', 'linkedin'),
        '/logout/linkedin': (context) => WebView('http://localhost:8080/oauth2/logout/linkedin?userid=${user.id}', 'Deconnection LINKEDIN', 'linkedin', isNotLog: false,),
        '/login/twitter': (context) => WebView('http://localhost:8080/oauth2/autorize/twitter', 'Connection TWITTER', 'twitter'),
        '/logout/twitter': (context) => WebView('http://localhost:8080/oauth2/logout/twitter?userid=${user.id}', 'Deconnection TWITTER', 'twitter', isNotLog: false,),
        '/login/facebook': (context) => WebView('http://localhost:8080/oauth2/autorize/facebook', 'Connection FACEBOOK', 'facebook'),
        '/logout/facebook': (context) => WebView('http://localhost:8080/oauth2/logout/facebook?userid=${user.id}', 'Deconnection FACEBOOK', 'facebook', isNotLog: false,),
        '/login/twitch': (context) => WebView('http://localhost:8080/oauth2/autorize/twitch', 'Connection TWITCH', 'twitch'),
        '/logout/twitch': (context) => WebView('http://localhost:8080/oauth2/logout/twitch?userid=${user.id}', 'Deconnection TWITCH', 'twitch', isNotLog: false,),
        '/login/discord': (context) => WebView('http://localhost:8080/oauth2/autorize/discord', 'Connection DISCORD', 'discord'),
        '/logout/discord': (context) => WebView('http://localhost:8080/oauth2/logout/discord?userid=${user.id}', 'Deconnection DISCORD', 'discord', isNotLog: false,),
        '/login/navbar': (context) => BottomBarPage(),
        '/login/home': (context) => HomePage(),
        '/login/home/notification' : (context) => NotificationPage(),
      },
      home: LoginPage(),
    );
  }
}
