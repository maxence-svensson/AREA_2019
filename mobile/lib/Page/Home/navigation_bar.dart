import 'package:flutter/material.dart';
import 'package:mobile/Page/Home/home.dart';
import 'package:mobile/Page/Notification/notification.dart';
import 'package:morpheus/morpheus.dart';

class BottomBarPage extends StatefulWidget {

  @override
  _BottomBarPageState createState() => _BottomBarPageState();

}

class _BottomBarPageState extends State<BottomBarPage> {

  final List<Widget> _screens = [
    HomePage(),
    NotificationPage(),
  ];
  int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: MorpheusTabView(
          child: _screens[_currentIndex]
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.accessible_forward),
            title: Text('Home'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.child_friendly),
            title: Text('Notif'),
          ),
        ],
        onTap: (index) {
          if (index != _currentIndex) {
            setState(() => _currentIndex = index);
          }
        },
      ),
    );
  }

}