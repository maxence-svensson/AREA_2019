import 'package:http/http.dart' as http;

String userAgent = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10136";

int count = 1;
List<Post> post = new List<Post>();
User user = new User();
API api = new API();
List<String> actionList = [];
List<String> reactionList = [];
List<String> serviceList;

bool isOpen = false;

bool google = false;
bool github = false;
bool spotify = false;
bool linkedin = false;
bool twitter = false;
bool facebook = false;
bool twitch = false;
bool reddit = false;
bool discord = false;

Map<String, bool> connectedService() => {
  'google' : google,
  'github' : github,
  'spotify' : spotify,
  'linkedin' : linkedin,
  'twitter' : twitter,
  'facebook' : facebook,
  'twitch' : twitch,
  'reddit' : reddit,
  'discord' : discord,
};

class Post {
  String action;
  String reaction;
  String actionVal;
  String reactionVal;
}

class API {
  static Future getID() {
    return http.get('localhost:8081/getId');
  }
}


class User {
  String id = "";
  String name = "";
  String pass = "";
}