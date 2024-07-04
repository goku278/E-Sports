package com.example.esport.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esport.R
import com.example.esport.model.Response
import com.example.esport.model.Turfs
import com.example.esport.ui.home.TurfsListener
import com.example.esport.util.Constant

class TurfsAdapter(
    private val context: Context,
    private val responseList: List<Response>?,
    private val clickListener: TurfsListener
) : RecyclerView.Adapter<TurfsAdapter.ResponseViewHolder>() {

    // ViewHolder class
    inner class ResponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val imageViewIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val llParent: LinearLayout = itemView.findViewById(R.id.llParent)

        private var cardBackground = mutableListOf<Drawable>()

        init {
            ContextCompat.getDrawable(context, R.drawable.blue_background)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.light_blue)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.green_background)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.sky_blue_background)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.orange_background)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.purle_background)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.red_background)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.yellow_background)
                ?.let { cardBackground.add(it) }
            ContextCompat.getDrawable(context, R.drawable.white_background)
                ?.let { cardBackground.add(it) }

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val response = responseList?.get(position)
                    clickListener.onTurfClicked(response)
                }
            }
        }

        // Method to bind the data from a Response object to the views
        fun bind(response: Response, position: Int) {
            tvName.text = response.name
            llParent.background = cardBackground[position]
            // Load the icon into ImageView using Glide or any other image loading library
            Glide.with(context).load(Constant.imageServer + response.icon).into(imageViewIcon)
        }
    }

    // Inflates the item layout and creates the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.turfs_item, parent, false)
        return ResponseViewHolder(view)
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        holder.bind(responseList!![position], position)
    }

    // Returns the size of the list
    override fun getItemCount(): Int {
        return responseList!!.size
    }
}