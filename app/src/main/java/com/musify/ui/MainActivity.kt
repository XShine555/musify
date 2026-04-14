package com.musify.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.musify.R
import com.musify.databinding.ActivityMainBinding
import com.musify.ui.common.UsageStatsRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var usageStatsRepository: UsageStatsRepository
    private var sessionStartMs: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usageStatsRepository = UsageStatsRepository(this)

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
}