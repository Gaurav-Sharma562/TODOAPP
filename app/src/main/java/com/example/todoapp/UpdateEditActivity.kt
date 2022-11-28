package com.example.todoapp

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.todoapp.databinding.ActivityUpdateEditBinding

class UpdateEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_edit)
        val intent = intent.getShortExtra(Keys.personalData)
        val personalData = Gson().fromGson(intentData, personalData::class.java)
        binding.etupdatetitle.setText(personalData.Title)
        binding.etupdatedescription.setText(personalData.Update Description)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            101 -> {
                val updateBinding: ActivityUpdateEditBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(this), R.layout.activity_update_edit, null, false
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
        }

        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId==R.id.item_delete_all_records){
//            viewModel.deleteAll()
//        }
//        return super.onOptionsItemSelected(item)
//    }
}