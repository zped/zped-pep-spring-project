package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Message findByMessageId(int messageId);

    boolean existsByMessageId(int messageId);

    List<Message> findAll();

    List<Message> findAllByPostedBy(int postedBy);
}
