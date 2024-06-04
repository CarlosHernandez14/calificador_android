package com.example.calificadormovil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Curso(
    val curso_id: Int,
    val nombre: String,
    val descripcion: String,
    val instructor_id: Int?,
    val image_path: String?,
    val created_at: String,
    val updated_at: String
)

class CursoAdapter(private var cursos: List<Curso>) : RecyclerView.Adapter<CursoAdapter.CursoViewHolder>() {

    private val baseUrl = "http://10.0.2.2:8000/" // Cambia esta URL si es necesario

    fun updateCursos(nuevosCursos: List<Curso>) {
        cursos = nuevosCursos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_curso, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursos[position]
        holder.tvCursoNombre.text = curso.nombre
        holder.tvCursoDescripcion.text = curso.descripcion
        val imageUrl = baseUrl + (curso.image_path ?: "")
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background) // Añadir un placeholder mientras se carga la imagen
            .into(holder.ivCursoImage)
    }

    override fun getItemCount(): Int = cursos.size

    class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCursoImage: ImageView = itemView.findViewById(R.id.ivCursoImage)
        val tvCursoNombre: TextView = itemView.findViewById(R.id.tvCursoNombre)
        val tvCursoDescripcion: TextView = itemView.findViewById(R.id.tvCursoDescripcion)
    }
}
