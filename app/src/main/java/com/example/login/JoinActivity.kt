package com.example.login

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    // 중복체크 버튼
    fun onClickDoubleCheck(view: View) {}

    // 회원가입버튼
    fun onClickSign(view: View) {
        dbHelper = DBHelper(this, "member.db", null, 1)
        database = dbHelper.writableDatabase

        var query = "INSERT INTO person('id', 'pw', 'name') values('${etId.text.toString()}', '${etPw.text.toString()}', '${etName.text.toString()}');"
        database.execSQL(query)
        Toast.makeText(this, "가입되었습니다.\n다시 로그인해주세요.", Toast.LENGTH_SHORT).show()

        finish()
    }
}