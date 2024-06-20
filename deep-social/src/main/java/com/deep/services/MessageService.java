package com.deep.services;

import java.util.List;

import com.deep.exception.ChatException;
import com.deep.exception.MessageException;
import com.deep.models.Chat;
import com.deep.models.Message;
import com.deep.models.User;

public interface MessageService {

	public Message createMessage(User user,Integer chatId,Message req) throws MessageException, ChatException;
	
	public  List<Message> findChatsMessages(Integer chatId) throws MessageException, ChatException;
}
