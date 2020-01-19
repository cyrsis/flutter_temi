package tech.blockmanic.flutter_temi

import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import io.flutter.plugin.common.EventChannel

class TtsListenerImpl : Robot.TtsListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/tts_stream"
    }

    override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
        val ttsRequestMap = HashMap<String, Any?>(4)
        ttsRequestMap["id"] = ttsRequest.id
        ttsRequestMap["packageName"] = ttsRequest.packageName
        ttsRequestMap["speech"] = ttsRequest.speech
        ttsRequestMap["status"] = ttsRequest.status.toString()
        this.eventSink?.success(ttsRequestMap)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }
}