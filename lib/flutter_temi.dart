import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class FlutterTemi {
  //Single Method Channel
  static const MethodChannel _channel = const MethodChannel('flutter_temi');

  //Event Channel for Stream
  static const EventChannel _onBeWithMeEventChannel =
      EventChannel('flutter_temi/on_be_with_me_stream');

  static const EventChannel _onLocationStatusChangeEventChannel =
      EventChannel('flutter_temi/on_location_status_stream');

  static const EventChannel _onLocationsUpdatedEventChannel =
      EventChannel('flutter_temi/on_locations_updated_stream');

  static const EventChannel _nlpEventChannel =
      EventChannel('flutter_temi/nlp_stream');

  static const EventChannel _onUserInteractionEventChannel =
      EventChannel('flutter_temi/on_user_interaction_stream');

  static const EventChannel _ttsEventChannel =
      EventChannel('flutter_temi/tts_stream');

  static const EventChannel _asrEventChannel =
      EventChannel('flutter_temi/asr_stream');

  static const EventChannel _wakeupWordEventChannel =
      EventChannel('flutter_temi/wakeup_word_stream');

  static const EventChannel _constraintBeWithStatusEventChannel =
      EventChannel('flutter_temi/on_constraint_be_with_stream');

//  static const EventChannel _onTelepresenceStatusChangedEventChannel =
//      EventChannel('flutter_temi/on_telepresence_status_changed_stream');

//  static const EventChannel _onUsersUpdatedEventChannel =
//      EventChannel('flutter_temi/on_users_updated_stream');

  static const EventChannel _onPrivacyModeChangedEventChannel =
      EventChannel('flutter_temi/on_privacy_mode_changed_stream');

  static const EventChannel _onBatteryStatusChangedEventChannel =
      EventChannel('flutter_temi/on_battery_status_changed_stream');

  static const EventChannel _onDetectionStateChangedEventChannel =
      EventChannel('flutter_temi/on_detection_state_changed_stream');

  static const EventChannel _onRobotReadyEventChannel =
      EventChannel('flutter_temi/on_robot_ready_stream');

  static Future<String> get temiSerialNumber async {
    return await _channel.invokeMethod('temi_serial_number');
  }

  static Future<bool> get temiPrivacyMode async {
    return await _channel.invokeMethod('temi_privacy_mode');
  }

  static Future<bool> temiSetPrivacyMode(bool privacyMode) async {
    return await _channel.invokeMethod('temi_set_privacy_mode', privacyMode);
  }

  static Future<Map<String, dynamic>> get temiBatteryData async {
    return await _channel.invokeMethod('temi_battery_data');
  }

  static temiShowTopBar() async {
    await _channel.invokeMethod('temi_show_top_bar');
  }

  static temiHideTopBar() async {
    await _channel.invokeMethod('temi_hide_top_bar');
  }

  static temiSpeak(String speech) async {
    await _channel.invokeMethod('temi_speak', speech);
  }

  static temiSpeakForce(String speech) async {
    await _channel.invokeMethod('temi_speak_force', speech);
  }
  static temiFinisheConverstaion() async {
    await _channel.invokeMethod('temi_finishe_conversation');
  }


  static temiGoTo(String location) async {
    await _channel.invokeMethod('temi_goto', location);
  }

  static Future<bool> temiSaveLocation(String location) async {
    return await _channel.invokeMethod('temi_save_location', location);
  }

  static Future<List<String>> temiGetLocations() async {
    return await _channel.invokeListMethod('temi_get_locations');
  }

  static Future<bool> temiDeleteLocation(String location) async {
    return await _channel.invokeMethod('temi_delete_location');
  }

  static temiBeWithMe() async {
    await _channel.invokeMethod('temi_be_with_me');
  }

  static temiConstraintBeWith() async {
    await _channel.invokeMethod('temi_constraint_be_with');
  }

  static temiFollowMe() async {
    await _channel.invokeMethod('temi_follow_me');
  }

  static temiStopMovement() async {
    await _channel.invokeMethod('temi_stop_movement');
  }

  static temiSkidJoy(double x, double y) async {
    await _channel.invokeMethod('temi_skid_joy', [x, y]);
  }

  static temiTiltAngle(int angle) async {
    //-25 to 0 +55
    await _channel.invokeMethod('temi_tilt_angle', angle);
  }

  static temiTurnBy(int angle) async {
    await _channel.invokeMethod('temi_turn_by', angle);
  }

  static temiTiltBy(int degrees) async {
    await _channel.invokeMethod('temi_tilt_by', degrees);
  }

  static Future<String> temiStartTelepresence(
      String displayName, String peerId) async {
    return await _channel
        .invokeMethod('temi_start_telepresence', [displayName, peerId]);
  }

  //${some['name']} ${some['userId']}
  static Future<Map<dynamic, dynamic>> get userInfo async {
    return await _channel.invokeMethod('temi_user_info');
  }

  static Future<List<Map<String, dynamic>>> get allContacts async {
    return await _channel.invokeListMethod('temi_get_contacts');
  }

  static Future<List<Map<String, dynamic>>> get recentCalls async {
    return await _channel.invokeListMethod('temi_get_recent_calls');
  }

  static temiSetWakeup(bool disable) async {
    await _channel.invokeMethod('temi_toggle_wakeup', disable);
  }

  static temiWakeup() async {
    await _channel.invokeMethod('temi_wakeup');
  }

  static temiSetNavigationBillboard(bool hide) async {
    await _channel.invokeMethod('temi_toggle_navigation_billboard', hide);
  }

  static Stream<String> temiSubscribeToOnBeWithMeEvents() {
    return _onBeWithMeEventChannel.receiveBroadcastStream();
  }

  static Stream<dynamic> temiSubscribeToOnLocationStatusChangeEvents() {
    return _onLocationStatusChangeEventChannel.receiveBroadcastStream();
  }

  static Stream<List<dynamic>> temiSubscribeToOnLocationsUpdatedEvents() {
    return _onLocationsUpdatedEventChannel.receiveBroadcastStream();
  }

  static Stream<String> temiSubscribeToNlpEvents() {
    return _nlpEventChannel.receiveBroadcastStream();
  }

  static Stream<bool> temiSubscribeToOnUserInteractionEvents() {
    return _onUserInteractionEventChannel.receiveBroadcastStream();
  }

  static Stream<Map<String, dynamic>> temiSubscribeToTtsEvents() {
    return _ttsEventChannel.receiveBroadcastStream();
  }

  static Stream<dynamic> temiSubscribeToAsrEvents() {
    return _asrEventChannel.receiveBroadcastStream();
  }

  static Stream<Map<String, dynamic>> temiSubscribeToWakeupWordEvents() {
    return _wakeupWordEventChannel.receiveBroadcastStream();
  }

  static Stream<bool> temiSubscribeToOnConstraintBeWithStatusChangedEvents() {
    return _constraintBeWithStatusEventChannel.receiveBroadcastStream();
  }

//  static Stream<Map<String, dynamic>> temiSubscribeToOnTelepresenceStatusChangedEvents() {
//    return _onTelepresenceStatusChangedEventChannel.receiveBroadcastStream();
//  }

//  static Stream<Map<String, dynamic>> temiSubscribeToUsersUpdatedEvents() {
//    return _onUsersUpdatedEventChannel.receiveBroadcastStream();
//  }

  static Stream<bool> temiSubscribeToOnPrivacyModeChangedEvents() {
    return _onPrivacyModeChangedEventChannel.receiveBroadcastStream();
  }

  static Stream<Map<String, dynamic>>
      temiSubscribeToOnBatteryStatusChangedEvents() {
    return _onBatteryStatusChangedEventChannel.receiveBroadcastStream();
  }

  static Stream<dynamic> temiSubscribeToDetectionStateChangedEvents() {
    return _onDetectionStateChangedEventChannel.receiveBroadcastStream();
  }

  static Stream<bool> temiSubscribeToRobotReadyEvents() {
    return _onRobotReadyEventChannel.receiveBroadcastStream();
  }
}
