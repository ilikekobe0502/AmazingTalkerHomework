package com.example.amazingtalkerhomework.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.amazingtalkerhomework.R
import com.example.amazingtalkerhomework.databinding.ActivityMainBinding
import com.example.amazingtalkerhomework.misc.utils.DateUtil
import com.example.amazingtalkerhomework.model.api.ApiResult
import com.example.amazingtalkerhomework.ui.schedule.CalendarPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : FragmentActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by inject()
    private var dateOffset = 0
    private var todayTabPosition = 0
    private var scrollState: Int = 0
    private val scrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollState = newState
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && (scrollState == 0 || scrollState == 2)) {
                    hideDateTitle()
                } else if (dy < -10) {
                    showDateTitle()
                }
            }
        }
    private val pagerAdapter =
        CalendarPagerAdapter(scrollListener, supportFragmentManager, lifecycle)

    private val viewPagerChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (DateUtil.isBeforeToday(binding.vpCalendar.currentItem + dateOffset) && binding.vpCalendar.currentItem < todayTabPosition) {
                binding.vpCalendar.currentItem = todayTabPosition
            }
            super.onPageSelected(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserve()
        getData()
    }

    private fun getData() {
        viewModel.getSchedule(
            teacherName = DEFAULT_TEACHER,
            time = DateUtil.getFullTimeString(dateOffset),
            dateOffset = dateOffset
        )
    }

    override fun onDestroy() {
        binding.vpCalendar.unregisterOnPageChangeCallback(viewPagerChangeCallback)
        _binding = null
        super.onDestroy()
    }

    private fun initView() {
        setTitleUI()
        setTabLayout()
    }

    private fun setTitleUI() {
        binding.apply {
            ivPrevious.setOnClickListener {
                if (dateOffset != 0 && !binding.pbLoading.isVisible) {
                    dateOffset -= 7
                    getData()
                    updateTitle()
                }
            }
            ivForward.setOnClickListener {
                if (!binding.pbLoading.isVisible) {
                    dateOffset += 7
                    getData()
                    updateTitle()
                }
            }

            updateTitle()
            tvTimezone.text = DateUtil.getDisplayTimezone()
            tvTeacher.text = getString(R.string.main_teacher, DEFAULT_TEACHER)
        }
    }

    private fun updateTitle() {
        val start = dateOffset + 0
        val end = dateOffset + 6
        binding.tvDate.text = getString(
            R.string.main_title_date,
            DateUtil.getDateString(start),
            DateUtil.getDateString(end)
        )
    }

    private fun setTabLayout() {
        binding.vpCalendar.adapter = pagerAdapter
        TabLayoutMediator(binding.tlDate, binding.vpCalendar) { tab, position ->
            val adapter = binding.vpCalendar.adapter
            if (adapter is CalendarPagerAdapter) {
                val tabText = DateUtil.getDayOfWeekString(position + dateOffset)

                binding.tlDate.post {
                    if (DateUtil.isBeforeToday(position + dateOffset)) {
                        binding.tlDate.getTabAt(position)?.view?.isClickable = false
                        binding.tlDate.getTabAt(position)?.view?.setBackgroundColor(
                            ContextCompat.getColor(this, R.color.gray)
                        )
                    } else {
                        binding.tlDate.getTabAt(position)?.view?.isClickable = true
                        binding.tlDate.getTabAt(position)?.view?.setBackgroundColor(
                            ContextCompat.getColor(this, R.color.white)
                        )
                    }
                }

                if (dateOffset >= 7) {
                    binding.vpCalendar.currentItem = 0
                } else if (DateUtil.getTodayOfWeekString() == tabText) {
                    todayTabPosition = position
                    binding.vpCalendar.currentItem = todayTabPosition
                }

                tab.text = tabText
            }
        }.attach()

        binding.vpCalendar.registerOnPageChangeCallback(viewPagerChangeCallback)
    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.scheduleResult.observe(this@MainActivity) { response ->
                    when (response) {
                        is ApiResult.Error -> {
                            Snackbar.make(
                                binding.root,
                                response.throwable.message ?: "",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        is ApiResult.Loading -> {
                            binding.pbLoading.isVisible = true
                        }
                        is ApiResult.Success -> {
                            binding.pbLoading.isVisible = false
                            response.result?.let { list ->
                                pagerAdapter.setData(list)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hideDateTitle() {
        binding.tvDate.isVisible = false
        binding.ivForward.isVisible = false
        binding.ivPrevious.isVisible = false
    }

    private fun showDateTitle() {
        binding.tvDate.isVisible = true
        binding.ivForward.isVisible = true
        binding.ivPrevious.isVisible = true
    }

    companion object {
        private const val DEFAULT_TEACHER = "jamie-coleman"
    }
}