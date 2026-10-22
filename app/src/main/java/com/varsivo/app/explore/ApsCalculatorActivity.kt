package com.varsivo.app.explore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.varsivo.app.R
import com.varsivo.app.data.ApsStore
import com.varsivo.app.databinding.ActivityApsCalculatorBinding
import com.varsivo.app.databinding.ItemSubjectRowBinding

class ApsCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApsCalculatorBinding
    private val rows = mutableListOf<ItemSubjectRowBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApsCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repeat(ApsCalculator.REQUIRED_SUBJECTS) {
            rows.add(ItemSubjectRowBinding.inflate(layoutInflater, binding.subjectsContainer, true))
        }

        binding.btnCalculate.setOnClickListener { calculate() }
        binding.btnDone.setOnClickListener { finish() }
    }

    private fun calculate() {
        val marks = mutableListOf<Int>()
        var allValid = true

        for (row in rows) {
            row.tilMark.error = null
            val text = row.etMark.text?.toString()?.trim().orEmpty()
            if (text.isNotEmpty()) {
                val mark = text.toIntOrNull()
                if (mark == null || mark !in 0..100) {
                    row.tilMark.error = getString(R.string.error_marks_range)
                    allValid = false
                } else {
                    marks.add(mark)
                }
            }
        }

        if (!allValid) return

        if (marks.size < ApsCalculator.REQUIRED_SUBJECTS) {
            binding.tvResult.text = getString(R.string.error_min_subjects)
            return
        }

        val aps = ApsCalculator.total(marks)
        ApsStore(this).save(aps)
        binding.tvResult.text = getString(R.string.aps_result, aps)
    }
}