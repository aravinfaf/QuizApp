package com.aravind.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aravind.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding?.startBtn?.setOnClickListener {
            if (binding?.nameEt?.text!!.isNotEmpty()) {
                startActivity(Intent(this, QuestionsActivity::class.java))
            }else{
                Toast.makeText(this,"Enter name",Toast.LENGTH_LONG).show()
            }
        }
    }
}