package com.example.sendnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() ,INotesRVAdapter  {

    lateinit var viewModel : NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter


        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                    adapter.updateList(it as ArrayList<Note>)
            }
        })
    }
    override fun onItemClicked (note: Note){
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted",Toast.LENGTH_LONG).show()
    }

    fun submitData(view: View) {
        val noteText = findViewById<EditText>(R.id.editText).text.toString()
        if(noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this, "$noteText Added",Toast.LENGTH_LONG).show()
        }
    }
}