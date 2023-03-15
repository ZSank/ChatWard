package com.example.letschat.model

import java.sql.Timestamp

class MessageModel(
	var message: String? = "",
	var senderId: String = "",
	var timestamp: Long,

) {
}