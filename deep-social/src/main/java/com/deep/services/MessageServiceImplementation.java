package com.deep.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.exception.ChatException;
import com.deep.exception.MessageException;
import com.deep.models.Chat;
import com.deep.models.Message;
import com.deep.models.User;
import com.deep.repository.ChatRepository;
import com.deep.repository.MessageRepository;

@Service
public class MessageServiceImplementation implements MessageService{

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Override
	public Message createMessage(User user, Integer chatId, Message req) throws MessageException, ChatException {
		
		Chat chat = chatService.findChatById(chatId);
		
		Message message = new Message();
		
		message.setChat(chat);
		message.setContent(req.getContent());
		message.setImage(req.getImage());
		message.setUser(user);
		message.setTimestamp(LocalDateTime.now());
		
		Message  savedMessage = messageRepository.save(message);
		
		chat.getMessages().add(savedMessage);
		
		chatRepository.save(chat);
		
		return savedMessage;
	}

	@Override
	public List<Message> findChatsMessages(Integer chatId) throws MessageException, ChatException {
		
		Chat chat = chatService.findChatById(chatId);

		return messageRepository.findByChatId(chatId);
	}

}
