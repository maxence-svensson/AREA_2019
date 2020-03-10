import 'package:flutter/material.dart';

class CustomNameContainer extends StatefulWidget {
  final String text;

  CustomNameContainer(this.text);
  @override
  _CustomNameContainerState createState() => _CustomNameContainerState();
}

class _CustomNameContainerState extends State<CustomNameContainer> {
  TextStyle style = TextStyle(fontSize: 20.0, color: Colors.white);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(5.0),
      width: MediaQuery.of(context).size.width,
      color: Colors.black12,
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(widget.text, style: style),
            ],
          )
      ),
    );
  }
}
