import 'dart:async';

import 'package:flutter/services.dart';

class FlutterTemi {
  static const MethodChannel _channel =
      const MethodChannel('flutter_temi');
  static const EventChannel _onBeWithMeEventChannel = EventChannel('flutter_temi/on_be_with_me_stream');
  static const EventChannel _onLocationStatusChangeEventChannel = EventChannel('flutter_temi/on_location_status_stream');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static temiSpeak(String speech) async {
    await _channel.invokeMethod('temi_speak', speech);
  }

  static temiGoTo(String location) async {
    await _channel.invokeMethod('temi_goto', location);
  }

  static temiSaveLocation(String location) async {
    await _channel.invokeMethod('temi_save_location', location);
  }

  static temiFollowMe() async {
    await _channel.invokeMethod('temi_follow_me');
  }

  static temiSkidJoy() async {
    await _channel.invokeMethod('temi_skid_joy');
  }

  static temiTiltAngle() async {
    await _channel.invokeMethod('temi_tilt_angle');
  }

  static temiTurnBy() async {
    await _channel.invokeMethod('temi_turn_by');
  }

  static temiTiltBy() async {
    await _channel.invokeMethod('temi_tilt_by');
  }

  static Stream<String> temiSubscribeToOnBeWithMeEvents() {
    return _onBeWithMeEventChannel.receiveBroadcastStream();
  }

  static Stream<String> temiSubscribeToOnLocationStatusChangeEvents() {
    return _onLocationStatusChangeEventChannel.receiveBroadcastStream();
  }
}
