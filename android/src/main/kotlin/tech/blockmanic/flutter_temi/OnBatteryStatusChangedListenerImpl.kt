package tech.blockmanic.flutter_temi

import com.robotemi.sdk.BatteryData
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener
import io.flutter.plugin.common.EventChannel

class OnBatteryStatusChangedListenerImpl: OnBatteryStatusChangedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_battery_status_changed_stream"
        fun batteryToMap(batteryData: BatteryData): HashMap<String, Any> {
            val batteryMap = HashMap<String, Any>(2)
            batteryMap["level"] = batteryData.batteryPercentage
            batteryMap["isCharging"] = batteryData.isCharging
            return batteryMap
        }
    }

    override fun onBatteryStatusChanged(batteryData: BatteryData?) {
        if(batteryData != null) {
          eventSink?.success(batteryToMap(batteryData))
        }
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }

}