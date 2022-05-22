package com.example.login

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_join.etId
import kotlinx.android.synthetic.main.activity_join.etPw
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // DB
    lateinit var dbHelper: DBHelper
    lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickLogin(view: View) {
        val etInputId = etId.text.toString()
        val etInputPw = etPw.text.toString()

        dbHelper = DBHelper(this, "member.db", null, 1)
        database = dbHelper.writableDatabase

        var query = "SELECT * FROM person;"
        var cursor = database.rawQuery(query, null)
        while(cursor.moveToNext()) {
            var id = cursor.getString(0)
            var pw = cursor.getString(1)
            var name = cursor.getString(2)

            // 공백에러
            if(etInputId == "" || etInputPw == "") {
                Toast.makeText(this, "아이디 또는 비밀번호가 공백입니다.", Toast.LENGTH_SHORT).show()
                break
            }

            // 아이디와 비밀번호가 일치한다면
            if(id.equals(etInputId) && pw.equals(etInputPw)) {
                val intent = Intent(this, CompleteActivity::class.java)

                Toast.makeText(this, "로그인 성공!\n[${name}]님 환영합니다.", Toast.LENGTH_SHORT).show()
                startActivity(intent)

            } else {
                tvFailInf.text = "로그인에 실패했습니다. 다시 시도해주세요."
            }
        }
    }

    fun onClickJoin(view: View) {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }
}