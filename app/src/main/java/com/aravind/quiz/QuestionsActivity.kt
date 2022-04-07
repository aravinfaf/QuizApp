package com.aravind.quiz

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.aravind.quiz.databinding.ActivityQuestionsBinding

class QuestionsActivity : AppCompatActivity(),View.OnClickListener {

    private var binding : ActivityQuestionsBinding? = null
    private var mCurrentPosition = 1
    private var mSelectedPosition : Int = 0
    private var mQuestionList : ArrayList<Question>? = null
    private var mCorrectAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_questions)

       mQuestionList  = Constants.getQuestions()

        setQuestions()

        binding?.optionOne?.setOnClickListener(this)
        binding?.optionTwo?.setOnClickListener(this)
        binding?.optionThree?.setOnClickListener(this)
        binding?.optionFour?.setOnClickListener(this)
        binding?.btnSubmit?.setOnClickListener(this)
    }

    private fun setQuestions(){
        val questions : Question = mQuestionList!![mCurrentPosition-1]
        binding?.questionsTextview?.text = questions.Question
        binding?.imageView?.setImageResource(questions.image)
        binding?.optionOne?.text = questions.optionOne
        binding?.optionTwo?.text = questions.optionTwo
        binding?.optionThree?.text = questions.optionThree
        binding?.optionFour?.text = questions.optionFour
        binding?.progressBar?.progress = mCurrentPosition
        binding?.progress?.text =
            "$mCurrentPosition" + "/" + binding?.progressBar?.max // Setting up the progress text

        if (mCurrentPosition == mQuestionList!!.size){
                binding?.btnSubmit?.text = "FINISH"
            }else{
                binding?.btnSubmit?.text = "SUBMIT"
            }
    }

    private fun defaultOptionView(){
        val options = ArrayList<TextView>()

        binding?.optionOne?.let {
            options.add(0,it)
        }
        binding?.optionTwo?.let {
            options.add(1,it)
        }
        binding?.optionThree?.let {
            options.add(2,it)
        }
        binding?.optionFour?.let {
            options.add(3,it)
        }

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedPosition(tv : TextView,mPosition : Int){
        defaultOptionView()

        mSelectedPosition = mPosition

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    private fun checkAnswer(answer : Int, drawableView : Int){
        when(answer) {
            1 -> binding?.optionOne?.background = ContextCompat.getDrawable(
                this,
                drawableView
            )
            2 -> binding?.optionTwo?.background = ContextCompat.getDrawable(
                this,
                drawableView
            )
            3 -> binding?.optionThree?.background = ContextCompat.getDrawable(
                this,
                drawableView
            )
            4 -> binding?.optionFour?.background = ContextCompat.getDrawable(
                this,
                drawableView
            )
        }
    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> {
                binding?.optionOne?.let {
                    selectedPosition(it,1)

                }
            }
            R.id.optionTwo -> {
                binding?.optionTwo?.let {
                    selectedPosition(it,2)

                }
            }
            R.id.optionThree -> {
                binding?.optionThree?.let {
                    selectedPosition(it,3)

                }
            }
            R.id.optionFour -> {
                binding?.optionFour?.let {
                    selectedPosition(it,4)

                }
            }
            R.id.btnSubmit -> {
                defaultOptionView()
                    if (mSelectedPosition == 0){
                        mCurrentPosition++
                
                        when{
                            mCurrentPosition <= mQuestionList!!.size ->{
                                setQuestions()
                            } else -> {
                                Toast.makeText(this,"$mCorrectAnswers out of 10",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        val questionList = mQuestionList!!.get(mCurrentPosition-1)
                        if (questionList!!.correctOption != mSelectedPosition){
                            checkAnswer(mSelectedPosition, R.drawable.wrong_option_border_bg)
                        }else{
                            mCorrectAnswers++
                        }
                        checkAnswer(questionList!!.correctOption, R.drawable.correct_option_border_bg)

                        if (mCurrentPosition == 0){
                            binding?.btnSubmit?.text = "FINISH"
                        }else{
                            binding?.btnSubmit?.text = "NEXT QUESTION"
                        }
                    }
                mSelectedPosition = 0
            }
        }
    }
}