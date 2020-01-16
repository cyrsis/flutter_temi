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
        }
    }

    init {
        robot.addOnGoToLocationStatusChangedListener(this.goToLocationStatusChangedImpl)
        robot.addOnBeWithMeStatusChangedListener(this.onBeWithMeStatusChangedImpl)
        robot.addOnLocationsUpdatedListener(this.onLocationsUpdatedImpl)
        robot.addNlpListener(this.nlpImpl)
        robot.addOnDetectionStateChangedListener(this.onDetectionStateChangedImpl)
    }


    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "temi_speak") {
            val speech = call.arguments<String>()
            val request = TtsRequest.create(speech, true)
            robot.speak(request)
            result.success(true)
        } else if (call.method == "temi_goto") {
            val location = call.arguments<String>()
            robot.goTo(location)
            result.success(true)
        } else if (call.method == "temi_save_location") {
            val location = call.arguments<String>()
            robot.saveLocation(location)
            result.success(true)
        } else if (call.method == "temi_follow_me") {
            robot.beWithMe()
            result.success(true)
        } else if(call.method == "temi_skid_joy") {
            robot.skidJoy(-1.0F, 1.0F)
            result.success(true)
        } else if(call.method == "temi_tilt_angle") {
            val tilt_angele = call.arguments<Int>()
            robot.tiltAngle(tilt_angele, 15.3F)
            result.success(true)
        } else if(call.method == "temi_turn_by") {
            val turn_by_angele = call.arguments<Int>()
            robot.turnBy(turn_by_angele, 6.2F)
            result.success(true)
        } else if(call.method == "temi_tilt_by") {
            robot.tiltBy(70, 1.2F)
            result.success(true)
        } else {
            result.notImplemented()
        }
    }
}
