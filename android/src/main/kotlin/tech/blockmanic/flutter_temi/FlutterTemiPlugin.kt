package tech.blockmanic.flutter_temi

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import com.robotemi.sdk.*
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener
import io.flutter.plugin.common.EventChannel
import com.robotemi.sdk.TtsRequest



class FlutterTemiPlugin : MethodCallHandler, OnGoToLocationStatusChangedListener, EventChannel.StreamHandler {
    override fun onGoToLocationStatusChanged(location: String?, status: String?, descriptionId: Int, description: String?) {
        val response = HashMap<String, Any?>()
        response["location"] = location
        response["status"] = status
        response["descriptionId"] = descriptionId
        response["description"] = description
        locationChangeEventSink?.success(response)
    }

    private val robot: Robot = Robot.getInstance()
    private var locationChangeEventSink: EventChannel.EventSink? = null

    companion object {
        private const val ON_BE_WITH_ME_STREAM_CHANNEL_NAME = "flutter_temi/on_be_with_me_stream"
        private const val ON_LOCATION_STATUS_STREAM_CHANNEL_NAME = "flutter_temi/on_location_status_stream"
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "flutter_temi")
            val plugin = FlutterTemiPlugin()
            channel.setMethodCallHandler(plugin)
//            val onBeWithMeEventChannel = EventChannel(registrar.messenger(), ON_BE_WITH_ME_STREAM_CHANNEL_NAME)
//            onBeWithMeEventChannel.setStreamHandler(plugin)
            val onLocationStatusChangeEventChannel = EventChannel(registrar.messenger(), ON_LOCATION_STATUS_STREAM_CHANNEL_NAME)
            onLocationStatusChangeEventChannel.setStreamHandler(plugin)
        }
    }

    init {
        robot.addOnGoToLocationStatusChangedListener(this)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.locationChangeEventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.locationChangeEventSink = null
    }




//    override fun onBeWithMeStatusChanged(status: String?) {
//        when (status) {
//            "abort" -> {
//                this.locationChangeEventSink?.success("abort")
//            }
//
//            "calculating" -> {
//                this.locationChangeEventSink?.success("calculating")
//            }
//
//            "lock" -> {
//                this.locationChangeEventSink?.success("lock")
//            }
//
//            "search" -> {
//                this.locationChangeEventSink?.success("search")
//            }
//
//            "start" -> {
//                this.locationChangeEventSink?.success("start")
//            }
//
//            "track" -> {
//                this.locationChangeEventSink?.success("track")
//            }
//        }// do something i.e. speak.
//    }

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
            robot.tiltAngle(23, 5.3F)
            result.success(true)
        } else if(call.method == "temi_turn_by") {
            robot.turnBy(90, 6.2F)
            result.success(true)
        } else if(call.method == "temi_tilt_by") {
            robot.tiltBy(70, 1.2F)
            result.success(true)
        } else {
            result.notImplemented()
        }
    }
}
