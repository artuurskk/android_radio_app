package com.example.radio_aplikacija

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PowerManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.radio_aplikacija.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var playBtn: ImageView;
    var ehr_link: String = "https://stream.ehrhiti.lv:8000/Stream_34.mp3"
    var lr2_link: String = "http://lr2mp1.latvijasradio.lv:8002/;"
    var rtev_link: String = "http://stream.radiotev.lv:8002/radiov"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_ehr, R.id.nav_lr2, R.id.nav_rtev
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        playBtn = findViewById(R.id.playBtn);

        playRadio(ehr_link)

        playBtn.setOnClickListener {
            if(!mediaPlayer.isPlaying) {
                mediaPlayer.start();
                playBtn.setImageResource(R.drawable.ic_baseline_pause_24);
            } else {
                mediaPlayer.pause();
                playBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        playBtn.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer.stop()

        return when (item.itemId) {
            R.id.nav_ehr -> {
                playRadio(ehr_link)
                true
            }
            R.id.nav_lr2 -> {
                playRadio(lr2_link)
                true
            }
            R.id.nav_rtev -> {
                playRadio(rtev_link)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun playRadio(audioUrl: String) {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                setDataSource(audioUrl)
            } catch (e: IOException) {
                e.printStackTrace();
            }
            prepareAsync()
            start()
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        }

        if(!mediaPlayer.isPlaying) {
            mediaPlayer.start();
            playBtn.setImageResource(R.drawable.ic_baseline_pause_24);
        } else {
            mediaPlayer.pause();
            playBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }
}