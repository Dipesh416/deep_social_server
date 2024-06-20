package com.deep.services;

import java.util.List;

import com.deep.exception.ChatException;
import com.deep.models.Chat;
import com.deep.models.User;

public interface ChatService {
	
	
	public Chat createChat(User requser,User user2);
	
	public Chat findChatById(Integer chatId) throws ChatException;
	
	public List<Chat> findUsersChat(Integer userId);
}
