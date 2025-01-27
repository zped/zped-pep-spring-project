package com.example.service;

import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // Two versions of method to check message length constraints 0 < message length < 256
    public boolean isValidMessage(Message message) {
        return !message.getMessageText().isEmpty() && message.getMessageText().length() < 256;
    }

    public boolean isValidMessage(String messageText) {
        if (messageText.equals("") || messageText.length() > 255) return false;
        return true;
    }

    // Does there exist account in Account table with account_id == posted_by
    private boolean messagePosterExistsInAccounts(Message message) {
        if (accountRepository.existsByAccountId(message.getPostedBy())) return true;
        return false;
    }

    // Does message exist in Message table with message_id
    private boolean messageIdExistsInMessages(int messageId) {
        if(messageRepository.existsByMessageId(messageId)) return true;
        return false;
    }

    // Check if postedBy field of new message exists as accountId in account table,
    // and message length is within bounds. If so, create new message. If not, inform
    // user bad input supplied
    public Message createMessage(Message message) throws ClientErrorException {
        if (isValidMessage(message) && messagePosterExistsInAccounts(message)) {
            return messageRepository.save(message);
        }
        throw new ClientErrorException();
    }

    // Retrieve message from message table by message_id
    public Message getMessageByMessageId(int messageId){
        return messageRepository.findByMessageId(messageId);
    }

    // Retrieve all messages in message table
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // retrieve all messages in message table by posted_by
    public List<Message> getAllMessagesOfAUser(int accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }

    // delete message in db
    public int deleteMessageByMessageId(int messageId) {
        if (messageIdExistsInMessages(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    // update message in db
    public int updateMessageText(int messageId, String messageText) throws ClientErrorException {
        if(messageIdExistsInMessages(messageId) && isValidMessage(messageText)) {
            messageRepository.save(getMessageByMessageId(messageId));
            return 1;
        }
        throw new ClientErrorException();
    }
}
