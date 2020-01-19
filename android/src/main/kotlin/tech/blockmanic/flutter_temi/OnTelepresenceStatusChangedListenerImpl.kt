package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnTelepresenceStatusChangedListener
import com.robotemi.sdk.telepresence.CallState
import io.flutter.plugin.common.EventChannel

class OnTelepresenceStatusChangedListenerImpl : OnTelepresenceStatusChangedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    constructor(sessionId: String) : super(sessionId)

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_telepresence_status_changed_stream"
    }

    override fun onTelepresenceStatusChanged(callState: CallState) {
        val callStateMap = HashMap<String, Any?>(2)
        callStateMap["sessionId"] = callState.sessionId
        callStateMap["state"] = callState.state.toString()
        this.eventSink?.success(callStateMap)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }
}