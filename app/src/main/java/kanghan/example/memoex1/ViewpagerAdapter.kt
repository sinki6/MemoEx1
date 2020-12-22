package kanghan.example.memoex1

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerAdapter(activity: AppCompatActivity, private val lists: List<Fragment>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = lists.size
    override fun createFragment(position: Int): Fragment = lists[position]
}