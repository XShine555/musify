package com.musify.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.musify.databinding.FragmentStatisticsBinding
import com.musify.ui.common.FirebaseStatsRepository
import com.musify.ui.common.UsageStatsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var usageStatsRepository: UsageStatsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        usageStatsRepository = UsageStatsRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        lifecycleScope.launch {
            usageStatsRepository.incrementUserScreenViews()

            val stats = usageStatsRepository.getUsageStats().first()
            val usageText = "Inicios: ${stats.appLaunches} • Vistas usuario: ${stats.userScreenViews}"
            binding.statsSummary.text = usageText
            binding.co2Estimated.text = String.format("%.2f kg CO₂ eq", stats.estimatedCo2Kg)
            binding.usageHours.text = String.format("%.2f h de uso", stats.usageHours)
            updatePieChart(stats)
            updateLineChart(stats)

            // Save and read Firestore
            val firebase = FirebaseStatsRepository(requireContext())
            firebase.saveStats(stats)
            val remote = firebase.loadLastStats()
            if (remote != null) {
                binding.remoteStats.text = "Firebase: inicios=${remote.appLaunches}, vistas=${remote.userScreenViews}, añadidos=${remote.trackAdds}, eliminados=${remote.trackRemoves}, minutos=${remote.totalUsageMinutes}"
            } else {
                binding.remoteStats.text = "Firebase: no hubo datos leídos (revisa configuración de Firebase/Firestore y reglas)"
            }
        }
    }

    private fun updatePieChart(stats: com.musify.ui.common.UsageStats) {
        val entries = listOf(
            PieEntry(stats.appLaunches.toFloat(), "Inicios"),
            PieEntry(stats.userScreenViews.toFloat(), "Vistas usuario"),
            PieEntry(stats.trackAdds.toFloat(), "Añadidos"),
            PieEntry(stats.trackRemoves.toFloat(), "Eliminados")
        )
        val set = PieDataSet(entries, "Estadísticas").apply {
            colors = listOf(
                ContextCompat.getColor(requireContext(), android.R.color.holo_blue_light),
                ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
            )
            valueTextSize = 12f
            valueFormatter = PercentFormatter(binding.pieChart)
        }
        binding.pieChart.data = PieData(set)
        binding.pieChart.invalidate()
    }

    private fun updateLineChart(stats: com.musify.ui.common.UsageStats) {
        val entries = listOf(
            Entry(0f, stats.appLaunches.toFloat()),
            Entry(1f, stats.userScreenViews.toFloat()),
            Entry(2f, stats.trackAdds.toFloat()),
            Entry(3f, stats.trackRemoves.toFloat())
        )
        val set = LineDataSet(entries, "Evolución").apply {
            color = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
            valueTextSize = 11f
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark))
        }
        val data = LineData(set)
        binding.lineChart.data = data
        binding.lineChart.xAxis.apply {
            granularity = 1f
            valueFormatter = IndexAxisValueFormatter(listOf("Inicios", "Vistas", "Añadidos", "Eliminados"))
            position = XAxis.XAxisPosition.BOTTOM
        }
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
