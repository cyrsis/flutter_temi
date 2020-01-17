package tech.blockmanic.flutter_temi

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import com.robotemi.sdk.*
import io.flutter.plugin.common.EventChannel
import com.robotemi.sdk.TtsRequest


class FlutterTemiPlugin : MethodCallHandler  {

    private val robot: Robot = Robot.getInstance()
    private val goToLocationStatusChangedImpl : GoToLocationStatusChangedImpl = GoToLocationStatusChangedImpl()
    private val onBeWithMeStatusChangedImpl : OnBeWithMeStatusChangedImpl = OnBeWithMeStatusChangedImpl()
    private val onLocationsUpdatedImpl : OnLocationsUpdatedImpl = OnLocationsUpdatedImpl()
    private val nlpImpl : NlpImpl = NlpImpl()
    private val onDetectionStateChangedImpl : OnDetectionStateChangedImpl = OnDetectionStateChangedImpl()
    private val onUserInteractionChangedImpl : OnUserInteractionChangedImpl = OnUserInteractionChangedImpl()

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "flutter_temi")
            val plugin = FlutterTemiPlugin()
            channel.setMethodCallHandler(plugin)

            val onBeWithMeEventChannel = EventChannel(registrar.messenger(), OnBeWithMeStatusChangedImpl.STREAM_CHANNEL_NAME)
            onBeWithMeEventChannel.setStreamHandler(plugin.onBeWithMeStatusChangedImpl)

            val onLocationStatusChangeEventChannel = EventChannel(registrar.messenger(), GoToLocationStatusChangedImpl.STREAM_CHANNEL_NAME)
            onLocationStatusChangeEventChannel.setStreamHandler(plugin.goToLocationStatusChangedImpl)

            val onLocationsUpdatedEventChannel = EventChannel(registrar.messenger(), OnLocationsUpdatedImpl.STREAM_CHANNEL_NAME)
            onLocationsUpdatedEventChannel.setStreamHandler(plugin.onLocationsUpdatedImpl)

            val onNlpEventChannel = EventChannel(registrar.messenger(), NlpImpl.STREAM_CHANNEL_NAME)
            onNlpEventChannel.setStreamHandler(plugin.nlpImpl)

            val onDetectionStateEventChannel = EventChannel(registrar.messenger(), OnBeWithMeStatusChangedImpl.STREAM_CHANNEL_NAME)
            onDetectionStateEventChannel.setStreamHandler(plugin.onDetectionStateChangedImpl)

            val onUserInteractionEventChannel = EventChannel(registrar.messenger(), OnUserInteractionChangedImpl.STREAM_CHANNEL_NAME)
            onUserInteractionEventChannel.setStreamHandler(plugin.onDetectionStateChangedImpl)
        }
    }

    init {
        robot.addOnGoToLocationStatusChangedListener(this.goToLocationStatusChangedImpl)
        robot.addOnBeWithMeStatusChangedListener(this.onBeWithMeStatusChangedImpl)
        robot.addOnLocationsUpdatedListener(this.onLocationsUpdatedImpl)
        robot.addNlpListener(this.nlpImpl)
        robot.addOnDetectionStateChangedListener(this.onDetectionStateChangedImpl)
        robot.addOnUserInteractionChangedListener(this.onUserInteractionChangedImpl)
    }


    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "temi_serial_number" -> {
                result.success(robot.serialNumber)
            }
            "temi_privacy_mode" -> {
                result.success(robot.privacyMode)
            }
            "temi_set_privacy_mode" -> {
                val privacyMode = call.arguments<Boolean>()
                robot.privacyMode = privacyMode
                result.success(privacyMode)
            }
            "temi_battery_data" -> {
                val returnMap = HashMap<String, Any>(2)
                val batteryData = robot.batteryData!!
                returnMap.put("level", batteryData.batteryPercentage)
                returnMap.put("isCharging", batteryData.isCharging)
                result.success(returnMap)
            }
            "temi_show_top_bar" -> {
                robot.showTopBar()
                result.success(true)
            }
            "temi_hide_top_bar" -> {
                robot.hideTopBar()
                result.success(true)
            }
            "temi_speak" -> {
                val speech = call.arguments<String>()
                val request = TtsRequest.create(speech, true)
                robot.speak(request)
                result.success(true)
            }
            "temi_goto" -> {
                val location = call.arguments<String>()
                robot.goTo(location)
                result.success(true)
            }
            "temi_save_location" -> {
                val location = call.arguments<String>()
                result.success(robot.saveLocation(location))
            }
            "temi_get_locations" -> {
                result.success(robot.locations)
            }
            "temi_delete_location" -> {
                val location = call.arguments<String>()
                result.success(robot.deleteLocation(location))
            }
            "temi_be_with_me" -> {
                robot.beWithMe()
                result.success(true)
            }
            "temi_constraint_be_with" -> {
                robot.constraintBeWith()
                result.success(true)
            }
            "temi_follow_me" -> {
                robot.beWithMe()
                result.success(true)
            }
            "temi_stop_movement" -> {
                robot.stopMovement()
                result.success(true)
            }
            "temi_skid_joy" -> {
                val values = call.arguments<List<Double>>()
                robot.skidJoy(values[0].toFloat(), values[1].toFloat())
                result.success(true)
            }
            "temi_tilt_angle" -> {
                val tiltAngle = call.arguments<Int>()
                robot.tiltAngle(tiltAngle)
                result.success(true)
            }
            "temi_turn_by" -> {
                val turnByAngle = call.arguments<Int>()
                robot.turnBy(turnByAngle)
                result.success(true)
            }
            "temi_tilt_by" -> {
                val degrees = call.arguments<Int>()
                robot.tiltBy(degrees)
                result.success(true)
            }
            "temi_start_telepresence" -> {
                val arguments = call.arguments<List<String>>()
                result.success(robot.startTelepresence(arguments[0], arguments[1]))
            }
            "temi_user_info" -> {
                val userInfo = robot.adminInfo!!
                val userInfoMap = HashMap<String, Any?>(3)
                userInfoMap["userId"] = userInfo.userId
                userInfoMap["name"] = userInfo.name
                userInfoMap["picUrl"] = userInfo.picUrl
                userInfoMap["role"] = userInfo.role
                result.success(userInfoMap)
            }
            else -> {
                result.notImplemented()
            }
        }
    }
}
