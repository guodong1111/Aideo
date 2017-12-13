package com.guodong.avideo.feature.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.guodong.avideo.R
import com.guodong.avideo.pojo.Video
import kotlinx.android.synthetic.main.activity_video.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.net.Uri


/**
 * Created by Guodong on 2017/10/1.
 */
class VideoActivity : AppCompatActivity() {
    val videoModel by lazy { VideoModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val video: Video = intent.extras.getParcelable(INTENT_VIDEO)
        videoProgressBar.visibility = View.VISIBLE
        doAsync{
            val videoUri = videoModel.getVideoUri(video)
            uiThread {
                videoProgressBar.visibility = View.GONE
                if(null != packageManager.getLaunchIntentForPackage("com.mxtech.videoplayer.ad") ||
                   null != packageManager.getLaunchIntentForPackage("com.mxtech.videoplayer.pro")) {
                    startVideoIntent(videoUri)
                    finish()
                }else {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mxtech.videoplayer.ad")));
                    } catch (e: Exception) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad")));
                    }
                }
            }
        }
    }

    private fun startVideoIntent(videoUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(videoUri, "video/m3u8");
        startActivity(intent);
    }

    companion object {

        private val INTENT_VIDEO = "video"

        fun newIntent(context: Context, video: Video): Intent {
            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(INTENT_VIDEO, video)
            return intent
        }
    }

}