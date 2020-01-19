package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener
import io.flutter.plugin.common.EventChannel

class OnConstraintBeWithStatusListenerImpl : OnConstraintBeWithStatusChangedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_constraint_be_with_stream"
    }

    override fun onConstraintBeWithStatusChanged(isConstraint: Boolean) {
        this.eventSink?.success(isConstraint)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }
}