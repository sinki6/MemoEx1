package kanghan.example.memoex1.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import kanghan.example.memoex1.adapter.MainMemoRVAdapter
import kanghan.example.memoex1.utils.MySharedPreferences
import kanghan.example.memoex1.R
import kanghan.example.memoex1.activity.EditActivity
import kanghan.example.memoex1.model.MemoData
import kotlinx.android.synthetic.main.fragment_memo.*

class MemoFragment : Fragment() {
    private val mainMemoRVAdapter by lazy {
        MainMemoRVAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRVAdapter()
        setViewClickListener()
        getMemoList()
    } // Fragment전환때마다 데이터 refresh필요
    // 하단에 recyclerView 구현


    private fun setRVAdapter() {
        recyclerViewMemo.adapter = mainMemoRVAdapter
        mainMemoRVAdapter.setItemClickListener(object :
            MainMemoRVAdapter.ItemClickListener {
            //메모 객체 클릭
            override fun onClick(position: Int, memoData: MemoData) {
                val editIntent = startIntent(activity!!, true, position)
                startActivityForResult(editIntent, 101)
            }
        })
    }

    private fun setViewClickListener() {
        fab_memo_add.setOnClickListener {
            val addIntent = startIntent(activity!!, false, 0)
            //val addIntent2 = Intent(activity, EditActivity::class.java)
            startActivityForResult(addIntent, 101)
        }
    }

    private fun getMemoList() {
        val items = MySharedPreferences.LoadData(context!!)
        mainMemoRVAdapter.setItems(items)
    }

    private fun startIntent(activity: FragmentActivity, editable: Boolean, idx: Int): Intent {
        val intent = Intent(activity, EditActivity::class.java)
        intent.putExtra("editable", editable)
        intent.putExtra("dataIdx", idx)
        return intent
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == 91 || resultCode == 92 || resultCode == 93) {
                getMemoList()
            }
        }
    }

    companion object {
        fun newInstance(): Fragment =
            MemoFragment()
    }
}