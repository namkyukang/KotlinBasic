package com.androidhuman.example.simplecontacts

import android.app.Application

import io.realm.Realm

class SimpleContactsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)   //Realm은 반드시 init을 해주고 시작해야한다. manifest에서 이 클래스를 참조하도록 추가해줘야한다.
        //android:name="com.androidhuman.example.simplecontacts.SimpleContactsApp"
        //앱이 실행 되는 동안 계속해서 Realm이 필요하기 때문에 따로 클래스를 만들어 참조하게 만들었따.
    }
}
