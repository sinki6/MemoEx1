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
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.util.*

class CalendarFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCalendar()

        val indicators: List<CalendarView.DateIndicator> = getDatesIndicators()
        calendarView.datesIndicators = indicators

        getMemoData()

    }
    private fun setUpCalendar() {
        val calendarView = calendar_view
        val calendar = Calendar.getInstance()

        //Initial date
        //calendar.set(2018, Calendar.JUNE, 1)
        val initialDate = CalendarDate(calendar.time)

        //Minimum available date
        calendar.set(2018,Calendar.JANUARY,1)
        val minDate = CalendarDate(calendar.time)

        //Maximum availabe date
        calendar.set(2040, Calendar.DECEMBER, 31)
        val maxDate = CalendarDate(calendar.time)

        //List of preselected dates that will be initially selected
        //val preselectedDates: List<CalendarDate> =getPreselectedDates()

        val firstDayOfWeek = java.util.Calendar.SUNDAY

        calendarView.setupCalendar(
                initialDate = initialDate,
                minDate = minDate,
                maxDate = maxDate,
                selectionMode = CalendarView.SelectionMode.NONE,
                selectedDates = emptyList(),
                firstDayOfWeek = firstDayOfWeek,
                showYearSelectionView = true
        )

    }

    private fun getMemoData() {

        val indicators: List<CalendarView.DateIndicator> = getDatesIndicators()
        calendarView.datesIndicators = indicators

//        interface DateIndicator {
//            val date: CalendarDate // indicator date
//            val color: Int // indicator color
//        }

        val items = MySharedPreferences.LoadData(context!!)
        items.forEach {
            MutableList<CalendarView.DateIndicator>().add( CalendarView.DateIndicator(it.savedTimeInMills, it.color))
        }
    }
    class DateIndicatorMemo: CalendarView.DateIndicator{
        override val color: Int
            get() = TODO("Not yet implemented")
        override val date: CalendarDate
            get() = TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(): Fragment =
            CalendarFragment()
    }
}