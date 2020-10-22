package lv.javaguru.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("lv.javaguru.chatbot")
@ComponentScan(basePackages = "lv.javaguru.chatbot")
public class ChatBotConfiguration {

}
