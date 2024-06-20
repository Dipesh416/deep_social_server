package com.deep.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.exception.ChatException;
import com.deep.models.Chat;
import com.deep.models.User;
import com.deep.repository.ChatRepository;

@Service
public class ChatServiceImplementation implements ChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	@Override
	public Chat createChat(User requser, User user2) {
		
		Chat isExist = chatRepository.findChatByUserId(user2, requser);
		
		if(isExist != null) {
			return isExist;
		}
		Chat chat = new Chat();
		
		chat.getUsers().add(user2);
		chat.getUsers().add(requser);
		chat.setTimestamp(LocalDateTime.now());
		
		return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		
		Optional<Chat> opt = chatRepository.findById(chatId);
		
		if(opt.isEmpty()) {
			throw new ChatException("chat not found with id - "+ chatId);
		}
		return opt.get();
	}

	@Override
	public List<Chat> findUsersChat(Integer userId) {
		
		return chatRepository.findByUsersId(userId);
	}

}
