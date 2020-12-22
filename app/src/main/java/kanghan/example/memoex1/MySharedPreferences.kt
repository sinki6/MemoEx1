package kanghan.example.memoex1

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kanghan.example.memoex1.model.MemoData

object MySharedPreferences {


    // 확장 함수로도 바꿀수 있을까?
    fun SaveData(context: Context, memoData: MutableList<MemoData>) {
        val prefs: SharedPreferences = context.getSharedPreferences("prefs", 0)
        val editor = prefs.edit()
        val gson = GsonBuilder().create()
        val json = gson.toJson(memoData)
        editor.putString("prefs", json)
        editor.commit()
    }

    fun LoadData(context: Context): MutableList<MemoData> {
        val prefs: SharedPreferences = context.getSharedPreferences("prefs", 0)
        val gson = GsonBuilder().create()
        val defaultData: MutableList<MemoData> = arrayListOf(
            MemoData("NoData", "NoData")
        )
        val defaultDataJson = gson.toJson(defaultData)
        val json = prefs.getString("prefs", defaultDataJson)
        val type = object : TypeToken<MutableList<MemoData>>() {}.type
        val memodata = gson.fromJson<MutableList<MemoData>>(json, type)
        return memodata
    }

}