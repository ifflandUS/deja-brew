package dejabrew.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dejabrew.data.ReviewRepository;
import dejabrew.models.Review;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @MockBean
    ReviewRepository repository;

    @Autowired
    MockMvc mvc;


    @Test
    void addShouldReturn201() throws Exception {

        Review review = new Review(0, 1, "test brewery",4,"Test review");
        Review expected = new Review(1, 1, "test brewery",4,"Test review");

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String reviewJson = jsonMapper.writeValueAsString(review);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewJson)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn200WhenFoundFindByBreweryId() throws Exception {
        Review review = new Review(5, 3, "new-brewery", 4, "testing review");
        ObjectMapper mapper = new JsonMapper();
        String responseBody = mapper.writeValueAsString(review);

        when(repository.findByBrewery("new-brewery")).thenReturn(review);


        mvc.perform( get("/review/new-brewery") )
                .andExpect( content().json(responseBody) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) );

    }

    @Test
    void shouldReturn200WhenFoundFindByUser() throws Exception {
        List<Review> reviews = new ArrayList<>();
        ObjectMapper mapper = new JsonMapper();
        String responseBody = mapper.writeValueAsString(reviews);

        when(repository.findByUser(any())).thenReturn(reviews);

        RequestBuilder requestBuilder = get("/review/app-user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseBody)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform( get("/review/app-user/1") )
                .andExpect( content().json(responseBody) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) );
    }

    @Test
    void shouldReturn404WhenNoIdFoundFindById() throws Exception {
        mvc.perform( get("/review/2560") )
                .andExpect( status().isNotFound() );
    }

    @Test
    void shouldReturn204WhenSuccessfulDelete() throws Exception {
        Mockito.when(repository.deleteById(4)).thenReturn(true);

        mvc.perform(delete("/review/4").with(user("admin").roles("USER","ADMIN")))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenNothingToDelete() throws Exception {
        mvc.perform(delete("/review/10").with(user("admin").roles("USER","ADMIN")))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkWhenUpdateIsSuccessful() throws Exception {
        Review review = new Review(4,1, "new brewery", 4, "review" );
        Review updatedReview = new Review(4,1, "new brewery", 4, "updated review" );

        String update = jsonMapper(updatedReview);

        when(repository.findById(review.getReviewId())).thenReturn(review);
        when(repository.update(updatedReview)).thenReturn(true);

        RequestBuilder requestBuilder = put("/review/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(update))
        ;

    }


    private String jsonMapper(Object input ) throws Exception{
        JsonMapper mapper = new JsonMapper();
        return mapper.writeValueAsString(input);
    }


}