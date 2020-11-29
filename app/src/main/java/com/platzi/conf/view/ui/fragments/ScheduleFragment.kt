package com.platzi.conf.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.platzi.conf.R
import com.platzi.conf.R.id.rlBaseSchedule
import com.platzi.conf.view.adapter.ScheduleAdapter
import com.platzi.conf.viewmodel.ScheduleViewModel
import com.platzi.conf.R.id.rvSchedule
import com.platzi.conf.model.Conference
import com.platzi.conf.view.adapter.ScheduleListener

/**
 * A simple [Fragment] subclass.
 */
class ScheduleFragment(
    var adapter: ScheduleAdapter,
    var layoutManager: LinearLayoutManager
) : Fragment(), ScheduleListener {
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        viewModel.refresh()

        scheduleAdapter = ScheduleAdapter(this)

        rvSchedule.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = scheduleAdapter
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.listSchedule.observe(this, Observer<List<Conference>>{ schedule ->
            scheduleAdapter.updateData(schedule)
        })

        viewModel.isLoading.observe(this, Observer<Boolean>{
            if(it != null) {
                rlBaseSchedule.visibility = view.INVISIBLE
            }
        })
    }

    override fun onConferenceClicked(conference: Conference, position: RecyclerView.RecyclerListener) {
        val bundle = bundleOf("conference" to conference)
        findNavController().navigate(R.id.scheduleDetailFragmentDialog, bundle)
    }
}