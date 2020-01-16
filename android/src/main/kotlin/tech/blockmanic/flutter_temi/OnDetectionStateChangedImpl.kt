package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnDetectionStateChangedListener
import io.flutter.plugin.common.EventChannel

class OnDetectionStateChangedImpl : OnDetectionStateChangedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_detection_state_stream"
    }

    override fun onDetectionStateChanged(state: Int) {
        eventSink?.success(state)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }
}