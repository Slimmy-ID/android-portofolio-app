package com.portfolio.app.ui.kontak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.portfolio.app.data.DatabaseHelper
import com.portfolio.app.databinding.FragmentKontakBinding
import com.portfolio.app.util.PdfGenerator

class KontakFragment : Fragment() {

    private var _binding: FragmentKontakBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var pdfGenerator: PdfGenerator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKontakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        dbHelper = DatabaseHelper(requireContext())
        pdfGenerator = PdfGenerator(requireContext())
        
        loadProfile()
        setupDownloadButton()
    }

    private fun loadProfile() {
        val profile = dbHelper.getProfile()
        profile?.let {
            binding.tvEmail.text = it.email
            binding.tvPhone.text = it.phone
            binding.tvAddress.text = it.address
            binding.tvLinkedin.text = it.linkedin
            binding.tvGithub.text = it.github
        }
    }

    private fun setupDownloadButton() {
        binding.btnDownloadCv.setOnClickListener {
            val file = pdfGenerator.generateCV()
            if (file != null) {
                Toast.makeText(requireContext(), "CV berhasil dibuat!", Toast.LENGTH_SHORT).show()
                pdfGenerator.shareCV(file)
            } else {
                Toast.makeText(requireContext(), "Gagal membuat CV", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
