package com.iqbal.databaseroom

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_idea.view.*
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity(), AppIdeaRecyclerViewAdapter.TouchEvent {

    private lateinit var appIdeaViewModel: AppIdeaViewModel

    private lateinit var appIdeaRecyclerViewAdapter: AppIdeaRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add.setOnClickListener {
            creator()
        }

        // Get the view model
        appIdeaViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(AppIdeaViewModel::class.java)

        // Recycler view
        appIdeaRecyclerViewAdapter = AppIdeaRecyclerViewAdapter(this, this)
        rec_ideas.adapter = appIdeaRecyclerViewAdapter
        rec_ideas.layoutManager = LinearLayoutManager(this)

        // Combine them
        appIdeaViewModel.allIdeas.observe(this, Observer { ideas ->
            ideas.let {
                appIdeaRecyclerViewAdapter.setItems(it)
            }
        })
    }

    override fun onClick(item: AppIdea) {

    }

    override fun onHold(item: AppIdea) {
        val items = arrayOf("Edit", "Delete")

        AlertDialog.Builder(this)
                .setTitle("What would you like to do?")
                .setItems(items, object: DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, idx: Int) {
                        when (idx) {
                            0 -> {
                                editor(item)
                            }
                            1 -> {
                                appIdeaViewModel.delete(item)
                            }
                        }
                    }

                }).show()
    }

    fun creator() {
        val layout = layoutInflater.inflate(R.layout.dialog_idea, null)

        val customFragmentDialog = AlertDialog.Builder(this)
                .setTitle("Create new task")
                .setView(layout)
                .setPositiveButton("Go", object: DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val created = AppIdea(0, layout.edit_title.text.toString(),
                                layout.edit_lang.text.toString())

                        appIdeaViewModel.insert(created)
                    }

                })
                .create()

        customFragmentDialog.show()
    }

    fun editor(arg: AppIdea) {
        val layout = layoutInflater.inflate(R.layout.dialog_idea, null)

        val customFragmentDialog = AlertDialog.Builder(this)
                .setTitle("Edit this task")
                .setView(layout)
                .setPositiveButton("Go", object: DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        arg.title = layout.edit_title.text.toString()
                        arg.language = layout.edit_lang.text.toString()

                        appIdeaViewModel.update(arg)
                    }

                })
                .create()

        customFragmentDialog.show()
    }
}
