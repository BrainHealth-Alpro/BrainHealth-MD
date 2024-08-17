package com.example.brainhealth.ui.chat

import android.os.Bundle
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainhealth.adapter.ChatAdapter
import com.example.brainhealth.databinding.FragmentChatBinding
import com.example.brainhealth.di.db.ChatData

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvChat.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvChat.addItemDecoration(itemDecoration)

        setChatData(
            listOf(
                ChatData(
                    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fid.linkedin.com%2Fin%2Frosyhaqqy%2Fin&psig=AOvVaw2iNoq47so8oR-enG8nkb3J&ust=1720589428794000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJCZmp-dmYcDFQAAAAAdAAAAABAE",
                    "Dr. Mawar Haqqy",
                    "Halo! Apa ada yang bisa saya bantu?",
                    "Today"
                )
            )
        )


        return root
    }

    private fun setChatData(userData: List<ChatData>) {
        val adapter = ChatAdapter()
        adapter.submitList(userData)
        binding.rvChat.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}