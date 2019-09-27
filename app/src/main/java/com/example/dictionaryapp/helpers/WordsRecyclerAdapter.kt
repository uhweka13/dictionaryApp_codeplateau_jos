package com.example.dictionaryapp.helpers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Words
import com.example.dictionaryapp.viewActivity

class WordsRecyclerAdapter(private var listWords:List<Words>, internal var context: Context) : RecyclerView.Adapter<WordsRecyclerAdapter.WordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        //inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.words_list_card, parent, false)

        return WordViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return listWords.size

    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.textWords.text = listWords[position].wordT

        holder.itemView.setOnClickListener(View.OnClickListener {

            val i = Intent(context, viewActivity::class.java)

            //pass the details of the user to the next activity

            i.putExtra("id", listWords[position].id)
            i.putExtra("word",listWords[position].wordT)
            i.putExtra("meaning",listWords[position].meaning)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        })


    }

    inner class WordViewHolder(view: View):RecyclerView.ViewHolder(view){
        var textWords:TextView

        init{
          textWords = view.findViewById<View>(R.id.word_word) as TextView
        }
    }

    fun filterList(filteredWords: ArrayList<Words>) {
        this.listWords = (filteredWords)
        notifyDataSetChanged()
    }

}