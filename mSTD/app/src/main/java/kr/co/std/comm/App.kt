package kr.co.std.comm

import android.app.Application

/**
 * @authar shadow
 * @email _session@kakao.com
 * @created 2022-06-18
 * @desc
 */

class App : Application(){
    companion object{
        lateinit var instance: App
        private  set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}