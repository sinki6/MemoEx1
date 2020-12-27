package kanghan.example.memoex1.model

import android.graphics.Color


enum class DayOfWeek(val color: Int) {
    SUNDAY(Color.RED,),
    MONDAY(Color.BLUE),
    TUESDAY(Color.CYAN),
    WEDNESDAY(Color.DKGRAY),
    THURSDAY(Color.MAGENTA),
    FRIDAY(Color.YELLOW),
    SATURDAY(Color.GREEN);
}