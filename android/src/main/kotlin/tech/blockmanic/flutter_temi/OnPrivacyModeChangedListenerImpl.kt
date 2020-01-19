package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnPrivacyModeChangedListener
import io.flutter.plugin.common.EventChannel

class OnPrivacyModeChangedListenerImpl: OnPrivacyModeChangedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_privacy_mode_changed_stream"
    }

    override fun onPrivacyModeChanged(state: Boolean) {
        eventSink?.success(state)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }

}