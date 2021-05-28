package org.eveh.plantdetector.ui.gallery_detector

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.eveh.plantdetector.R
import org.eveh.plantdetector.databinding.LayoutCoincidenceBinding
import org.eveh.plantdetector.tflite.Classifier

class CoincidencesAdapter(private val results: ArrayList<Classifier.Recognition>, private val context: Context): RecyclerView.Adapter<CoincidencesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutCoincidenceBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(data: Classifier.Recognition){
            binding.title.text = data.title
            var p: Float = String.format("%.2f", data.confidence.times(100)).toFloat()
            binding.coincidence.text = "${p}%"
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