package com.example.dictionaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.dictionaryapp.helpers.DatabaseHelper
import com.example.dictionaryapp.model.Words

class AddwordsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addwords)

        var addWords_btn = findViewById<Button>(R.id.bt_addwords_back)
        var btnAddWord = findViewById<Button>(R.id.bt_addwords_submit)
        var addWordsT = findViewById<EditText>(R.id.et_addwords)


        addWords_btn.setOnClickListener(View.OnClickListener {
            backToMain()
        })

        btnAddWord.setOnClickListener(View.OnClickListener {
            inputValidation()
        })
    }

    fun backToMain(){
        val toMainActivity = Intent(this, MainActivity::class.java)
        startActivity(toMainActivity)
        finish()
    }

    fun inputValidation(){
        val addWordsT = findViewById<EditText>(R.id.et_addwords)
        val addWordsMeaning = findViewById<EditText>(R.id.et_words_meaning)

        val addWordsFinal:String = addWordsT.text.toString().trim()
        val addWordsMeaningFinal:String = addWordsMeaning.text.toString().trim()




        if (addWordsFinal.isEmpty()){
            addWordsT.setError("Field Empty")
        } else if (addWordsMeaningFinal.isEmpty()){
            addWordsMeaning.setError("Empty Field")
        }else{
            val firstCharacterS = addWordsFinal.substring(0, 1)

            if (firstCharacterS == "-"){
                addWordsT.setError("Invalid")
            }else if (firstCharacterS == "'"){
                addWordsT.setError("Invalid")
            }else{


                val databaseHelper = DatabaseHelper(this)
//            var timerCountT = findViewById<TextView>(R.id.tv_test)
                var image_show = findViewById<ImageView>(R.id.im_image)

                if (!databaseHelper.checkWord(addWordsFinal)){
                    val word = Words(wordT = addWordsFinal, meaning = addWordsMeaningFinal)
                    databaseHelper.addWords(word)
                    addWordsT.setText("")
                    addWordsMeaning.setText("")



                    object : CountDownTimer(800, 10) {

                        override fun onTick(millisUntilFinished: Long) {
//                        timerCountT.setText("seconds remaining: " + millisUntilFinished / 10)
                            image_show.setImageResource(R.drawable.ic_check)
                            image_show.visibility = View.VISIBLE
                        }

                        override fun onFinish() {
                            image_show.visibility = View.INVISIBLE
                        }
                    }.start()

                    Toast.makeText(this, "Word Added", Toast.LENGTH_LONG).show()
                }else{

                    object : CountDownTimer(800, 10) {

                        override fun onTick(millisUntilFinished: Long) {
//                        timerCountT.setText("seconds remaining: " + millisUntilFinished / 10)
                            image_show.setImageResource(R.drawable.ic_clear)
                            image_show.visibility = View.VISIBLE
                        }

                        override fun onFinish() {
                            image_show.visibility = View.INVISIBLE
                        }
                    }.start()
                    Toast.makeText(this, "Word already exist", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

}
