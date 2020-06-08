package com.architecture.clean.ui.fragment.home.adapter

import android.content.Context
import com.architecture.clean.R
import com.architecture.clean.core.Config
import com.architecture.clean.domain.model.popular_person.local.PopularPersons
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_movie.view.*

class PopularPersonsAdapter(private val mContext: Context, var data: PopularPersons) : Item<ViewHolder>() {

    override fun getLayout(): Int = R.layout.item_movie

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tvName.text = data.name
            tvTitle.text = data.tile
            tvPopularity.text = data.popularity.toString()
            tvOverview.text = data.tile
            Picasso.with(mContext).load(Config.BASE_IMAGE_URL.plus(data.image)).into(ivImage)
        }
    }
}