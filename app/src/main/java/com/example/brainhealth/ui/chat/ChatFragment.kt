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
                    "https://media.licdn.com/dms/image/D5603AQFIi6DFvRlnig/profile-displayphoto-shrink_400_400/0/1681419555010?e=2147483647&v=beta&t=UvCNfGQXuL8sf0GGCLsilZ93GUnEuaHHFxsuWXWxmqU",
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