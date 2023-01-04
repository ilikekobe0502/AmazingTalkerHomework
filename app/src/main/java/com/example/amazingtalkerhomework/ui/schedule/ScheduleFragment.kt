package com.example.amazingtalkerhomework.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.amazingtalkerhomework.R
import com.example.amazingtalkerhomework.databinding.FragmentCalendarBinding
import com.example.amazingtalkerhomework.model.data.Schedule
import com.example.amazingtalkerhomework.ui.MainViewModel
import com.example.amazingtalkerhomework.ui.schedule.CalendarPagerAdapter.Companion.ARG_TIME_PRIOR
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ScheduleFragment(private val scrollListener: OnScrollListener) : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val listAdapter = ScheduleRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        binding.rvContent.removeOnScrollListener(scrollListener)
        _binding = null
        super.onDestroyView()
    }

    fun updateData(schedule: Schedule) {
        listAdapter.setData(schedule.priorList)
    }

    private fun initView() {
        binding.rvContent.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
            addOnScrollListener(scrollListener)
        }
        listAdapter.setData((arguments?.getSerializable(ARG_TIME_PRIOR) as Schedule).priorList)
    }
}