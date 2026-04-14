package com.musify.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.Manifest
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.musify.R
import com.musify.databinding.ActivityMainBinding
import com.musify.ui.common.UsageStatsRepository
import com.musify.ui.common.VoicePreferencesRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var usageStatsRepository: UsageStatsRepository
    private lateinit var voicePreferencesRepository: VoicePreferencesRepository
    private var sessionStartMs: Long = 0L
    private lateinit var recognizer: SpeechRecognizer
    private lateinit var recognizerIntent: Intent
    private var voiceEnabled = false
    private var pendingVoiceStart = false

    private val microphonePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted && pendingVoiceStart) {
                pendingVoiceStart = false
                recognizer.startListening(recognizerIntent)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usageStatsRepository = UsageStatsRepository(this)
        voicePreferencesRepository = VoicePreferencesRepository(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val originalPaddingStart = binding.root.paddingTop
        val originalPaddingTop = binding.root.paddingTop
        val originalPaddingEnd = binding.root.paddingEnd

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                originalPaddingStart + systemBars.left,
                originalPaddingTop + systemBars.top,
                originalPaddingEnd + systemBars.right,
                0
            )
            insets
        }

        val navView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        binding.voiceFab.visibility = View.GONE

        binding.voiceFab.setOnClickListener {
            startVoiceRecognition()
        }

        initializeSpeechRecognizer()

        lifecycleScope.launch {
            voicePreferencesRepository.isVoiceEnabled.collect { enabled ->
                voiceEnabled = enabled
                binding.voiceFab.visibility = if (enabled) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initializeSpeechRecognizer() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Log.i("MainActivity", "Speech recognition no disponible.")
            return
        }

        recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }

        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val spokenText = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.get(0)
                    ?.lowercase()

                handleVoiceCommand(spokenText)
            }

            override fun onError(error: Int) {
                pendingVoiceStart = false
            }
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun handleVoiceCommand(command: String?) {
        if (!voiceEnabled || command == null) return

        when {
            command.contains("librería") || command.contains("biblioteca") -> {
                binding.navView.selectedItemId = R.id.navigation_library
            }
            command.contains("buscar") || command.contains("busqueda") -> {
                binding.navView.selectedItemId = R.id.navigation_search
            }
            command.contains("usuario") || command.contains("perfil") -> {
                binding.navView.selectedItemId = R.id.navigation_user
            }
        }
    }

    fun startVoiceRecognition() {
        if (!voiceEnabled) return

        val permissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            recognizer.startListening(recognizerIntent)
        } else {
            pendingVoiceStart = true
            microphonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    override fun onResume() {
        super.onResume()
        sessionStartMs = System.currentTimeMillis()
    }

    override fun onPause() {
        super.onPause()
        val sessionMinutes = ((System.currentTimeMillis() - sessionStartMs) / 60000).toInt()
        if (sessionMinutes > 0) {
            lifecycleScope.launch { usageStatsRepository.addUsageMinutes(sessionMinutes) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recognizer.destroy()
    }
}