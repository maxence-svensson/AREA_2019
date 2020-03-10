import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';
import 'package:mobile/Container/fetch.dart';
import 'package:mobile/Page/Home/home.dart';
import 'package:mobile/global.dart';

class WebView extends StatefulWidget {
  final String url;
  final String title;
  final String service;
  final String root;
  final bool isNotLog;
  WebView(this.url, this.title, this.service, {this.root = "/login/home", this.isNotLog = true});
  @override
  WebViewState createState() {
    return new WebViewState();
  }
}

class WebViewState extends State<WebView> {
  Set<dynamic> setAction;
  Set<dynamic> setReaction;
  var uri;
  final flutterWebViewPlugin = new FlutterWebviewPlugin();
  GlobalKey<ScaffoldState> scaffoldKey = GlobalKey<ScaffoldState>();
  StreamSubscription<String> _onUrlChanged;
  StreamSubscription _onDestroy;
  StreamSubscription<WebViewStateChanged> _onStateChanged;

  @override
  void initState() {
    super.initState();
    List<String> split;

    flutterWebViewPlugin.close();
    _onDestroy = flutterWebViewPlugin.onDestroy.listen((_) {
      print("destroy");
    });
    _onStateChanged = flutterWebViewPlugin.onStateChanged.listen((WebViewStateChanged state) {
          print("onStateChanged: ${state.type} ${state.url}");
          print("split ${state.url.split("/")}");
          split = state.url.split("/");
          if (split[2] == "localhost:8081") {
            uri = Uri.parse(state.url);
            uri.queryParameters.forEach((k, v) {
              if (k == "id") {
                  setState(() {
                    user.id = v;
                  });
              }
            });
            fetchAction().then((onValue) {
              actionList = List<String>.from(onValue);
            });
            fetchReaction().then((onValue) {
              reactionList = List<String>.from(onValue);
            });
            fetchService().then((onValue) {
              google = false;
              github = false;
              spotify = false;
              linkedin = false;
              twitter = false;
              facebook = false;
              twitch = false;
              reddit = false;
              discord = false;
              serviceList = List<String>.from(onValue);
              serviceList.forEach((f) {
                setState(() {
                  switch (f) {
                    case "google" :
                      setState(() {
                        google = true;
                      });
                      break;
                    case "github" :
                      setState(() {
                        github = true;
                      });
                      break;
                    case "spotify" :
                      setState(() {
                        spotify = true;
                      });
                      break;
                    case "linkedin" :
                      setState(() {
                        linkedin = true;
                      });
                      break;
                    case "twitter" :
                      setState(() {
                        twitter = true;
                      });
                      break;
                    case "facebook" :
                      setState(() {
                        facebook = true;
                      });
                      break;
                    case "twitch" :
                      setState(() {
                        twitch = true;
                      });
                      break;
                    case "reddit" :
                      setState(() {
                        reddit = true;
                      });
                      break;
                    case "discord" :
                      setState(() {
                        discord = true;
                      });
                      break;
                    default :
                      break;
                  }
                });
              });
            });
            flutterWebViewPlugin.close();
            (user.id == "0") ? Navigator.pushNamed(context, '/login/error') : Navigator.pushNamed(context, widget.root);
          }
    });
    _onUrlChanged = flutterWebViewPlugin.onUrlChanged.listen((String url) {
    });
  }

  @override
  void dispose() {
    // Every listener should be canceled, the same should be done with this stream.
    _onDestroy.cancel();
    _onUrlChanged.cancel();
    _onStateChanged.cancel();
    flutterWebViewPlugin.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return webView();
  }
  webView() {
    print("USER AGENT:   $userAgent");

    return WebviewScaffold(
      appBar: AppBar(
        backgroundColor: Color(0x9900cc66),
        title: Text(widget.title),
        centerTitle: true,
      ),
      key: scaffoldKey,
      url: widget.url,
      hidden: true,
      clearCache: true,
      userAgent: userAgent,
      clearCookies: true,
//      appBar: AppBar(title: Text("Current Url")),
    );
  }
}