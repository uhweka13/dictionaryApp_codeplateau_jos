package com.example.dictionaryapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.helpers.DatabaseHelper
import com.example.dictionaryapp.helpers.WordsRecyclerAdapter
import com.example.dictionaryapp.model.Words

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var allWordsRecycler: RecyclerView
    private lateinit var listView: MutableList<Words>
    private lateinit var recyclerAdapter: WordsRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        allWordsRecycler = findViewById<View>(R.id.all_users_recycler) as RecyclerView
        listView = ArrayList()
        recyclerAdapter = WordsRecyclerAdapter(listView, this)

        //val mLayoutManager = LinearLayoutManager(this)
        allWordsRecycler.layoutManager = LinearLayoutManager(this)
        var searchIt = findViewById<EditText>(R.id.et_search)

        allWordsRecycler.setHasFixedSize(true)

        //links your recycler adapter class to the the recyclerview on your xml file
        allWordsRecycler.adapter = recyclerAdapter


        databaseHelper = DatabaseHelper(this)


        GetDataFromSQLite().execute()
        var tv_test = findViewById<TextView>(R.id.tv_heading)



        searchIt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

//                tv_test.text = searchIt.text.toString().trim()
            }

        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId

        if (id == R.id.action_add_words) {
            addWords()
        }

        return super.onOptionsItemSelected(item)

    }

    fun addWords() {
        val add_words_activity = Intent(this, AddwordsActivity::class.java)
        startActivity(add_words_activity)
        finish()
    }

    //this class is to fetch all user record from SQLite without lagging
    inner class GetDataFromSQLite : AsyncTask<Void, Void, List<Words>>() {



        override fun doInBackground(vararg p0: Void?): List<Words> {
            return databaseHelper.fetchWords()

        }

        override fun onPostExecute(result: List<Words>?) {
            super.onPostExecute(result)

            listView.clear()

            listView.addAll(result!!)
        }

    }

    private fun filter(text: String) {
        //array list which will hold the filtered data
        val filteredWords = ArrayList<Words>()
        //looping through existing elements and adding to filtered words
        listView.filterTo(filteredWords) {
            //if the existing elements contains the search input
            it.wordT.toLowerCase().contains(text.toLowerCase())
        }
        //calling a method of the adapter class and passing the filtered list
        recyclerAdapter!!.filterList(filteredWords)
    }


}
