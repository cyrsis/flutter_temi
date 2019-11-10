import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_temi/flutter_temi.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await FlutterTemi.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
    FlutterTemi.temiSubscribeToOnLocationStatusChangeEvents().listen((event) {
      print('Location event: ${event}');
      print('Location event status : ${event['status']}');
      if (event['status'] == "complete") {
        FlutterTemi.temiSpeak("I have arrive from whatever ");
      }
      print('Location event: ${event.runtimeType}');
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
              FlatButton(
                child: Text('Goto Allen'),
                onPressed: () async {
                  await FlutterTemi.temiGoTo('allen');
                  await FlutterTemi.temiSpeak('Allen I am coming');

                },
              ),
              FlatButton(
                child: Text('Goto Victor'),
                onPressed: () async => FlutterTemi.temiGoTo('victor'),
              ),
              FlatButton(
                child: Text('Speak Victor'),
                onPressed: () async => FlutterTemi.temiSpeak('victor is Great'),
              ),
              FlatButton(
                child: Text('Save Location -- Allen'),
                onPressed: () async => FlutterTemi.temiSaveLocation('Allen'),
              ),
              FlatButton(
                child: Text('Follow me'),
                onPressed: () async => FlutterTemi.temiFollowMe(),
              ),
              FlatButton(
                child: Text('Skid Joy'),
                onPressed: () async => FlutterTemi.temiSkidJoy(),
              ),
              FlatButton(
                child: Text('Title Angle'),
                onPressed: () async => FlutterTemi.temiTiltAngle(15.2),
              ),
              FlatButton(
                child: Text('Trun by'),
                onPressed: () async => FlutterTemi.temiTurnBy(15.2),
              ),
              FlatButton(
                child: Text('Title  by'),
                onPressed: () async => FlutterTemi.temiTiltBy(),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
