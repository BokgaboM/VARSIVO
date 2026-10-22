package com.varsivo.app.explore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.varsivo.app.R
import com.varsivo.app.data.ApsStore
import com.varsivo.app.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val adapter = ExploreAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvExplore.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExplore.adapter = adapter

        binding.cardAps.setOnClickListener {
            startActivity(Intent(requireContext(), ApsCalculatorActivity::class.java))
        }
        binding.etSearch.doAfterTextChanged { refresh() }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val aps = ApsStore(requireContext()).apsOrNull()

        binding.cardAps.text = if (aps == null) {
            getString(R.string.aps_card_default)
        } else {
            getString(R.string.aps_card_result, aps)
        }

        val query = binding.etSearch.text?.toString()?.trim().orEmpty()
        val items = buildItems(aps).filter {
            query.isEmpty() ||
                    it.title.contains(query, ignoreCase = true) ||
                    it.subtitle.contains(query, ignoreCase = true)
        }

        adapter.submit(items)
        binding.tvEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun buildItems(aps: Int?): List<ExploreItem> {
        val courseItems = ExploreData.courses.map { course ->
            val status: String
            val state: ItemState
            when {
                aps == null -> {
                    status = getString(R.string.status_unknown)
                    state = ItemState.INFO
                }
                aps >= course.minAps -> {
                    status = getString(R.string.status_qualify)
                    state = ItemState.QUALIFIES
                }
                else -> {
                    status = getString(R.string.status_need_more, course.minAps - aps)
                    state = ItemState.NOT_YET
                }
            }
            ExploreItem(course.institution, "${course.course} - $status", state)
        }

        val careerItems = ExploreData.careers.map { career ->
            ExploreItem(
                getString(R.string.career_title, career.name),
                getString(R.string.career_line, getString(career.demandRes), career.salary),
                ItemState.INFO
            )
        }

        return courseItems + careerItems
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}