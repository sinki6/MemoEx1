package kanghan.example.memoex1.model

import java.io.Serializable

data class MemoData(
    var memoTitle: String,
    var memoContent: String,
    val savedTime: Long,
    val color: Int
) : Serializable