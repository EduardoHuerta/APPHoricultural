package org.eveh.plantdetector.ui.main_menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import org.eveh.plantdetector.DetectorActivity
import org.eveh.plantdetector.R
import org.eveh.plantdetector.data.Constants
import org.eveh.plantdetector.ui.gallery_detector.MainActivity
import org.eveh.plantdetector.databinding.ActivityMenuBinding
import org.eveh.plantdetector.ui.login.LoginActivity
import org.eveh.plantdetector.util.logd
import org.eveh.plantdetector.util.toastShort
import java.io.ByteArrayOutputStream


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE)

        val isLogged: Boolean = sharedPref.getBoolean(Constants.UserData.SESSION_STATUS_KEY, false)

        if (!(isLogged)){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.image.setOnClickListener { showImagePicker() }
        binding.realTime.setOnClickListener { startActivity(Intent(this, DetectorActivity::class.java)) }
        binding.logout.setOnClickListener {
            with (sharedPref.edit()) {
                putBoolean(Constants.UserData.SESSION_STATUS_KEY, false)
                apply()
            }
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun showImagePicker(){
        ImagePicker.with(this)
                .setImageProviderInterceptor { imageProvider -> //Intercept ImageProvider
                    Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name) }
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            toastShort("Hubo un problema")
            logd("Hubo un pedo")
            return
        }
        when(requestCode){
            ImagePicker.REQUEST_CODE -> {
                val fileUri = data?.data
                logd("Uri: $fileUri")
                sendUri(fileUri)
            }
        }
    }

    private fun sendUri(uri: Uri?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.BITMAP_KEY, uri.toString())
        startActivity(intent)
    }

    private fun compressBitmap(bitmap: Bitmap): ByteArray{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes = stream.toByteArray()
        return bytes
    }
}