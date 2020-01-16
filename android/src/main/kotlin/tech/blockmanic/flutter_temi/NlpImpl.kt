package tech.blockmanic.flutter_temi

import com.robotemi.sdk.NlpResult
import com.robotemi.sdk.Robot
import io.flutter.plugin.common.EventChannel

class NlpImpl : Robot.NlpListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/nlp_stream"
    }


    override fun onNlpCompleted(nlpResult: NlpResult) {
        eventSink?.success(nlpResult?.action)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }
}
