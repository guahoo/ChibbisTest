package com.testTask.chibbistest.ui.hits.list_items

import com.testTask.chibbistest.R
import com.testTask.chibbistest.data.models.HitModel
import com.testTask.chibbistest.extensions.cornerImage
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.hit_list_item.view.*


class HitListItem (private val hitModel: HitModel): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
       viewHolder.itemView.apply {
           iv_hit_logo.cornerImage(hitModel.ProductImage, 5)
           tv_hit_label.text = hitModel.ProductName
       }
    }

    override fun getLayout() = R.layout.hit_list_item
}