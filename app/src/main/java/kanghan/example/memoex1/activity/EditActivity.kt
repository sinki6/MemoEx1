package kanghan.example.memoex1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kanghan.example.memoex1.utils.MySharedPreferences
import kanghan.example.memoex1.utils.MySharedPreferences.SaveData
import kanghan.example.memoex1.R
import kanghan.example.memoex1.model.DayOfWeek
import kanghan.example.memoex1.model.MemoData
import kanghan.example.memoex1.utils.toEditable
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() { //저장, 삭제, 데이터수정 시 호출

    private val editable by lazy {
        intent.getBooleanExtra("editable", true)
    }

    private val dataIdx by lazy {
        intent.getIntExtra("dataIdx", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        if (editable) {
            val memoData = MySharedPreferences.LoadData(this)[dataIdx]
            editTextMemoTitle.text = memoData.memoTitle.toEditable()
            editTextMemoContent.text = memoData.memoContent.toEditable()
        } else {
            iv_delete.visibility = View.INVISIBLE
        }
        setViewClickListener()
    }

    private fun setViewClickListener() {
        iv_Save.setOnClickListener {
            if (editable) { // 데이터 수정
                val memoDataList =
                    MySharedPreferences.LoadData(this)

                memoDataList[dataIdx].apply {
                    memoTitle = editTextMemoTitle.text.toString()
                    memoContent = editTextMemoContent.text.toString()
                }
                SaveData(this, memoDataList)
                setResult(91)
                finish()
            } else { // 데이터  추가
                val memoDataToAppend =
                    MemoData(
                        editTextMemoTitle.text.toString(),
                        editTextMemoContent.text.toString(),
                        Calendar.getInstance().timeInMillis,
                        DayOfWeek.values()[Calendar.getInstance()
                            .get(Calendar.DAY_OF_WEEK) - 1].color
                    )
                val memoDataList =
                    MySharedPreferences.LoadData(this)
                memoDataList.add(memoDataToAppend)
                SaveData(this, memoDataList)
                setResult(93)
                finish()
            }
        }
        iv_delete.setOnClickListener {
            //삭제할 데이터 번호
            val memoDataList =
                MySharedPreferences.LoadData(this)
            memoDataList.removeAt(dataIdx)
            SaveData(this, memoDataList)
            setResult(92)
            finish()

        }
    }
}