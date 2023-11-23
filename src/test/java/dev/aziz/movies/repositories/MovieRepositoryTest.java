package dev.aziz.movies.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import dev.aziz.movies.entities.Movie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void findMovieByIdTest() {
        //given
        Long id = 1L;
        String title = "Titanic";
        String description = "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.";

        //when
        Optional<Movie> movie = movieRepository.findMovieById(id);

        //then
        assertAll(() -> {
            assertTrue(movie.isPresent());
            assertEquals(title, movie.get().getTitle());
            assertEquals(description, movie.get().getDescription());
        });
    }

    @Test
    void findAllTest (){
        assertEquals(movieRepository.findAll().size(), 3);
    }

    @Test
    void existsMovieByTitleTest (){
        String title = "Titanic";

        boolean b = movieRepository.existsMovieByTitle(title);

        assertAll(() -> {
            assertTrue(b);
        });
    }

    @Test
    void findMovieByTitleTest (){
        String title = "Titanic";

        Optional<Movie> movie = movieRepository.findMovieByTitle(title);
        try {
            System.out.println(movie.get().getId());
        } catch (Exception ex) {
        }
        List<Movie> all = movieRepository.findAll();
        System.out.println(all);
        System.out.println(all.size());

        assertAll(() -> {
            assertTrue(movie.isPresent());
            assertEquals("Titanic", movie.get().getTitle());
        });
    }

}
