package com.example.deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class TwitterProfileAdapter(private val twitterProfileList: List<TwitterProfile>) :
    RecyclerView.Adapter<TwitterProfileAdapter.TwitterProfileViewHolder>() {

    inner class TwitterProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView
        val descriptionTextView: TextView
        val userNameTsTextView: TextView
        val photoImageView: ImageView
        val btnFollow: Button
        init {
            userNameTextView = view.findViewById(R.id.id_user_name)
            descriptionTextView = view.findViewById(R.id.id_description)
            userNameTsTextView = view.findViewById(R.id.id_user_name_TS)
            photoImageView = view.findViewById(R.id.id_user_photo)
            btnFollow = view.findViewById(R.id.id_btn_follow)
        }
    }

    //pasa el layout para pintar cada item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterProfileViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_twitter_profile_view, parent, false)
        return TwitterProfileViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return twitterProfileList.size
    }

    //asignar los datos a cada item del recycler view
    override fun onBindViewHolder(holder: TwitterProfileViewHolder, position: Int) {
        val actualTwitterProfile = this.twitterProfileList[position]
        holder.userNameTextView.text = actualTwitterProfile.userName
        holder.userNameTsTextView.text = actualTwitterProfile.userNameTS
        holder.descriptionTextView.text  = actualTwitterProfile.descripcion
        holder.photoImageView.setImageResource(actualTwitterProfile.photo)
    }

}