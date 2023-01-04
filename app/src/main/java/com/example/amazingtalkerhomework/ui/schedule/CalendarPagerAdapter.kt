package com.example.amazingtalkerhomework.ui.schedule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.amazingtalkerhomework.model.data.Schedule

class CalendarPagerAdapter(
    private val scrollListener: RecyclerView.OnScrollListener,
    private val fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(
        fragmentManager,
        lifecycle
    ) {
    private var list: List<Schedule> = arrayListOf()

    fun setData(data: List<Schedule>) {
        list = data
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        fragmentManager.findFragmentByTag("f$position")?.let {
            (it as ScheduleFragment).updateData(list[position])
        }
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val data = list[position]
        val fragment = ScheduleFragment(scrollListener)
        fragment.arguments = Bundle().apply {
            putSerializable(ARG_TIME_PRIOR, data)
        }
        return fragment
    }

    companion object {
        const val ARG_TIME_PRIOR = "time_prior"
    }
}