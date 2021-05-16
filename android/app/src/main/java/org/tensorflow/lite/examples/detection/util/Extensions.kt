package org.tensorflow.lite.examples.detection.util

import android.app.Activity
import android.util.Log
import android.widget.Toast

fun Activity.toastShort(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
fun Activity.logd(any: Any?){
    Log.d("DEBUGGG", any.toString())
}