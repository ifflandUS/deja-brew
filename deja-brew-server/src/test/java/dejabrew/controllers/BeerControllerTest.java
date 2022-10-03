package dejabrew.controllers;

import com.fasterxml.jackson.databind.json.JsonMapper;
import dejabrew.data.BeerJdbcTemplateRepository;
import dejabrew.models.Beer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest {

    @MockBean
    BeerJdbcTemplateRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturn400WhenInvalidAdd() throws Exception {
        Beer alias = new Beer(1,"beer", 4.2, "American Lager", "cool-brewery-co" );
        String response = jsonMapper(alias);

        RequestBuilder requestBuilder = post("/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(response)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn201WhenAdded() throws Exception {
        Beer beer = new Beer("beer", 4.2, "American Lager", "cool-brewery-co" );
        Beer createdBeer = new Beer(1,"beer", 4.2, "American Lager", "cool-brewery-co" );

        String request = jsonMapper(beer);
        String response = jsonMapper(createdBeer);

        Mockito.when(repository.findBeersByBrewery(beer.getBreweryId())).thenReturn(null);
        Mockito.when(repository.addBeer(any())).thenReturn(createdBeer);

        RequestBuilder requestBuilder = post("/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(response));
    }

    @Test
    void shouldReturn409WhenUpdatedWithMismatchIds() throws Exception {

        Beer beer = new Beer(1,"beer", 4.2, "American Lager", "cool-brewery-co" );

        String update = jsonMapper(beer);

        RequestBuilder requestBuilder = put("/beer/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(requestBuilder)
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn400WhenUpdatedWithMissingInfo() throws Exception {

        Beer beer = new Beer();
        beer.setBeerId(3);

        String update = jsonMapper(beer);

        RequestBuilder requestBuilder = put("/beer/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenUpdatedWithMissingId() throws Exception {

        Beer beer = new Beer(6,"beer", 4.2, "American Lager", "cool-brewery-co" );

        String update = jsonMapper(beer);

        Mockito.when(repository.findBeersById(beer.getBeerId())).thenReturn(null);

        RequestBuilder requestBuilder = put("/beer/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenUpdated() throws Exception {
        Beer beer = new Beer(6,"beer", 4.2, "American Lager", "cool-brewery-co" );
        Beer createdBeer = new Beer(6,"beer 3", 4.2, "American Eagle Lager", "cool-brewery-co" );

        String update = jsonMapper(createdBeer);

        Mockito.when(repository.findBeersById(beer.getBeerId())).thenReturn(beer);
        Mockito.when(repository.updateBeer(createdBeer)).thenReturn(true);

        RequestBuilder requestBuilder = put("/beer/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(update))
        ;
    }

    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {
        Mockito.when(repository.deleteBeer(5)).thenReturn(false);

        mvc.perform(delete("/beer/5").with(user("admin").roles("USER","ADMIN")))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldReturn200WhenDeleted() throws Exception {
        Mockito.when(repository.deleteBeer(1)).thenReturn(true);

        mvc.perform(delete("/beer/1").with(user("admin").roles("USER","ADMIN")))
                .andExpect(status().isOk());
    }



    private String jsonMapper(Object input ) throws Exception{
        JsonMapper mapper = new JsonMapper();
        return mapper.writeValueAsString(input);
    }

}