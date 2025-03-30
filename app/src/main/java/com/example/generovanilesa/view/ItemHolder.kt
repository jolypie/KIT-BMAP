package com.example.generovanilesa.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.generovanilesa.R
import com.example.generovanilesa.Item
import com.example.generovanilesa.Icons

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageItem: ImageView = itemView.findViewById(R.id.imageItem)

    fun setImageItem(item: Item) {
        imageItem.setImageResource(Icons.get(item.name))
    }
}
