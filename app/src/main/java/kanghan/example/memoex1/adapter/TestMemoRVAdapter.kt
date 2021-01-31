package kanghan.example.memoex1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kanghan.example.memoex1.R
import kanghan.example.memoex1.model.MemoData
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class TestMemoRVAdapter : RecyclerView.Adapter<TestMemoRVAdapter.MyViewHolder>() {

    private val items: MutableList<MemoData> = mutableListOf()
    private var listener: OnMemoClickListener? = null

    fun setItems(items: List<MemoData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnMemoClickListener(onMemoClickListener: OnMemoClickListener) {
        this.listener = onMemoClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        return MyViewHolder(view).apply {
            this.itemView.setOnClickListener {
                listener?.onItemClick()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // apply, also : 안에 내용을 적용하고 그 적용한 상태를 반환한다.
        // let, run(with) : 그 안에 내용 실행 (반환 없음)
        // let 은 보통 ?.let null check 용으로 많이 사용
        items[position].let { item ->
            with(holder) {
                memoTitle.text = item.memoTitle
                memoContent.text = item.memoContent
            }
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val memoTitle: TextView = view.memoTitle
        val memoContent: TextView = view.memoContent
        val itemView = itemView
    }

    interface OnMemoClickListener {
        fun onItemClick()
    }
}