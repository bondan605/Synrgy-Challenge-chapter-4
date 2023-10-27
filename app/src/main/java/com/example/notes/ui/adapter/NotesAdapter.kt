package com.example.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ItemNotesBinding
import com.example.notes.data.model.Notes
import com.example.notes.data.model.UserAndNotes

class NotesAdapter(private var onClick: NotesAdapter.NotesInterface) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<UserAndNotes>() {

        override fun areItemsTheSame(oldItem: UserAndNotes, newItem: UserAndNotes): Boolean {
            return oldItem.notes.id_notes == newItem.notes.id_notes
        }

        override fun areContentsTheSame(oldItem: UserAndNotes, newItem: UserAndNotes): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notes: UserAndNotes) {
            binding.apply {
                dataNotes = notes.notes

                btnRvEdit.setOnClickListener {
                    onClick.editNote(notes.notes)
                }

                btnRvDelete.setOnClickListener {
                    onClick.deleteNote(notes.notes)
                }
            }
        }
    }

    interface NotesInterface {
        fun editNote(notes: Notes)
        fun deleteNote(notes: Notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun setData(data: List<UserAndNotes>) {
        differ.submitList(data)
    }
}