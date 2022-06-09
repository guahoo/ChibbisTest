package com.testTask.chibbistest.ui.reviews

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.testTask.chibbistest.R
import com.testTask.chibbistest.data.models.ReviewModel
import com.testTask.chibbistest.extensions.gone
import com.testTask.chibbistest.extensions.show
import com.testTask.chibbistest.ui.reviews.list_items.ReviewsListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_review.error_layout
import kotlinx.android.synthetic.main.fragment_review.rw_rest
import kotlinx.android.synthetic.main.fragment_review.swiperefreshlayout


class ReviewFragment : Fragment() {

    companion object {
        fun newInstance() = ReviewFragment()
    }

    private lateinit var viewModel: ReviewViewModel
    private val reviewAdapter = GroupAdapter<GroupieViewHolder>()

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.isRefreshing.postValue(true)
        viewModel.loadListReviews()
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.isRefreshing.postValue(false) }, 1000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        rw_rest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
        }

        viewModel.listReviews.observe(viewLifecycleOwner) {
            reviewAdapter.clear()
            reviewAdapter.addAll(it.toReviewListItem())
        }

        viewModel.needShowErrorScreen.observe(viewLifecycleOwner) {
            if (it) error_layout.show() else error_layout.gone()
        }

        swiperefreshlayout.setOnRefreshListener(refreshListener)


        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            swiperefreshlayout.isRefreshing = it
        }

    }

    private fun List<ReviewModel>.toReviewListItem(): List<ReviewsListItem>{
        return map { reviewModel ->
            ReviewsListItem(reviewModel)
        }
    }

    override fun onResume() {
        viewModel.loadListReviews()
        super.onResume()
    }

}