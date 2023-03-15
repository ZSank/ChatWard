package com.example.letschat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.R
import com.example.letschat.databinding.ReceiverItemLayoutBinding
import com.example.letschat.databinding.SentItemLayoutBinding
import com.example.letschat.model.MessageModel
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val list: ArrayList<MessageModel>) :
	RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	val ITEM_SENT = 1
	val ITEM_RECEIVE = 2

	class SentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val binding = SentItemLayoutBinding.bind(view)
	}

	class ReceiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val binding = ReceiverItemLayoutBinding.bind(view)
	}

	override fun getItemViewType(position: Int): Int {
		return if (FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT else ITEM_RECEIVE
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return if (viewType == ITEM_SENT) {
			SentViewHolder(
				LayoutInflater.from(parent.context)
					.inflate(R.layout.sent_item_layout, parent, false)
			)
		} else ReceiveViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(R.layout.receiver_item_layout, parent, false)
		)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val message = list[position]

		if(holder.itemViewType == ITEM_SENT) {
			val viewHolder = holder as SentViewHolder
			viewHolder.binding.userMsg.text = message.message
		} else {
			val viewHolder = holder as ReceiveViewHolder
			viewHolder.binding.userMsg.text = message.message
		}
	}
}