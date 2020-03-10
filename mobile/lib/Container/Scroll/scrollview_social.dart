import 'package:flutter/material.dart';
import 'package:mobile/Container/Scroll/social.dart';

class DashboardContainer extends StatefulWidget {
  @override
  _DashboardContainerState createState() => _DashboardContainerState();
}

class _DashboardContainerState extends State<DashboardContainer> {
  final items = List<String>.generate(20, (i) => "Facebook");
  TextStyle style = TextStyle(fontSize: 20.0, color: Colors.blue);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: items.length,
      itemBuilder: (context, index) {
        final item = items[index];

        return SocialContainer(item, false);
      },
    );
  }
}

/*

 */

/*
SingleChildScrollView(
        child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                NotificationContainer(Icons.accessible_forward, "Il est l'heure de se lever"),
                NotificationContainer(Icons.accessibility_new, "Nouveau msg d'une zoulette"),
                NotificationContainer(Icons.videogame_asset, "Vous avez reçu une invitation à jouer"),
                NotificationContainer(Icons.airplanemode_active, "Votre vol a était retardé"),
              ],
            )
        ),
      )
 */