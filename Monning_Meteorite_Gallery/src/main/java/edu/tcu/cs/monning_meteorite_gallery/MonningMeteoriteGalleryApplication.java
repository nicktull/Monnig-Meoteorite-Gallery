package edu.tcu.cs.monning_meteorite_gallery;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MonningMeteoriteGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonningMeteoriteGalleryApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }
}
