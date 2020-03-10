import 'package:flutter/material.dart';

class NotificationContainer extends StatefulWidget {
  IconData logo;
  String msg;

  NotificationContainer(this.logo, this.msg);

  @override
  _NotificationContainerState createState() => _NotificationContainerState();
}

class _NotificationContainerState extends State<NotificationContainer> {
  TextStyle style = TextStyle(fontSize: 20.0, color: Colors.white);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        color: Colors.black12,
//        width: MediaQuery.of(context).size.width*0.4,
        padding: EdgeInsets.only(left: 0, top: 20.0, bottom: 20.0, right: 0),
        margin: EdgeInsets.only(left: 0, top: 5.0, bottom: 5.0, right: 0),
        child: Row(
          children: <Widget>[
            SizedBox(
              width: 10,
            ),
            CircleAvatar(
              backgroundColor: Colors.black12,
              radius: 20,
              backgroundImage: AssetImage('assets/logo-google.png'),
            ),
            SizedBox(
              width: 10,
            ),
            Text(widget.msg, style: style),
          ],
        ),
      ),
    );
  }
}

class NotificationLeft extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.lightBlue,
      child: Align(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            SizedBox(
              width: 20,
            ),
            Icon(
              Icons.check,
              color: Colors.white,
            ),
            Text(
              " Like",
              style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.w700,
              ),
              textAlign: TextAlign.right,
            ),
          ],
        ),
        alignment: Alignment.centerLeft,
      ),
    );
  }
}

class NotificationRight extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.red,
      child: Align(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.end,
          children: <Widget>[
            Icon(
              Icons.delete,
              color: Colors.white,
            ),
            Text(
              " Delete",
              style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.w700,
              ),
              textAlign: TextAlign.right,
            ),
            SizedBox(
              width: 20,
            ),
          ],
        ),
        alignment: Alignment.centerRight,
      ),
    );
  }
}
