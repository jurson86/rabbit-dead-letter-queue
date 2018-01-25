package com.example.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Service;

import com.example.consumer.api.Constants;

@Service
public class AmqpConfig {
	
	public Boolean init(String inQueue){
		incomingQueue(inQueue,inQueue);
		String deadQueue = "dlq-" + inQueue;
		binding(deadQueue,inQueue);
		return true;
	}

    Queue incomingQueue(String inQueue,String deadKey) {
        return QueueBuilder.durable(inQueue)
                .withArgument("x-dead-letter-exchange", Constants.DEAD_LETTER_EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", deadKey)
                .withArgument("x-message-ttl", Constants.RETRY_DELAY)
                .build();
    }
    
    // 使用exchangeKey将队列绑定到exchange上
    Binding binding(String deadQueue,String deadKey) {
      return BindingBuilder.bind(deadQueue(deadQueue)).to(exchange()).with(deadKey);
    }

    Queue deadQueue(String deadQueue) {
        return QueueBuilder.durable(deadQueue).build();
    }
    
    //创建一个topicExchange
    TopicExchange exchange() {
      return new TopicExchange(Constants.DEAD_LETTER_EXCHANGE_NAME);
    }


}
