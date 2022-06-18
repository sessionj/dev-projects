package kr.co.std.model

import android.util.Log

/**
 * @authar shadow
 * @email _session@kakao.com
 * @created 2022-06-18
 * @desc
 */

// 옵셔널 : 빈값이여도 좋다 ~
class MyModel(val name: String? = null, val profileImage: String? = null) {

    val TAG: String = "로그"

    init {
        Log.d(TAG, "MyModel - init() called")
    }

}