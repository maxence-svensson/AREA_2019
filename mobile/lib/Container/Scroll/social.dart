import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:awesome_dialog/awesome_dialog.dart';

class SocialContainer extends StatefulWidget {
  String msg;
  bool value;

  SocialContainer(this.msg, this.value);

  @override
  _SocialContainerState createState() => _SocialContainerState();
}

class _SocialContainerState extends State<SocialContainer> {
  TextStyle style = TextStyle(fontSize: 20.0, color: Colors.blue);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: MergeSemantics(
        child: ListTile(
          title: Text(widget.msg, style: style),
          trailing: CupertinoSwitch(
            value: widget.value,
            onChanged: (bool value) { setState(() {
              widget.value = value;
              if (widget.value == true) {
                PopUp();
              }
            }); },
          ),
          onTap: () { setState(() {
            widget.value = !widget.value;
            if (widget.value == true) {
              PopUp();
            }
          });},
        ),
      ),
    );
  }

  Future PopUp() {
    return AwesomeDialog(
        context: context,
        dialogType: DialogType.INFO,
        animType: AnimType.BOTTOMSLIDE,
        body: FormPopup(),
        btnCancelOnPress: () {setState(() {
          widget.value = false;
        });},
        btnOkOnPress: () {}
    ).show();
  }
}

class FormPopup extends StatelessWidget {
  TextStyle style = TextStyle(fontSize: 20.0, color: Colors.white);

  @override
  Widget build(BuildContext context) {

    final emailField = TextField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(
        contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
        hintText: "Username",
      ),
    );
    final passwordField = TextField(
      obscureText: true,
      style: style,
      decoration: InputDecoration(
          contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "Password",
          ),
    );
    return Center(
      child: Container(
        color: Colors.blue,
        child: Column(
          children: <Widget>[
            Text("NEED TO BE LOG", style: style),
            emailField,
            SizedBox(height: 5.0,),
            passwordField,
          ],
        ),
      )
    );
  }
}
