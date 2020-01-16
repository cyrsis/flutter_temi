package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener
import io.flutter.plugin.common.EventChannel

class OnBeWithMeStatusChangedImpl: OnBeWithMeStatusChangedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_be_with_me_stream"
    }

    override fun onBeWithMeStatusChanged(status: String) {
        eventSink?.success(status)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }
}