package com.example.dictionaryapp.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dictionaryapp.model.Words

class DatabaseHelper(context: Context): SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {


    //table to perform our sql query
    private val CREATE_WORD_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_WORD_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WORD_NAME+ " TEXT," + COLUMN_WORD_MEANING+ " TEXT" +")"
            )

    //query to drop table
    private val DROP_WORD_TABLE =" DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase?) {

        if(db != null){
            db.execSQL(CREATE_WORD_TABLE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if (db != null){
            db.execSQL(DROP_WORD_TABLE)
        }
        onCreate(db)
    }

    fun addWords(words: Words){
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_WORD_NAME,words.wordT)
        values.put(COLUMN_WORD_MEANING,words.meaning)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun checkWord(wordT: String) : Boolean{
        //species the Array of column to fetch
        val columns = arrayOf(COLUMN_WORD_ID)
        val db = readableDatabase

        //write selection criteria
        val selection = "$COLUMN_WORD_NAME = ?"

        //write selection argument

        val selectionArgs = arrayOf(wordT)

        val cursor = db.query(TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount>0) {
            return true
        }

        return false
    }

    fun fetchWords(): List<Words> {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_WORD_ID, COLUMN_WORD_NAME, COLUMN_WORD_MEANING)

        // sorting order

        val sortOrder = "$COLUMN_WORD_NAME ASC"
        val wordList = ArrayList<Words>()

        val db = this.readableDatabase

        // query the user table

        val cursor = db.query(TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            sortOrder)
        if (cursor.moveToFirst()){
            do {
                val wordAndMeaning = Words(id = cursor.getString(cursor.getColumnIndex(COLUMN_WORD_ID)).toInt(),
                    wordT = cursor.getString(cursor.getColumnIndex(COLUMN_WORD_NAME)),
                    meaning = cursor.getString(cursor.getColumnIndex(COLUMN_WORD_MEANING)))

                wordList.add(wordAndMeaning)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return wordList
    }

    fun deleteWord(word: Words){
        val db = this.writableDatabase
        //delete word record by id
        db.delete(TABLE_NAME, "${COLUMN_WORD_ID} = ?",
            arrayOf(word.id.toString()))
    }

    fun updateWord(word: Words){

        val db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_WORD_NAME, word.wordT)
        values.put(COLUMN_WORD_MEANING, word.meaning)

        db.update(TABLE_NAME, values, "$COLUMN_WORD_ID = ?",
            arrayOf(word.id.toString()))

        db.close()
    }


    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "WordsDB.db"
        private val TABLE_NAME = "wordstable"

        private val COLUMN_WORD_ID = "word_id"
        private val COLUMN_WORD_NAME = "word_name"
        private val COLUMN_WORD_MEANING = "word_meaning"
    }
}