package kanghan.example.memoex1.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import kanghan.example.memoex1.adapter.MainMemoRVAdapter
import kanghan.example.memoex1.utils.MySharedPreferences
import kanghan.example.memoex1.R
import kanghan.example.memoex1.activity.EditActivity
import kanghan.example.memoex1.adapter.ViewpagerAdapter
import kanghan.example.memoex1.databinding.FragmentMemoBinding
import kanghan.example.memoex1.model.MemoData
import kotlinx.android.synthetic.main.fragment_memo.*
import java.util.*

class MemoFragment : Fragment() {
    private lateinit var binding: FragmentMemoBinding
    private val mainMemoRVAdapter by lazy {
        MainMemoRVAdapter { position: Int, memoData: MemoData ->
            val editIntent = startIntent(activity!!, true, position)
            startActivityForResult(editIntent, 101)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_memo,container,false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRVAdapter()
        getMemoList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == 91 || resultCode == 92 || resultCode == 93) {
                getMemoList()
            }
        }
    }
    private fun setRVAdapter() {
        binding.rvMemo.adapter = mainMemoRVAdapter
    }

    fun addBtn(){
        val addIntent = startIntent(activity!!, false,0)
        startActivityForResult(addIntent, 101)
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

    companion object {
        fun newInstance(): Fragment =
            MemoFragment()
    }
}