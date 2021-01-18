package kanghan.example.memoex1.fragment

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
import java.util.*

class CalendarFragment : Fragment() {

    private val calendarMemoRVAdapter by lazy {
        MainMemoRVAdapter { position: Int, memoData: MemoData ->
            // TODO 클릭 했을 때 화면 전환
        }
    }

    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView = calendar_view
        setUpCalendar(calendarView) // 달력 설정값 기준 호출


        //달력 날짜 클릭 시, 하기 창에 메모 내용 호출
        calendarView.onDateClickListener = { date ->
            val items = MySharedPreferences.LoadData(context!!)
            val itemsExtraction = mutableListOf<MemoData>()
            items.forEach {
                if (CalendarDate(it.savedTime) == date) {
                    itemsExtraction.add(it)
                }
            }
            recyclerViewMemoCalendar.adapter = calendarMemoRVAdapter
            calendarMemoRVAdapter.setItems(itemsExtraction)
        }
    }

    override fun onResume() {
        super.onResume()
        getMemoData(calendarView) // 달력 메모 기준dot표시 -> refresh필요
    }

    private fun setUpCalendar(calendarView: CalendarView) {
        val calendar = Calendar.getInstance()
        //Initial date
        //calendar.set(2018, Calendar.JUNE, 1)
        val initialDate = CalendarDate(calendar.time)
        //Minimum available date
        calendar.set(2018, Calendar.JANUARY, 1)
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

    private fun getMemoData(calendarView: CalendarView) {
        val items = MySharedPreferences.LoadData(context!!)
        val listIndicatorMemo = mutableListOf<DateIndicatorMemo>()
        items.forEach {
            val calendarDate = CalendarDate(it.savedTime)
            listIndicatorMemo.add(DateIndicatorMemo(calendarDate, it.color))
        }
        calendarView.datesIndicators = listIndicatorMemo
    }

    inner class DateIndicatorMemo(override val date: CalendarDate, override val color: Int) :
        CalendarView.DateIndicator { // CalendarView.DateIndicator인터페이스를 상속한 class정의
    }

    companion object {
        fun newInstance(): Fragment =
            CalendarFragment()
    }
}