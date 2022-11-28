package com.example.todoapp

import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.ActivityUpdateEditBinding
import com.example.todoapp.databinding.TableLayoutTodosqliteBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DBViewModel
    private lateinit var factory: DBFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        factory = DBFactory(DBRepository(this))
        viewModel = ViewModelProvider(this, factory)[DBViewModel::class.java]
        val dataList = viewModel.getDataList()
        Log.d("DATALIST", "onCreate:" + dataList)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        updateUI()



        binding.floatingButton.setOnClickListener {
            var tableLayoutBinding: TableLayoutTodosqliteBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.table_layout_todosqlite, null, false
            )

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(tableLayoutBinding.root)
            dialog.setCancelable(false)
            dialog.show()
            val window = dialog.window
            window!!.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            tableLayoutBinding.create.setOnClickListener {
                viewModel.createData(
                    tableLayoutBinding.title.text.toString(),
                    tableLayoutBinding.desc.text.toString(),
                    viewModel.dateTime().toString()
                )
                dialog.dismiss()
                updateUI()
            }
            tableLayoutBinding.dismiss.setOnClickListener {
                dialog.dismiss()
            }

        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            101 -> {
                val updateBinding: ActivityUpdateEditBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(this),
                    R.layout.activity_update_edit,
                    null,
                    false
                )
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(updateBinding.root)
                dialog.setCancelable(false)
                dialog.show()
                val window = dialog.window
                window!!.setLayout(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                //  window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                updateBinding.update.setOnClickListener {
                    viewModel.updateData(
                        updateBinding.updateTitle.text.toString(),
                        updateBinding.updateDescription.text.toString(),
                        viewModel.dateTime()
                    )
                    binding.recyclerView.adapter
                    dialog.dismiss()
                }

            }
            102 -> {

            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_delete_all_records) {
            viewModel.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        var adapter = MyRecyclerViewAdapter(getDataList(), object : OnItemClickListener {
            override fun onItemClick(personal: PersonalData, position: Int) {
                val intent = Intent(this@MainActivity, UpdateEditActivity::class.java)
                intent.putExtra(Keys.PersonalData, Gson.tojson(personaldata))
                startActivity(intent)
            }
        })
        binding.recyclerView.adapter = adapter
    }

    private fun getDataList(): List<PersonalData> {
        return viewModel.getPersonalData()
    }
}

