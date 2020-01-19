package tech.blockmanic.flutter_temi

import com.robotemi.sdk.Robot
import io.flutter.plugin.common.EventChannel

class WakeupWordListenerImpl : Robot.WakeupWordListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/wakeup_word_stream"
    }

    override fun onWakeupWord(wakeupWord: String, direction: Int) {
        val wakeupWordMap = HashMap<String, Any>()
        wakeupWordMap["wakeupWord"] = wakeupWord
        wakeupWordMap["direction"] = direction
        this.eventSink?.success(wakeupWordMap)
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }
}