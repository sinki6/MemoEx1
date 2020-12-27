package kanghan.example.memoex1.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skyhope.eventcalenderlibrary.CalenderEvent
import com.skyhope.eventcalenderlibrary.model.Event
import kanghan.example.memoex1.R
import kanghan.example.memoex1.utils.MySharedPreferences
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarEvent: CalenderEvent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarEvent = calender_event
        getMemoData()
    }

    private fun getMemoData() {
        val items = MySharedPreferences.LoadData(context!!)
        items.forEach {
            val event = Event(it.savedTimeInMills, it.memoTitle, Color.RED)
            calendarEvent.addEvent(event)
        }
    }

    companion object {
        fun newInstance(): Fragment =
            CalendarFragment()
    }
}