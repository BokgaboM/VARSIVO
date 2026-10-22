package com.varsivo.app.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.varsivo.app.MainActivity
import com.varsivo.app.R
import com.varsivo.app.data.SessionManager
import com.varsivo.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = SessionManager(requireContext()).userName()
        binding.tvWelcome.text = if (name.isNullOrBlank()) {
            getString(R.string.welcome_back)
        } else {
            getString(R.string.welcome_back_name, name)
        }

        val main = requireActivity() as MainActivity
        binding.cardExplorer.setOnClickListener { main.openTab(R.id.nav_explore) }
        binding.cardBursaries.setOnClickListener { main.openTab(R.id.nav_bursaries) }
        binding.cardTracker.setOnClickListener { main.openTab(R.id.nav_tracker) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}