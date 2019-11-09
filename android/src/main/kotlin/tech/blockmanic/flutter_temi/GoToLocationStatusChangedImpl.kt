package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener
import io.flutter.plugin.common.EventChannel

class GoToLocationStatusChangedImpl : OnGoToLocationStatusChangedListener, EventChannel.StreamHandler {

    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_location_status_stream"
    }

    override fun onGoToLocationStatusChanged(location: String?, status: String?, descriptionId: Int, description: String?) {
        val response = HashMap<String, Any?>()
        response["location"] = location
        response["status"] = status
        response["descriptionId"] = descriptionId
        response["description"] = description
        eventSink?.success(response)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }

}