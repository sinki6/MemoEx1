package kanghan.example.memoex1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import kanghan.example.memoex1.fragment.CalendarFragment
import kanghan.example.memoex1.fragment.MemoFragment
import kanghan.example.memoex1.R
import kanghan.example.memoex1.adapter.ViewpagerAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val fragmentMemo by lazy { MemoFragment.newInstance() }
    private val fragmentCalendar by lazy { CalendarFragment.newInstance() }
    private val viewPagerAdapter: ViewpagerAdapter by lazy {
        ViewpagerAdapter(
            this,
            listOf(fragmentMemo, fragmentCalendar)
        )
    }

    private lateinit var bottomBar: BottomNavigationView
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val binding: MainActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //binding.bottomBar
        //binding.viewPager2
        setContentView(R.layout.activity_main)
        bottomBar = findViewById(R.id.bottom_bar)
        viewPager2 = findViewById(R.id.view_Pager)

        initBottomNavigationView()
        initViewPager()

    }

    private fun initViewPager() {
        viewPager2.run {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val id = when (position) {
                        0 -> R.id.action_one
                        1 -> R.id.action_two
                        else -> R.id.action_one
                    }
                    bottomBar.selectedItemId = id
                }
            })
        }
    }

    private fun initBottomNavigationView() {
        bottomBar.run {
            setOnNavigationItemSelectedListener {
                val position = when (it.itemId) {
                    R.id.action_one -> 0
                    R.id.action_two -> 1
                    else -> 0
                }
                viewPager2.currentItem = position
                true
            }
        }
    }
}