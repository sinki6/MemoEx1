package kanghan.example.memoex1.model

import java.io.Serializable

data class MemoData(
    var memoTitle: String,
    var memoContent: String,
    var isFavorite: Boolean,
    val savedTime: Long,
    val color: Int
) : Serializable