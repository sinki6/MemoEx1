package kanghan.example.memoex1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import kanghan.example.memoex1.MySharedPreferences
import kanghan.example.memoex1.MySharedPreferences.SaveData
import kanghan.example.memoex1.R
import kanghan.example.memoex1.model.MemoData
import kotlinx.android.synthetic.main.activity_edit.*

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

//String확장함수,
class EditActivity : AppCompatActivity() { //저장, 삭제, 데이터수정 시 호출

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editable: Boolean = intent.getBooleanExtra("editable", true)
        val dataIdx = intent.getIntExtra("dataIdx", 0)

        if (editable) {
            val memoData = MySharedPreferences.LoadData(
                    this
            )[dataIdx]
            editTextMemoTitle.text = memoData.memoTitle.toEditable()
            editTextMemoContent.text = memoData.memoContent.toEditable()
        } else {
            iv_delete.visibility = View.INVISIBLE
        }

        iv_Save.setOnClickListener {
            if (editable) { // 데이터 수정
                val memoDataToChange =
                    MemoData(
                        editTextMemoTitle.text.toString(),
                        editTextMemoContent.text.toString()
                    )
                val memoDataList =
                        MySharedPreferences.LoadData(this)
                memoDataList.set(dataIdx, memoDataToChange)
                //데이터 저장소에 저장

                SaveData(this, memoDataList)

                setResult(91)
                finish()
            } else  { // 데이터  추가
                val memoDataToAppend =
                    MemoData(
                        editTextMemoTitle.text.toString(),
                        editTextMemoContent.text.toString()
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