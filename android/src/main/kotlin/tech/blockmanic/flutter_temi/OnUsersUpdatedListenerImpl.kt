package tech.blockmanic.flutter_temi

import com.robotemi.sdk.UserInfo
import com.robotemi.sdk.listeners.OnUsersUpdatedListener
import io.flutter.plugin.common.EventChannel

class OnUsersUpdatedListenerImpl: OnUsersUpdatedListener, EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null

    constructor(userIds: List<String>?) : super(userIds)

    companion object {
        const val STREAM_CHANNEL_NAME = "flutter_temi/on_users_updated_stream"
        fun contactToMap(userInfo: UserInfo): HashMap<String, Any?> {
            val userInfoMap = HashMap<String, Any?>(3)
            userInfoMap["userId"] = userInfo.userId
            userInfoMap["name"] = userInfo.name
            userInfoMap["picUrl"] = userInfo.picUrl
            userInfoMap["role"] = userInfo.role
            return userInfoMap
        }
    }

    override fun onUserUpdated(user: UserInfo) {
        eventSink?.success(contactToMap(user))
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        this.eventSink = null
    }

}