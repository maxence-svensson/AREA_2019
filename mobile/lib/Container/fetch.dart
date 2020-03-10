import 'dart:convert';

import 'package:mobile/global.dart';
import 'package:http/http.dart' as http;

Future<dynamic> fetchAction() async {
  final response = await http.get('http://localhost:8080/getActionForUser?userid=' + user.id);

  if (response.statusCode == 200) {
    // If the call to the server was successful, parse the JSON.
    return json.decode(response.body);
  } else {
    // If that call was not successful, throw an error.
    throw Exception('Failed to load post');
  }
}

Future<dynamic> fetchReaction() async {
  final response = await http.get('http://localhost:8080/getReactionForUser?userid=' + user.id);

  if (response.statusCode == 200) {
    // If the call to the server was successful, parse the JSON.
    return json.decode(response.body);
  } else {
    // If that call was not successful, throw an error.
    throw Exception('Failed to load post');
  }
}

Future<dynamic> fetchService() async {
  final response = await http.get('http://localhost:8080/getServiceForUser?userid=' + user.id);

  if (response.statusCode == 200) {
    // If the call to the server was successful, parse the JSON.
    return json.decode(response.body);
  } else {
    // If that call was not successful, throw an error.
    throw Exception('Failed to load post');
  }
}

Future logOut() async {
  final response = await http.get('http://localhost:8080/logout');

  if (response.statusCode == 200) {
    // If the call to the server was successful, parse the JSON.
    print("ok logout");
    return json.decode(response.body);
  } else {
    // If that call was not successful, throw an error.
    throw Exception('Failed to load post');
  }
}