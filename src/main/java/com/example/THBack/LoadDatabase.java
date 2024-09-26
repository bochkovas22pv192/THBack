package com.example.THBack;

import com.example.THBack.models.Employee;
import com.example.THBack.models.Offer;
import com.example.THBack.models.OfferPhoto;
import com.example.THBack.models.OfferRate;
import com.example.THBack.models.enums.OfferRateType;
import com.example.THBack.models.enums.OfferState;
import com.example.THBack.repository.EmployeeRepository;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRateRepository;
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
    CommandLineRunner initDatabase(EmployeeRepository repository, OfferRepository offerRepository,
                                   OfferPhotoRepository offerPhotoRepository, OfferRateRepository offerRateRepository) {

        Employee employee = new Employee( "Ivan", "Ivanov", "Ivanovich",
                "Junior", "88005553535", LocalDate.now(), "Ivan@mail.ru", "Ivan",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d")
        );
        Employee employee2 = new Employee( "Petr", "Petrov", "Ivanovich",
                "Junior", "88005553530", LocalDate.now(), "Petr@mail.ru", "Petr",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d")
        );

        Offer offer = new Offer(1L, "Помыть полы", "Помыть полы", OfferState.APPROVED,
                LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>());

        Offer offer2 = new Offer(2L, "Помыть окна", "Помыть окна", OfferState.WAITING,
                LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>());

        Offer offer3 = new Offer(3L, "Помыть стены", "Помыть стены", OfferState.APPROVED,
                LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>());

        Offer offer4 = new Offer(4L, "Помыть потолок", "Помыть потолок", OfferState.APPROVED,
                LocalDate.now(), employee2, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>());

        OfferRate offerRate = new OfferRate(1L, OfferRateType.LIKE, offer, employee2);
        OfferRate offerRate2 = new OfferRate(2L, OfferRateType.DISLIKE, offer3, employee2);

        OfferPhoto photo = new OfferPhoto(1L, "aaaaaaaa".getBytes(), offer);
        OfferPhoto photo2 = new OfferPhoto(2L, "bbbbbbbb".getBytes(), offer);
        OfferPhoto photo3 = new OfferPhoto(3L, "cccccccc".getBytes(), offer);
        OfferPhoto photo4 = new OfferPhoto(4L, "zzzzzzz".getBytes(), offer2);

        repository.save(employee);
        repository.save(employee2);
        offerRepository.save(offer);
        offerRepository.save(offer2);
        offerRepository.save(offer3);
        offerRepository.save(offer4);
        offerPhotoRepository.save(photo);
        offerPhotoRepository.save(photo2);
        offerPhotoRepository.save(photo3);
        offerPhotoRepository.save(photo4);
        offerRateRepository.save(offerRate);
        offerRateRepository.save(offerRate2);
        return args -> {
            log.info("Preloading " + employee);
            log.info("Preload" + offer);
            log.info("Preload" + offer2);
            log.info("Preload" + photo);
        };
    }

}

