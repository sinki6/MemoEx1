package kanghan.example.memoex1.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kanghan.example.memoex1.R
import kanghan.example.memoex1.adapter.MainMemoRVAdapter
import kanghan.example.memoex1.model.MemoData
import kanghan.example.memoex1.utils.MySharedPreferences
import kotlinx.android.synthetic.main.fragment_calendar.*
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    private val calendarMemoRVAdapter by lazy {
        MainMemoRVAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarView = calendar_view
        setUpCalendar(calendarView)
        getMemoData(calendarView)

        calendarView.onDateClickListener = { date ->
            val items = MySharedPreferences.LoadData(context!!)
            val itemsExtraction = mutableListOf<MemoData>()
            items.forEach {
                if (CalendarDate(it.savedTime) == date ) {
                    itemsExtraction.add(it)
                }
            }
            recyclerViewMemoCalendar.adapter = calendarMemoRVAdapter
            calendarMemoRVAdapter.setItems(itemsExtraction)


        }
    }


    private fun setUpCalendar(calendarView: CalendarView) {
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
        calendarView.datesIndicators
    }

    private fun getMemoData(calendarView: CalendarView) {
        val items = MySharedPreferences.LoadData(context!!)
        val listIndicatorMemo = mutableListOf<DateIndicatorMemo>()
        items.forEach {
            val calendarDate = CalendarDate(it.savedTime)
            listIndicatorMemo.add(DateIndicatorMemo(calendarDate, it.color))
        }
        calendarView.datesIndicators = listIndicatorMemo
    }

    inner class DateIndicatorMemo(date: CalendarDate, color: Int) : CalendarView.DateIndicator{ // CalendarView.DateIndicator인터페이스를 상속한 class정의
        override val date: CalendarDate =date
        override val color: Int = color
    }

    companion object {
        fun newInstance(): Fragment =
            CalendarFragment()
    }
}