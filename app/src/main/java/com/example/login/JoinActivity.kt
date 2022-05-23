package com.example.login

import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    // 중복체크 버튼
    fun onClickDoubleCheck(view: View) {
        dbHelper = DBHelper(this, "member.db", null, 1)
        database = dbHelper.writableDatabase

        var query = "SELECT id FROM person;"
        var cursor = database.rawQuery(query, null)

        val builder = AlertDialog.Builder(this)

        while(cursor.moveToNext()) {
            var id = cursor.getString(0)

            if(id.equals(etId.text.toString())) { // 중복될 경우
                builder.setTitle("아이디 중복")
                    .setMessage("아이디가 중복됩니다.\n다른 아이디를 입력하세요.")
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener {dialog, id ->
                            etId.text.clear()
                            etId.isFocusable = true
                        })
                    .setNegativeButton("취소", null)
                builder.show()
                break
            }
        }
        // SQLite 다 읽어도 일치하는 아이디가 없다면
        if(cursor.isAfterLast) {
            // 현재 아이디로 할 것인지 다시 물어보기
            builder.setTitle("아이디 확정")
                .setMessage("이 아이디로 하시겠습니까?\n${etId.text.toString()}")
                .setPositiveButton("예",
                DialogInterface.OnClickListener{dialog, id->
                    btnDoubleCheck.isEnabled = false
                    etId.isClickable = false
                    etId.isEnabled = false
                })
                .setNegativeButton("아니요",
                DialogInterface.OnClickListener { dialog, id ->
                    etId.requestFocus()
                })
            builder.show()
        }
    }

    // 회원가입버튼
    fun onClickSign(view: View) {
        dbHelper = DBHelper(this, "member.db", null, 1)
        database = dbHelper.writableDatabase

        if(etId.text.toString() == "" || etPw.text.toString() == "" || etName.text.toString() == "" ) { // 아이디, 비밀번호, 이름 중 입력 안한 게 있을 경우
            showToast("입력칸을 모두 채워주세요.")
        } else { // 아니라면 가입 완료
            if(etId.length() < 4 || etPw.length() < 8) { // 아이디 4자 이상, 비밀번호 8자 이상인지 아닌지를 판별하는 조건문
                check()
            } else if(etId.length() >= 4 || etPw.length() >= 8){
                var query = "INSERT INTO person('id', 'pw', 'name') values('${etId.text.toString()}', '${etPw.text.toString()}', '${etName.text.toString()}');"
                database.execSQL(query)
                showToast("가입되었습니다.\n다시 로그인해주세요.")

                finish()
            }
        }
    }

    // 글자수 적을 경우 사용될 함수
    private fun check() {
        showToast("아이디는 4글자 이상,\n비밀번호는 8글자 이상 입력해주세요.")
    }
    // Toast 메세지 나오는 함수
    private fun showToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}