package com.example.vk_bot.repository;

import com.example.vk_bot.entity.MessageIn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Repository for {@link MessageIn} entity. */
@Repository
public interface MessageInRepository extends CrudRepository<MessageIn, Long> {

}
