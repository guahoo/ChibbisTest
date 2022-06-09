package com.testTask.chibbistest.ui.hits

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
import com.testTask.chibbistest.data.models.HitModel
import com.testTask.chibbistest.extensions.gone
import com.testTask.chibbistest.extensions.show
import com.testTask.chibbistest.ui.hits.list_items.HitListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_hit.*
import kotlinx.android.synthetic.main.fragment_hit.error_layout
import kotlinx.android.synthetic.main.fragment_hit.rw_rest



class HitsFragment : Fragment() {

    companion object {
        fun newInstance() = HitsFragment()
    }

    private lateinit var viewModel: HitsViewModel
    private val hitAdapter = GroupAdapter<GroupieViewHolder>()


    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.isRefreshing.postValue(true)
        viewModel.loadListHits()
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.isRefreshing.postValue(false) }, 1000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HitsViewModel::class.java)

        rw_rest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = hitAdapter
        }

        viewModel.listHits.observe(viewLifecycleOwner) {
            hitAdapter.clear()
            hitAdapter.addAll(it.toHitListItem())
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
        viewModel.loadListHits()
        super.onResume()
    }

    private fun List<HitModel>.toHitListItem(): List<HitListItem>{
        return map { hitModel ->
            HitListItem(hitModel)
        }
    }

}