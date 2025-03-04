import android.content.Context
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerComposable(context: Context, videoId: String) {
    AndroidView(
        factory = { ctx ->
            val youTubePlayerView = YouTubePlayerView(ctx).apply {
                // Initialize the player here if necessary
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        // Load and play the video
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }
            youTubePlayerView
        },
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    )
}
