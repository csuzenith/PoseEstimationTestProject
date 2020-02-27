package com.example.poseestimationapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*

class PETestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petest)
        //setSupportActionBar(toolbar)

        initUI()
    }

    private fun initUI() {
        val roundsEditText = findViewById<EditText>(R.id.rounds_edit_text)
        val framesEditText = findViewById<EditText>(R.id.frame_count_edit_text)
        val intervalEditText = findViewById<EditText>(R.id.frame_interval_edit_text)
        val picNumEditText = findViewById<EditText>(R.id.num_pic_edit_text)
        val modeSpinner = findViewById<Spinner>(R.id.sche_mode_spinner)
        val numThreadsSpinner = findViewById<Spinner>(R.id.num_threads_spinner)
        val gpuFPRadioGroup = findViewById<RadioGroup>(R.id.gpu_fp_radio_group)
        val testButton = findViewById<Button>(R.id.test_button)

        // Get array resource
        val scheModes = resources.getStringArray(R.array.sche_modes)
        modeSpinner.adapter = ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, scheModes)

        val numThreadsArray = resources.getStringArray(R.array.num_threads)
        numThreadsSpinner.adapter = ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, numThreadsArray)

        // Set OnClickListener for buttons
        testButton.setOnClickListener {
            val rounds = roundsEditText.text.toString().toInt()
            val frames = framesEditText.text.toString().toInt()
            val frameInterval = intervalEditText.text.toString().toInt()
            val picNum = picNumEditText.text.toString().toInt()
            val mode = modeSpinner.selectedItemPosition
            val numThreads = getNumThreadsFromSpinner(numThreadsSpinner)
            val useGpuFp16 = getUseGpuFp16FromRadioGroup(gpuFPRadioGroup)

            //PETestTask(this).start()
            PETestTask(this).test(
                    rounds, frames, frameInterval, picNum, mode, numThreads, useGpuFp16)


            showTestStartToast()
        }

        val task1TestButton = findViewById<Button>(R.id.task1_test_button)
        val task2TestButton = findViewById<Button>(R.id.task2_test_button)

        task1TestButton.setOnClickListener {
            PECPUThreadTestTask(this).startTask1()
            showTestStartToast()
        }

        task2TestButton.setOnClickListener {
            PECPUThreadTestTask(this).startTask2()
            showTestStartToast()
        }
    }

    private fun getNumThreadsFromSpinner(spinner: Spinner): Int {
        if (spinner.selectedItemPosition == 0)
            return -1
        else
            return spinner.selectedItemPosition
    }

    private fun getUseGpuFp16FromRadioGroup(rg: RadioGroup): Boolean {
        var checkedId = 0
        for (i in 0 until rg.childCount) {
            val rb : RadioButton = rg.getChildAt(i) as RadioButton
            if (rb.isChecked) {
                checkedId = i
            }
        }

        var useGpuFp16 = false
        if (checkedId == 1)
            useGpuFp16 = true

        return useGpuFp16
    }

    private fun showTestStartToast() {
        Toast.makeText(this, "Test start!", Toast.LENGTH_SHORT).show()
    }
}