package com.portfolio.app.util

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.portfolio.app.data.DatabaseHelper
import java.io.File
import java.io.FileOutputStream

class PdfGenerator(private val context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun generateCV(): File? {
        return try {
            val fileName = "CV_Portfolio.pdf"
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
            
            val pdfWriter = PdfWriter(FileOutputStream(file))
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            val profile = dbHelper.getProfile()
            
            profile?.let {
                document.add(
                    Paragraph(it.nama)
                        .setFontSize(24f)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                )
                
                document.add(
                    Paragraph(it.title)
                        .setFontSize(14f)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontColor(ColorConstants.GRAY)
                )
                
                document.add(Paragraph("\n"))
                
                document.add(
                    Paragraph("Email: ${it.email} | Phone: ${it.phone}")
                        .setFontSize(10f)
                        .setTextAlignment(TextAlignment.CENTER)
                )
                
                document.add(
                    Paragraph("Address: ${it.address}")
                        .setFontSize(10f)
                        .setTextAlignment(TextAlignment.CENTER)
                )
                
                document.add(Paragraph("\n"))
                
                document.add(
                    Paragraph("TENTANG SAYA")
                        .setFontSize(14f)
                        .setBold()
                )
                document.add(Paragraph(it.bio).setFontSize(11f))
                
                document.add(Paragraph("\n"))
            }

            document.add(
                Paragraph("PENDIDIKAN")
                    .setFontSize(14f)
                    .setBold()
            )
            
            dbHelper.getPendidikan().forEach { edu ->
                document.add(
                    Paragraph(edu.institusi)
                        .setFontSize(12f)
                        .setBold()
                )
                document.add(
                    Paragraph("${edu.jurusan} (${edu.tahunMulai} - ${edu.tahunSelesai})")
                        .setFontSize(11f)
                )
                document.add(
                    Paragraph(edu.deskripsi)
                        .setFontSize(10f)
                        .setFontColor(ColorConstants.GRAY)
                )
                document.add(Paragraph("\n"))
            }

            document.add(
                Paragraph("PENGALAMAN KERJA")
                    .setFontSize(14f)
                    .setBold()
            )
            
            dbHelper.getPengalaman().forEach { exp ->
                document.add(
                    Paragraph(exp.perusahaan)
                        .setFontSize(12f)
                        .setBold()
                )
                document.add(
                    Paragraph("${exp.posisi} (${exp.tahunMulai} - ${exp.tahunSelesai})")
                        .setFontSize(11f)
                )
                document.add(
                    Paragraph(exp.deskripsi)
                        .setFontSize(10f)
                        .setFontColor(ColorConstants.GRAY)
                )
                document.add(Paragraph("\n"))
            }

            document.add(
                Paragraph("SKILLS")
                    .setFontSize(14f)
                    .setBold()
            )
            
            val skillsByCategory = dbHelper.getSkills().groupBy { it.kategori }
            skillsByCategory.forEach { (kategori, skills) ->
                val skillNames = skills.joinToString(", ") { "${it.nama} (${it.level}%)" }
                document.add(
                    Paragraph("$kategori: $skillNames")
                        .setFontSize(11f)
                )
            }

            document.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun shareCV(file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        context.startActivity(Intent.createChooser(shareIntent, "Share CV"))
    }
}
