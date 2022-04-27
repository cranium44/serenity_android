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