package kanghan.example.memoex1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kanghan.example.memoex1.R
import kanghan.example.memoex1.model.MemoData
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class MainMemoRVAdapter(private val onItemClick: (Int, MemoData) -> Unit) :
    RecyclerView.Adapter<MainMemoRVAdapter.MyViewHolder>() {

    private val items: MutableList<MemoData> = mutableListOf()

    fun setItems(items: List<MemoData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        return MyViewHolder(view).apply {
            this.itemView.setOnClickListener {
                onItemClick.invoke(adapterPosition, items[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                memoTitle.text = item.memoTitle
                memoContent.text = item.memoContent
            }
        }
    }

    inner class MyViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
        val memoTitle: TextView = itemView.memoTitle
        val memoContent: TextView = itemView.memoContent
    }
}