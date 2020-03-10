import 'package:flutter/material.dart';
import 'package:mobile/Container/Scroll/notification.dart';

class ScrollContainer extends StatefulWidget {
  @override
  _ScrollContainerState createState() => _ScrollContainerState();
}

class _ScrollContainerState extends State<ScrollContainer> {
  final items = List<String>.generate(10, (i) => "Notif n° ${i + 1}");
  TextStyle style = TextStyle(fontSize: 20.0, color: Colors.blue);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: items.length,
      itemBuilder: (context, index) {
        final item = items[index];

        return Dismissible(
          key: Key(item),
          background: NotificationLeft(),
          secondaryBackground: NotificationRight(),
          onDismissed: (direction) {
            // Remove the item from the data source.
            setState(() {
              if(direction == DismissDirection.startToEnd){
                Scaffold.of(context).showSnackBar(SnackBar(content: Text(items[index] + " Like")));
              } else if(direction == DismissDirection.endToStart){
                Scaffold.of(context).showSnackBar(SnackBar(content: Text(items[index] + " Delete")));
              }
              items.removeAt(index);
            });
          },
          // Show a red background as the item is swiped away.
          child: NotificationContainer(Icons.accessible_forward, item),
        );
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