package dejabrew.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dejabrew.data.VisitJdbcTemplateRepository;
import dejabrew.models.Visit;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
class VisitControllerTest {
    @MockBean
    VisitJdbcTemplateRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturn201WhenAdded() throws Exception {
        Visit visit = new Visit(0,3, "new brewery", LocalDate.of(2022,8,13));
        Visit createdVisit = new Visit(1, 3, "new brewery", LocalDate.of(2022,8,13));

        when(repository.add(any())).thenReturn(createdVisit);
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String visitJson = jsonMapper.writeValueAsString(visit);
        String expectedJson = jsonMapper.writeValueAsString(createdVisit);

        var request = post("/visit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(visitJson)
                .with(user("admin").roles("USER","ADMIN"));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

//    @Test
//    void shouldReturn200WhenUpdated() throws Exception {
//        Visit visit = new Visit(6,1, "new visit", LocalDate.of(2022,9,10));
//        Visit createdVisit = new Visit(6,2, "new visit", LocalDate.of(2022,9,10) );
//
//        String update = jsonMapper(createdVisit);
//
//
//        Mockito.when(repository.findById(visit.getVisitId())).thenReturn(visit);
//        Mockito.when(repository.update(createdVisit)).thenReturn(true);
//
//        RequestBuilder requestBuilder = put("/visit/6")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(update)
//                .with(user("admin").roles("USER","ADMIN"));
//
//        mvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(update))
//        ;
//    }

    @Test
    void shouldReturn200WhenFoundFindById() throws Exception {
        Visit visit = new Visit(5, 3, "new brewery", LocalDate.of(2022,8,13));
        ObjectMapper mapper = new JsonMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String responseBody = mapper.writeValueAsString(visit);

        when(repository.findById(5)).thenReturn(visit);

        mvc.perform( get("/visit/5") )
                .andExpect( content().json(responseBody) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) );

    }

    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {
        Mockito.when(repository.deleteById(5)).thenReturn(false);

        mvc.perform(delete("/visit/5").with(user("admin").roles("USER","ADMIN")))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenDeleted() throws Exception {
        Mockito.when(repository.deleteById(1)).thenReturn(true);

        mvc.perform(delete("/review/1").with(user("admin").roles("USER","ADMIN")))
                .andExpect(status().isOk());
    }

    private String jsonMapper(Object input ) throws Exception{
        JsonMapper mapper = new JsonMapper();
        return mapper.writeValueAsString(input);
    }

}