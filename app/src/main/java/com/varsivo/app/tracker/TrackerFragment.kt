package com.varsivo.app.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.varsivo.app.R
import com.varsivo.app.data.ApplicationStore
import com.varsivo.app.databinding.DialogApplicationBinding
import com.varsivo.app.databinding.FragmentTrackerBinding
import java.util.UUID

class TrackerFragment : Fragment() {

    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!

    private lateinit var store: ApplicationStore
    private val adapter = TrackerAdapter { entry -> showDialog(entry) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        store = ApplicationStore(requireContext())
        binding.rvApplications.layoutManager = LinearLayoutManager(requireContext())
        binding.rvApplications.adapter = adapter
        binding.cardAdd.setOnClickListener { showDialog(null) }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val items = store.all()
        adapter.submit(items)
        binding.tvEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun showDialog(existing: ApplicationEntry?) {
        val dialogBinding = DialogApplicationBinding.inflate(layoutInflater)
        val statuses = resources.getStringArray(R.array.application_statuses)

        var selected = existing?.status ?: ApplicationStatus.IN_PROGRESS
        dialogBinding.actvStatus.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, statuses)
        )
        dialogBinding.actvStatus.setText(statuses[selected.ordinal], false)
        dialogBinding.actvStatus.setOnItemClickListener { _, _, position, _ ->
            selected = ApplicationStatus.values()[position]
        }
        existing?.let { dialogBinding.etName.setText(it.name) }

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (existing == null) R.string.tracker_add_title else R.string.tracker_edit_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.btn_save, null)
            .setNegativeButton(R.string.btn_cancel, null)

        if (existing != null) {
            builder.setNeutralButton(R.string.btn_delete) { _, _ ->
                store.delete(existing.id)
                refresh()
            }
        }

        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val name = dialogBinding.etName.text?.toString()?.trim().orEmpty()
                if (name.isEmpty()) {
                    dialogBinding.tilName.error = getString(R.string.error_name_required)
                    return@setOnClickListener
                }
                store.save(ApplicationEntry(existing?.id ?: UUID.randomUUID().toString(), name, selected))
                refresh()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}