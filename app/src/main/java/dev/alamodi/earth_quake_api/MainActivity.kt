package dev.alamodi.earth_quake_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, EarthQuakeFragment.newInstance())
                .commit()
        }
    }
}