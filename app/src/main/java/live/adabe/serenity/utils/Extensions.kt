package live.adabe.serenity.utils

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.media.MediaMetadataRetriever
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
fun buttonEffect(button: View) {
    button.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.background.clearColorFilter()
                v.invalidate()
            }
        }
        false
    }
}

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun getAlbumArt(uri: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(uri)
    val art = retriever.embeddedPicture
    retriever.close()
    return art
}

fun milliSecondsToTimer(milliseconds: Long): String {
    var finalTimerString = ""
    var secondsString = ""

    //Convert total duration into time
    val hours = (milliseconds / (1000 * 60 * 60)).toInt()
    val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
    // Add hours if there
    if (hours != 0) {
        finalTimerString = "$hours:"
    }

    // Pre appending 0 to seconds if it is one digit
    secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        "" + seconds
    }
    finalTimerString = "$finalTimerString$minutes:$secondsString"

    // return timer string
    return finalTimerString
}