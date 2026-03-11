package ru.movie.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.movie.entity.Movie;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataStoreConfig {

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Movie> movieContainer() {
        return new ArrayList<>();
    }
}
