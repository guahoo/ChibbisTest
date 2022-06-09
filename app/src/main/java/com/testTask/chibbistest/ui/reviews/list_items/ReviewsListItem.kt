package com.testTask.chibbistest.ui.reviews.list_items

import android.annotation.SuppressLint
import com.testTask.chibbistest.R
import com.testTask.chibbistest.data.models.ReviewModel
import com.testTask.chibbistest.extensions.formatToDateToString
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.review_list_item.view.*

class ReviewsListItem (private val reviewModel: ReviewModel): Item() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
       viewHolder.itemView.apply {

           val reviewLabelText = "${reviewModel.UserFIO} о ресторане ${reviewModel.RestaurantName}"

           tv_review_label.text = reviewLabelText
           tv_review_content.text = reviewModel.Message
           tv_review_date.text = formatToDateToString(reviewModel.DateAdded)

           if (!reviewModel.IsPositive) iv_rating_label.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_thumb_down_24))
       }
    }

    override fun getLayout() = R.layout.review_list_item
}