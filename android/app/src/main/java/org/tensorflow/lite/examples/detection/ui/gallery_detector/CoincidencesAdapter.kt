package org.tensorflow.lite.examples.detection.ui.gallery_detector

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detection.databinding.LayoutCoincidenceBinding
import org.tensorflow.lite.examples.detection.tflite.Classifier

class CoincidencesAdapter(private val results: ArrayList<Classifier.Recognition>): RecyclerView.Adapter<CoincidencesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutCoincidenceBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(data: Classifier.Recognition){
            binding.title.text = data.title
            binding.coincidence.text = data.confidence.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = LayoutCoincidenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(results[position])
    }

    override fun getItemCount(): Int = results.size
}