package kanghan.example.memoex1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import kanghan.example.memoex1.utils.MySharedPreferences
import kanghan.example.memoex1.utils.MySharedPreferences.SaveData
import kanghan.example.memoex1.R
import kanghan.example.memoex1.databinding.ActivityEditBinding
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
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit)
        binding.editActivity = this

        if (editable) {
            val memoData = MySharedPreferences.LoadData(this)[dataIdx]
            binding.memoData = memoData
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
                    memoTitle = binding.memoData!!.memoTitle
                    memoContent = binding.memoData!!.memoContent
                }
                SaveData(this, memoDataList)
                setResult(91)
                finish()
            } else { // 데이터  추가
                val memoDataToAppend =
                    MemoData(binding.memoData!!.memoTitle,
                        binding.memoData!!.memoContent,
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
    }
    fun deleteMemo() {
        val memoDataList =
            MySharedPreferences.LoadData(this)
        memoDataList.removeAt(dataIdx)
        SaveData(this, memoDataList)
        setResult(92)
        finish()
    }
    fun backActivity(){
        val i = 9
        finish()
    }
}