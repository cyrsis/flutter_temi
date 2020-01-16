package tech.blockmanic.flutter_temi

import com.robotemi.sdk.listeners.OnLocationsUpdatedListener
import io.flutter.plugin.common.EventChannel

class OnLocationsUpdatedImpl: OnLocationsUpdatedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_locations_updated_stream"
    }

    override fun onLocationsUpdated(locations: List<String>) {
        eventSink?.success(locations)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }

}