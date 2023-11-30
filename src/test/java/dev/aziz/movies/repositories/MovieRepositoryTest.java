package dev.aziz.movies.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import dev.aziz.movies.entities.Movie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        String description = "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.";

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
        List<Movie> all = movieRepository.findAll();

        assertAll(() -> {
            assertTrue(movie.isPresent());
            assertEquals("Titanic", movie.get().getTitle());
        });
    }

    @Test
    void searchMoviesWithThePersonTest() {
        //given
        String name = "kate";
        int pageNumber = 0;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        //when
        Page<String> listOfNames = movieRepository.searchMoviesWithThePerson(name, pageRequest);
        //then
        assertAll(() -> {
            assertNotNull(listOfNames);
            assertEquals(listOfNames.getContent().get(0).toLowerCase(), "titanic");
        });
    }
}
