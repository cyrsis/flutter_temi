package tech.blockmanic.flutter_temi

import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import io.flutter.plugin.common.EventChannel

class ASRListenerImpl : Robot.AsrListener, EventChannel.StreamHandler {


    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/asr_stream"
    }


    override fun onAsrResult(asrResult: String) {
        this.eventSink?.success(asrResult);
    }



}