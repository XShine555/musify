package com.musify.ui.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.musify.databinding.FragmentPreferencesBinding
import com.musify.ui.common.VoicePreferencesRepository
import kotlinx.coroutines.launch

class PreferencesFragment : Fragment() {
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
    private lateinit var voicePreferencesRepository: VoicePreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        voicePreferencesRepository = VoicePreferencesRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            voicePreferencesRepository.isVoiceEnabled.collect { isEnabled ->
                binding.voiceSwitch.isChecked = isEnabled
            }
        }

        binding.voiceSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                voicePreferencesRepository.setVoiceEnabled(isChecked)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

