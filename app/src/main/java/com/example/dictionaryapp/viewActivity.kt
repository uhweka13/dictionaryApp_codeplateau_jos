package com.example.dictionaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dictionaryapp.helpers.DatabaseHelper
import com.example.dictionaryapp.model.Words
import kotlinx.android.synthetic.main.activity_view.*
import android.os.CountDownTimer



class viewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val id = intent.getIntExtra("id",-1)
        val words = intent.getStringExtra("word")
        val wordMeaning = intent.getStringExtra("meaning")
        var btnback = findViewById<Button>(R.id.bt_view_back)
        var tv_word_TextView = findViewById<TextView>(R.id.tv_word_show)
        var tv_meaning_TextView = findViewById<TextView>(R.id.tv_meaning_show)
        var deleteButton = findViewById<Button>(R.id.btn_delete)
        var btn_view_edit_word = findViewById<Button>(R.id.btn_view_edit)

        tv_word_TextView.setText(words)
        tv_meaning_TextView.setText(wordMeaning)

        btnback.setOnClickListener(View.OnClickListener {
            backToMainactivity()
        })

        deleteButton.setOnClickListener(View.OnClickListener {
            deleteWord()
        })

        btn_view_edit_word.setOnClickListener(View.OnClickListener {
            toEditWord()
        })


    }

    fun backToMainactivity(){
        val toMainActivity = Intent(this, MainActivity::class.java)
        startActivity(toMainActivity)
        finish()
    }

    fun toEditWord(){
        val toWordEdit = Intent(this, EditTextActivity::class.java)


            val id = intent.getIntExtra("id",-1)
            val words = intent.getStringExtra("word")
            val wordMeaning = intent.getStringExtra("meaning")
//            val toWordEdit = Intent(this, editwordsActivity::class.java)


            //sends id, name, email, address and password to the next page
            toWordEdit.putExtra("id", id)
            toWordEdit.putExtra("word",words)
            toWordEdit.putExtra("meaning", wordMeaning)


            startActivity(toWordEdit)
            finish()

    }


    fun deleteWord(){

        val id = intent.getIntExtra("id",-1)
        //create the database helper instance
        val db_helper = DatabaseHelper(this)

        val word = Words(id = id, wordT = "", meaning = "")
        db_helper.deleteWord(word)

        //redirect the user to MainActivity
        val toMain = Intent(this,MainActivity::class.java)
//        toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(toMain)
        finish()

        Toast.makeText(this,"Word Deleted Successfully", Toast.LENGTH_LONG)
            .show()



    }

}
