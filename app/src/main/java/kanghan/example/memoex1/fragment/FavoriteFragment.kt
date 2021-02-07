package kanghan.example.memoex1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kanghan.example.memoex1.R
import kanghan.example.memoex1.adapter.MainMemoRVAdapter
import kanghan.example.memoex1.adapter.TestMemoRVAdapter
import kanghan.example.memoex1.databinding.FragmentFavoriteBinding
import kanghan.example.memoex1.model.MemoData
import kanghan.example.memoex1.utils.MySharedPreferences
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteMemoRVAdapter by lazy {
        MainMemoRVAdapter { position: Int, memoData: MemoData ->
            // TODO 클릭 햇을 때 화면 전환
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite,container,false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFavoriteMemoRV() // 뷰가 생성된 후에 뷰의 설정값들을 초기화 해주기!
    }

    /** 화면이 보여질 때 마다 호출 됨. */
    override fun onResume() {
        super.onResume()
        getFavoriteMemoData()
    }

    private fun getFavoriteMemoData() {
        val items = MySharedPreferences.LoadData(context!!)
        val favoriteMemoData = mutableListOf<MemoData>()
        items.forEach {
            if (it.isFavorite) {
                favoriteMemoData.add(it)
            }
        }
        favoriteMemoRVAdapter.setItems(favoriteMemoData)
    }

    private fun setFavoriteMemoRV() {
        binding.rvFavoriteMemo.adapter = favoriteMemoRVAdapter
    }

    companion object {
        fun newInstance(): Fragment = FavoriteFragment()
    }
}