package org.tensorflow.lite.examples.detection.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.tensorflow.lite.examples.detection.R
import org.tensorflow.lite.examples.detection.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}