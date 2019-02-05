package com.rusili.lib.log

import android.util.Log
import com.rusili.lib.common.shorten
import com.rusili.lib.common.abbreviateTime
import com.rusili.lib.parallax.domain.Event3

private const val TIMED_LOGGER = "TiltParallaxTimedLogger"

class TimedLogger {
    @Volatile
    private var lastLog = System.currentTimeMillis()

    @Synchronized
    fun logEventEveryNMilliSeconds(
        raw: Event3?,
        event: Event3?,
        n: Int
    ) {
        if (raw != null && event != null) {
            logEveryNMilliseconds(n) {
                Log.d(TIMED_LOGGER, (System.currentTimeMillis().abbreviateTime()))
                Log.d(
                    TIMED_LOGGER,
                    "Pre" + " x: " + raw.x.shorten() + " y: " + raw.y.shorten() + " z: " + raw.z.shorten()
                );
                Log.d(
                    TIMED_LOGGER,
                    "Post" + " x: " + event.x.shorten() + "z: " + event.y.shorten() + "y: " + event.z.shorten()
                );
            }
        }
    }

    private fun logEveryNMilliseconds(
        n: Int,
        func: () -> Unit
    ) {
        if (System.currentTimeMillis() > (lastLog + n)) {
            func()

            lastLog = System.currentTimeMillis()
        }
    }
}
