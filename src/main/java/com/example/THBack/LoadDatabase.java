package com.example.THBack;

import com.example.THBack.models.Employee;
import com.example.THBack.models.Offer;
import com.example.THBack.models.OfferPhoto;
import com.example.THBack.models.enums.OfferState;
import com.example.THBack.repository.EmployeeRepository;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, OfferRepository offerRepository, OfferPhotoRepository offerPhotoRepository) {

        Employee employee = new Employee( "Ivan", "Ivanov", "Ivanovich",
                "Junior", "88005553535", LocalDate.now(), "Ivan@mail.ru", "Ivan",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d")
        );

        Offer offer = new Offer(1L, "Помыть полы", "Помыть полы", OfferState.APPROVED,
                LocalDate.now(), employee, Set.of(), new LinkedHashSet<OfferPhoto>());

        OfferPhoto photo = new OfferPhoto(1L, HexFormat.of().parseHex("aaaaaa"), offer);

        repository.save(employee);
        offerRepository.save(offer);
        offerPhotoRepository.save(photo);
        return args -> {
            log.info("Preloading " + employee);
            log.info("Preload" + offer);
            log.info("Preload" + photo);
        };
    }

}

