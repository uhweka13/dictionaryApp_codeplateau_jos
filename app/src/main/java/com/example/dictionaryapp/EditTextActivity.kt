package com.example.dictionaryapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionaryapp.helpers.DatabaseHelper
import com.example.dictionaryapp.model.Words

import kotlinx.android.synthetic.main.activity_edit_text.*

class EditTextActivity : AppCompatActivity() {

    private lateinit var et_word: EditText
    private lateinit var et_meaning: EditText

    //global strings varriable
    private lateinit var update_word:String
    private lateinit var update_meaning:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)
        setSupportActionBar(toolbar)


        //get values sent from user activity
        val id = intent.getIntExtra("id",-1)
        val word = intent.getStringExtra("word")
        val meaning = intent.getStringExtra("meaning")
        var tvEditWord = findViewById<TextView>(R.id.tv_edit_word)
        var tvEditMeaning = findViewById<TextView>(R.id.tv_edit_meaning)

        //link the global varriables to xml element
        et_word = findViewById(R.id.et_edit_words)
        et_meaning = findViewById(R.id.et_edit_meaning)

        //set text to edit text
        et_word.setText(word)
        et_meaning.setText(meaning)

        //set textview as well
        tvEditWord.setText(word)
        tvEditMeaning.setText(meaning)

        var btnEditWord = findViewById<Button>(R.id.bt_edittext_btn)
        var btnUpdate = findViewById<Button>(R.id.btn_update_word)

        btnEditWord.setOnClickListener(View.OnClickListener {
            backtomain()
        })

        //update words button
        btnUpdate.setOnClickListener(View.OnClickListener {
            updateWord()
        })

        et_meaning.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                tvEditMeaning.text = et_meaning.text.toString().trim()
            }

        })
    }

    fun backtomain(){
        val toMainActivity = Intent(this, MainActivity::class.java)
        startActivity(toMainActivity)
        finish()
    }

    fun updateWord(){
        // get updated values that the user changed from the update user layout
        update_word = et_word.text.toString().trim()
        update_meaning= et_meaning.text.toString().trim()

        if(update_word.isEmpty()){
            et_word.setError("Empty Field")
        }else if(update_meaning.isEmpty()){
            et_meaning.setError("Empty Field")
        }else{
            //create the database helper instance
            val db_helper = DatabaseHelper(this)
            val id = intent.getIntExtra("id",-1)

            //insert the updated values to the Words class object
            val words = Words(id = id, wordT = update_word, meaning = update_meaning)

            //call the update user function of the Database helper to update the user's data
            db_helper.updateWord(words)

            //redirect the user to MainActivity
            val toUpdate = Intent(this, MainActivity::class.java)

            toUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            toUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            toUpdate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(toUpdate)
            finish()
        }

    }

}
