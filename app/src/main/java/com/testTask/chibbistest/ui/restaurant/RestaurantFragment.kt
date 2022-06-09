package com.testTask.chibbistest.ui.restaurant

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
import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.extensions.gone
import com.testTask.chibbistest.extensions.show
import com.testTask.chibbistest.ui.restaurant.list_items.RestaurantListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_restaurant.*


class RestaurantFragment : Fragment() {

    companion object {
        fun newInstance() = RestaurantFragment()
    }

    private lateinit var viewModel: RestaurantViewModel

    private val restAdapter = GroupAdapter<GroupieViewHolder>()
    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.isRefreshing.postValue(true)
        viewModel.loadListRestaurant()
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.isRefreshing.postValue(false) }, 1000)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)




        rw_rest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = restAdapter
        }

        viewModel.listRestaurant.observe(viewLifecycleOwner) {
            restAdapter.clear()
            restAdapter.addAll(it.toRestaurantListItem())
        }

        viewModel.needShowErrorScreen.observe(viewLifecycleOwner) {
            if (it) error_layout.show() else error_layout.gone()
        }

        swiperefreshlayout.setOnRefreshListener(refreshListener)


        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            swiperefreshlayout.isRefreshing = it
        }

    }

    override fun onResume() {
        viewModel.loadListRestaurant()
        super.onResume()
    }

    private fun List<RestModel>.toRestaurantListItem(): List<RestaurantListItem>{
        return map { restModel ->
            RestaurantListItem(restModel)
        }
    }

}