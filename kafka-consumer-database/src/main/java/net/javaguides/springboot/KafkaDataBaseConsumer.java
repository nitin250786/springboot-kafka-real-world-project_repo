package net.javaguides.springboot;

import net.javaguides.springboot.entity.WikimediaData;
import net.javaguides.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDataBaseConsumer {

    private static  final Logger LOGGER = LoggerFactory.getLogger(KafkaDataBaseConsumer.class);

    private WikimediaDataRepository dataRepository;

    public KafkaDataBaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String eventMessage){
        LOGGER.info(String.format("event message received -> %s",eventMessage));
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        dataRepository.save(wikimediaData);
    }
}
