import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/Page/Login/webview.dart';
import 'package:mobile/global.dart';

class ActionBloc extends StatefulWidget {
  final int index;
  final List<String> actionList;
  final List<String> reactionList;
  ActionBloc({this.index, this.actionList, this.reactionList});
  @override
  _ActionBlocState createState() => _ActionBlocState();
}

class _ActionBlocState extends State<ActionBloc> {
  bool isSub = false;

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 20.0),
      color: Colors.white,
      child: Column(
        children: <Widget>[
          Text("Action"),
          action(widget.index),
          Text("Reaction"),
          reaction(widget.index),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
              Text( isSub ? "Unsubscribe" : "Subscribe"),
              CupertinoSwitch(
                value: isSub,
                onChanged: (val) {setState(() {
                  isSub = val;
                  if(isSub == true)
                    Navigator.push(context, MaterialPageRoute(builder: (context) => WebView('http://localhost:8080/postActionReactionForUser?userid=${user.id}&actionName=${post[widget.index].action}&actionValue=${post[widget.index].actionVal}&reactionName=${post[widget.index].reaction}&reactionValue=${post[widget.index].reactionVal}', 'Add service', '')));
                  else
                    Navigator.push(context, MaterialPageRoute(builder: (context) => WebView('http://localhost:8080/deleteActionReactionForUser?userid=${user.id}&actionName=${post[widget.index].action}&actionValue=${post[widget.index].actionVal}&reactionName=${post[widget.index].reaction}&reactionValue=${post[widget.index].reactionVal}', 'Remove service', '')));
                });},
              )
            ],
          )
        ],
      ),
    );
  }

  Widget action(int index) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: <Widget>[
        DropdownButton(
          value: post[index].action,
          items: widget.actionList.map((String value) {
            return new DropdownMenuItem<String>(
              value: value,
              child: Text(value,),
            );
          }).toList(),
          onChanged: (value) {
            setState(() {
              post[index].action = value;
            });
          },
        ),
        Expanded(
          child: TextField(
            textAlign: TextAlign.center,
            onChanged: (value) {
              setState(() {
                post[widget.index].actionVal = value;
              });
            },
          ),
        )
      ],
    );
  }

  Widget reaction(int index) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: <Widget>[
        DropdownButton(
          value: post[index].reaction,
          items: widget.reactionList.map((String value) {
            return new DropdownMenuItem<String>(
              value: value,
              child: Text(value,),
            );
          }).toList(),
          onChanged: (value) {
            setState(() {
              post[index].reaction = value;
            });
          },
        ),
        Expanded(
          child: TextField(
            textAlign: TextAlign.center,
            onChanged: (value) {
              setState(() {
                print(value);
                post[widget.index].reactionVal = value;
                print(post[widget.index].reactionVal);
              });
              },
          ),
        )
      ],
    );
  }
}

bool checkService() {
  if (google == false &&
      github == false &&
      spotify == false &&
      linkedin == false &&
      twitter == false &&
      facebook == false &&
      twitch == false &&
      reddit == false &&
      discord == false)
    return false;
  else
    return true;
}