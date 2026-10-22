package com.varsivo.app.bursaries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.varsivo.app.R
import com.varsivo.app.databinding.FragmentBursariesBinding
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class BursariesFragment : Fragment() {

    private var _binding: FragmentBursariesBinding? = null
    private val binding get() = _binding!!

    private val adapter = BursaryAdapter()
    private var selectedField = StudyField.ALL
    private var selectedProvince = Province.ALL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBursariesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBursaries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBursaries.adapter = adapter

        setupDropdown(binding.actvField, R.array.study_fields) { position ->
            selectedField = StudyField.values()[position]
        }
        setupDropdown(binding.actvProvince, R.array.provinces) { position ->
            selectedProvince = Province.values()[position]
        }

        refresh()
    }

    private fun setupDropdown(
        view: AutoCompleteTextView,
        arrayRes: Int,
        onSelected: (Int) -> Unit
    ) {
        val items = resources.getStringArray(arrayRes)
        view.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items))
        view.setText(items[0], false)
        view.setOnItemClickListener { _, _, position, _ ->
            onSelected(position)
            refresh()
        }
    }

    private fun refresh() {
        val today = LocalDate.now()

        val rows = BursaryData.all(today)
            .filter { selectedField == StudyField.ALL || it.field == StudyField.ALL || it.field == selectedField }
            .filter { selectedProvince == Province.ALL || it.province == Province.ALL || it.province == selectedProvince }
            .map { it to ChronoUnit.DAYS.between(today, it.closingDate).toInt() }
            .filter { (_, days) -> days >= 0 }
            .sortedBy { (_, days) -> days }
            .map { (bursary, days) ->
                BursaryRow(
                    name = bursary.name,
                    description = bursary.description,
                    closes = closesText(days),
                    urgent = days <= URGENT_DAYS
                )
            }

        adapter.submit(rows)
        binding.tvEmpty.visibility = if (rows.isEmpty()) View.VISIBLE else View.GONE
        binding.tvCount.text = getString(R.string.bursary_count, rows.size)
    }

    private fun closesText(days: Int): String =
        if (days == 0) {
            getString(R.string.bursary_closes_today)
        } else {
            resources.getQuantityString(R.plurals.bursary_closes_in, days, days)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        const val URGENT_DAYS = 7
    }
}