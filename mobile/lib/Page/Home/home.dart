import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/Container/action_bloc.dart';
import 'package:mobile/Container/login_button.dart';
import 'package:mobile/Container/logout.dart';
import 'package:mobile/global.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  double _height;

  @override
  void initState() {
    print("IDDDDDDD ${user.id}");
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    _height = MediaQuery
        .of(context)
        .size
        .height;
    return Scaffold(
      body: Container(
        color: Colors.black87,
        child: Stack(
          children: <Widget>[
            Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                LogOut(),
                Container(
                  height: MediaQuery.of(context).size.height * .8,
                  child: service(),
                ),
                FloatingActionButton.extended(
                  label: (!isOpen) ? Text("Get more") : Text("Close"),
                  onPressed: () {
                    setState(() {
                      isOpen = !isOpen;
                    });
                  },
                ),
              ],
            ),
            AnimatedPositioned(
              // Use the properties stored in the State class.
                top: (isOpen) ? (_height - (_height * .6)) : _height,
                bottom: 0,
                left: 0,
                right: 0,
                duration: Duration(seconds: 1),
                curve: Curves.fastOutSlowIn,
                child: Container(
                  color: Colors.blue,
                  child: SingleChildScrollView(
                    child: signIn(),
                  ),
                )
            ),
          ],
        ),
      ),
    );
  }
  Widget signIn() {
    return Column(
      children: <Widget>[
        Center(child: IconButton(
          icon: Icon(Icons.keyboard_arrow_down),
          onPressed: () {setState(() {isOpen = !isOpen;});},
          color: Colors.white,
        )),
        SignIn(root: 'google',
          serviceName: 'Google',
          logo: 'assets/logo-google.png',
          textColor: Colors.grey,),
        SignIn(root: 'github',
          serviceName: 'Github',
          logo: 'assets/logo-github.png',
          textColor: Colors.black,),
        SignIn(root: 'spotify',
          serviceName: 'Spotify',
          logo: 'assets/logo-spotify.png',
          textColor: Color(0xFF1DB954),),
//        SignIn(root: 'linkedin', serviceName: 'Linkedin', logo: 'assets/logo-linkedin.png', textColor: Color(0xFF0072b1),),
        SignIn(root: 'twitter',
          serviceName: 'Twitter',
          logo: 'assets/logo-twitter.png',
          textColor: Color(0xFF00acee),),
        SignIn(root: 'facebook',
          serviceName: 'Facebook',
          logo: 'assets/logo-facebook.png',
          textColor: Color(0xFF3b5998),),
        SignIn(root: 'twitch',
          serviceName: 'Twitch',
          logo: 'assets/logo-twitch.png',
          textColor: Color(0xFF6441A4),),
//        SignIn(root: 'reddit', serviceName: 'Reddit', logo: 'assets/logo-reddit.png', textColor: Color(0xFFFF5700),),
        SignIn(root: 'discord',
          serviceName: 'Discord',
          logo: 'assets/logo-discord.png',
          textColor: Color(0xFF738adb),),
        SizedBox(height: 50,),
      ],
    );
  }
  Widget service() {
    post.add(new Post());
    return ListView.builder(
      itemCount: count,
      itemBuilder: (context, index) {
        if (count == index + 1) {
          return Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
              IconButton(
                icon: Icon(Icons.add),
                color: Colors.white,
                onPressed: () {
                  setState(() {
                    if (checkService() == true)
                      count += 1;
                    else
                      _handleClickMe();
                  });
                },
              ),
              (count > 1) ? IconButton(
                icon: Icon(Icons.delete),
                color: Colors.white,
                onPressed: () {
                  setState(() {
                    post.removeAt(index-1);
                    count -= 1;
                  });
                },
              ) : Container()
            ],
          );
        } else {
          return ActionBloc(
            index: index,
            actionList: actionList,
            reactionList: reactionList,
          );
        }
      }
    );
  }
  Future<void> _handleClickMe() async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return CupertinoAlertDialog(
          title: new Text("Can\'t not add a action / reaction"),
          content: new Text("You first be logged to a service"),
          actions: <Widget>[
            CupertinoDialogAction(
              onPressed: () { setState(() { isOpen = true; }); Navigator.of(context).pop(); },
              child: Text("Go"),
            ),
            CupertinoDialogAction(
              onPressed: () { Navigator.of(context).pop(); },
              child: Text("Close"),
            ),
          ],
        );
      },
    );
  }
}