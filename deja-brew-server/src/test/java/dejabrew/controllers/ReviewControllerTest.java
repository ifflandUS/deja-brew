package dejabrew.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dejabrew.data.ReviewRepository;
import dejabrew.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
                .with(user("admin").roles("USER","ADMIN"));;

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn200WhenFoundFindById() throws Exception {
        Review review = new Review(5, 3, "new brewery", 4, "testing review");
        ObjectMapper mapper = new JsonMapper();
        String responseBody = mapper.writeValueAsString(review);

        when(repository.findById(5)).thenReturn(review);

        mvc.perform( get("/review/5") )
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
        int id = 4;

        when(repository.deleteById(id)).thenReturn(true);

        mvc.perform( delete("/review/" + id) )
                .andExpect( status().isNoContent() );
    }

    @Test
    void shouldReturn404WhenNothingToDelete() throws Exception {
        mvc.perform( delete("/review/10") )
                .andExpect( status().isNotFound() );
    }

    @Test
    void shouldReturn204WhenUpdateIsSuccessful() throws Exception {
        Review updateReview = new Review(1, 2,"Updated Brewery",4,"updated review");

        ObjectMapper mapper = new JsonMapper();
        String requestBody = mapper.writeValueAsString(updateReview);

        when( repository.update( any() )).thenReturn(true);

        mvc.perform( put("/review/1").contentType(MediaType.APPLICATION_JSON).content(requestBody) )
                .andExpect( status().isNoContent() );

    }


    private String jsonMapper(Object input ) throws Exception{
        JsonMapper mapper = new JsonMapper();
        return mapper.writeValueAsString(input);
    }


}