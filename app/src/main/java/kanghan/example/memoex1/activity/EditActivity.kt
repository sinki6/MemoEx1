package kanghan.example.memoex1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import kanghan.example.memoex1.utils.MySharedPreferences
import kanghan.example.memoex1.utils.MySharedPreferences.SaveData
import kanghan.example.memoex1.R
import kanghan.example.memoex1.databinding.ActivityEditBinding
import kanghan.example.memoex1.model.DayOfWeek
import kanghan.example.memoex1.model.MemoData
import kanghan.example.memoex1.utils.toEditable
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.item_recycler_view.*
import java.util.*

class EditActivity : AppCompatActivity() { //저장, 삭제, 데이터수정 시 호출

    private val editable by lazy {
        intent.getBooleanExtra("editable", true)
    }
    private val dataIdx by lazy {
        intent.getIntExtra("dataIdx", 0)
    }
    private lateinit var binding: ActivityEditBinding

    /** *
     * 1. keyword : Observer 패턴
     * 2. set() 통해 값을 변경하면 구독하고 있는 뷰들이 자연스럽게 바뀜.
     * 3. get() 을 통해 저장되어 있는 값을 가져 올 수 있음.
     * 4. addOnPropertyChangedCallback() 함수를 통해 값을 변화할때 마다 로그를 찍어 볼 수 있음.
     */
    val memoTitleObservable: ObservableField<String> = ObservableField("")
    val memoContentsObservable: ObservableField<String> = ObservableField("")

    var isFavoriteMemo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        binding.editActivity = this

        if (editable) {//수정 화면
            val memoData = MySharedPreferences.LoadData(this)[dataIdx]
            /** 기존에 저장소에 저장되어있던 메모 데이터를 가져와 동기화 처리 */
            memoTitleObservable.set(memoData.memoTitle)
            memoContentsObservable.set(memoData.memoContent)
            isFavoriteMemo = memoData.isFavorite
            if (memoData.isFavorite) binding.ivFavorite.setImageResource(R.drawable.ic_star_fill)
            else binding.ivFavorite.setImageResource(R.drawable.ic_star_blank)

        } else {//저장 화면
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
                    // 초기 값을 "" (공백값) 으로 지정 했기 때문에 !! 연산자를 사용가능
                    memoTitle = memoTitleObservable.get()!!
                    memoContent = memoContentsObservable.get()!!
                    isFavorite = isFavoriteMemo
                }
                SaveData(this, memoDataList)
                setResult(91)
                finish()
            } else { // 데이터  추가
                val memoDataToAppend =
                    MemoData(
                        memoTitleObservable.get()!!,
                        memoContentsObservable.get()!!,
                        isFavoriteMemo,
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

    fun backActivity() {
        finish()//back 버튼 구현
    }

    fun changeFavorite() {
        if (isFavoriteMemo) {
            isFavoriteMemo = false
            binding.ivFavorite.setImageResource(R.drawable.ic_star_blank)
        } else {
            isFavoriteMemo = true
            binding.ivFavorite.setImageResource(R.drawable.ic_star_fill)
        }
    }
}