package com.example.controller;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.ClientErrorException;
import com.example.exception.UnauthorizedAccessException;
import com.example.exception.UsernameCollisionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> registerNewAccount(@RequestBody Account account) throws UsernameCollisionException, ClientErrorException {
            return ResponseEntity.ok(accountService.registerNewAccount(account));
    }

    @PostMapping("login")
    public ResponseEntity<Account> loginToAccout(@RequestBody Account account) throws UnauthorizedAccessException {
        Optional<Account> result = accountService.login(account);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        throw new UnauthorizedAccessException();
    }

    // SP3: Create new message
    @PostMapping("messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message) throws ClientErrorException {
        return ResponseEntity.ok(messageService.createMessage(message));
    }

    // SP4: Retrieve all Messages
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok((messageService.getAllMessages()));
    }

    // SP5: Retrieve message by message ID
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable int messageId){
        return ResponseEntity.ok(messageService.getMessageByMessageId(messageId));
    }

    // SP6: Delete message by message ID. Return 1 or null
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<String> deleteMessageByMessageId(@PathVariable int messageId) {
        int result = messageService.deleteMessageByMessageId(messageId);
        return ResponseEntity.ok(result==1?"1":"");
    }

    // SP7: Update message text by message id
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(@PathVariable int messageId, @RequestBody Message message) throws ClientErrorException {
        return ResponseEntity.ok(messageService.updateMessageText(messageId, message.getMessageText()));
    }

    // SP8: Retrieve all messages with posted_by = ?
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesOfAUser(@PathVariable("accountId") int accountId) {
        return ResponseEntity.ok(messageService.getAllMessagesOfAUser(accountId));
    }
}
