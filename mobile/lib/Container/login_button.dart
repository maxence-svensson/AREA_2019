import 'package:flutter/material.dart';
import 'package:mobile/global.dart';

class SignIn extends StatelessWidget {
  final root;
  final serviceName;
  final logo;
  final backgroundColor;
  final textColor;

  SignIn({this.root, this.serviceName, this.logo, this.backgroundColor = Colors.white, this.textColor = Colors.black});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery
          .of(context)
          .size
          .width,
      margin: const EdgeInsets.only(left: 40.0, right: 40.0, top: 10.0),
      alignment: Alignment.center,
      child: Container(
        width: MediaQuery
            .of(context)
            .size
            .width*.8,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(12.0),
          color: connectedService()[root] ? Colors.grey : backgroundColor,
        ),
        child: OutlineButton(
          onPressed: () => connectedService()[root] ? Navigator.of(context).pushNamed('/logout/$root') : Navigator.of(context).pushNamed('/login/$root'),
          shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(12)
          ),
          borderSide: BorderSide(color: Colors.white),
          child: Padding(
            padding: const EdgeInsets.fromLTRB(0, 10, 0, 10),
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Image(
                    image: AssetImage(logo), height: 35.0),
                Padding(
                  padding: const EdgeInsets.only(left: 10),
                  child: Text(
                    connectedService()[root] ? "Sign out to $serviceName" : "Sign in with $serviceName",
                    style: TextStyle(
                      fontSize: 20,
                      color: connectedService()[root] ? Colors.white : textColor,
                    ),
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
