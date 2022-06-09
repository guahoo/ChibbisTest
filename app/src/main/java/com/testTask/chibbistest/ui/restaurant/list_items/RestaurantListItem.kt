package com.testTask.chibbistest.ui.restaurant.list_items

import android.view.View
import com.testTask.chibbistest.R
import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.extensions.cornerImage
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.restaurant_list_item.view.*

class RestaurantListItem (val restModel: RestModel): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
       viewHolder.itemView.apply {

           iv_rest_logo.cornerImage(restModel.Logo, 5)
           tv_label.text = restModel.Name
           var specialization = ""

           for (spec in restModel.Specializations){
               specialization = if (specialization != ""){
                   "$specialization / ${spec.Name}"
               } else {
                   spec.Name
               }
           }

           tv_rest_spec.text = specialization

           if (restModel.ReviewsCount == 0){
               iv_rating_label.visibility = View.GONE
           }

           tv_rating.text = if (restModel.ReviewsCount != 0){
               "${restModel.PositiveReviews}%"
           } else "Пока нет отзывов"
       }
    }

    override fun getLayout() = R.layout.restaurant_list_item
}