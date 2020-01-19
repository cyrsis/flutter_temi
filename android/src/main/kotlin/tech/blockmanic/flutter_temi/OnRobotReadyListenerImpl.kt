package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnRobotReadyListener
import io.flutter.plugin.common.EventChannel

class OnRobotReadyListenerImpl: OnRobotReadyListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_robot_ready_stream"
    }

    override fun onRobotReady(isReady: Boolean) {
        eventSink?.success(isReady)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }

}