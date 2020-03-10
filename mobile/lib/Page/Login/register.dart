import 'package:mobile/Container/login_button.dart';
import 'package:flutter/material.dart';
import 'package:mobile/global.dart';


class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  @override
  void initState() {
     super.initState();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LoginScreen(),
    );
  }
}

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  Color backgroundColor1 = Colors.black87;
  final Color backgroundColor2 = Colors.grey;
  final Color highlightColor = Color(0x9900cc66);
  final Color foregroundColor = Colors.white;
  final AssetImage logo = AssetImage("assets/logo.png");

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: new BoxDecoration(
        gradient: new LinearGradient(
          begin: Alignment.centerLeft,
          end: new Alignment(1.0, 0.0),
          // 10% of the width, so there are ten blinds.
          colors: [this.backgroundColor1, this.backgroundColor2],
          // whitish to gray
          tileMode: TileMode.repeated, // repeats the gradient over the canvas
        ),
      ),
      height: MediaQuery
          .of(context)
          .size
          .height,
      child: Column(
        children: <Widget>[
          Container(
            padding: const EdgeInsets.only(top: 100.0, bottom: 50.0),
            child: Center(
              child: new Column(
                children: <Widget>[
                  Container(
                    height: 128.0,
                    width: 128.0,
                    decoration: BoxDecoration(
                        image: DecorationImage(image: this.logo)
                    ),
                  ),
                  new Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: new Text(
                      "AREA",
                      style: TextStyle(color: this.foregroundColor),
                    ),
                  )
                ],
              ),
            ),
          ),
          Container(
            width: MediaQuery
                .of(context)
                .size
                .width,
            margin: const EdgeInsets.only(left: 40.0, right: 40.0),
            alignment: Alignment.center,
            decoration: BoxDecoration(
              border: Border(
                bottom: BorderSide(
                    color: this.foregroundColor,
                    width: 0.5,
                    style: BorderStyle.solid),
              ),
            ),
            padding: const EdgeInsets.only(left: 0.0, right: 10.0),
            child: new Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                new Padding(
                  padding:
                  EdgeInsets.only(top: 10.0, bottom: 10.0, right: 00.0),
                  child: Icon(
                    Icons.alternate_email,
                    color: this.foregroundColor,
                  ),
                ),
                new Expanded(
                  child: TextField(
                    onChanged: (value) {setState(() { user.name = value; });},
                    textAlign: TextAlign.center,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: 'Username',
                      hintStyle: TextStyle(color: this.foregroundColor),
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: MediaQuery
                .of(context)
                .size
                .width,
            margin: const EdgeInsets.only(left: 40.0, right: 40.0, top: 10.0),
            alignment: Alignment.center,
            decoration: BoxDecoration(
              border: Border(
                bottom: BorderSide(
                    color: this.foregroundColor,
                    width: 0.5,
                    style: BorderStyle.solid),
              ),
            ),
            padding: const EdgeInsets.only(left: 0.0, right: 10.0),
            child: new Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                new Padding(
                  padding:
                  EdgeInsets.only(top: 10.0, bottom: 10.0, right: 00.0),
                  child: Icon(
                    Icons.lock_open,
                    color: this.foregroundColor,
                  ),
                ),
                new Expanded(
                  child: TextField(
                    onChanged: (value) {setState(() { user.pass = value; });},
                    obscureText: true,
                    textAlign: TextAlign.center,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: 'Password',
                      hintStyle: TextStyle(color: this.foregroundColor),
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: MediaQuery
                .of(context)
                .size
                .width,
            margin: const EdgeInsets.only(left: 40.0, right: 40.0, top: 30.0),
            alignment: Alignment.center,
            child: new Row(
              children: <Widget>[
                new Expanded(
                  child: new FlatButton(
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12.0)
                    ),
                    padding: const EdgeInsets.symmetric(
                        vertical: 20.0, horizontal: 20.0),
                    color: this.highlightColor,
                    onPressed: () => Navigator.of(context).pushReplacementNamed('/register/server'),
                    child: Text(
                      "Register",
                      style: TextStyle(color: this.foregroundColor),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}