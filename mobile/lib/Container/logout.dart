import 'package:flutter/material.dart';

class LogOut extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: <Widget>[
        Text("AREA", style: TextStyle(color: Colors.white, fontSize: 28),),
        Container(
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(90.0),
          ),
          child: IconButton(
            icon: Icon(Icons.power_settings_new, color: Colors.blue),
            onPressed: () => Navigator.pushReplacementNamed(context, '/logout/server'),
          ),
        ),
    ],
    );
  }
}
