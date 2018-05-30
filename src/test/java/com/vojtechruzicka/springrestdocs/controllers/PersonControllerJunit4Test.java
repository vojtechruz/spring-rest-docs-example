package com.vojtechruzicka.springrestdocs.controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerJunit4Test {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Rule
    public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(this.jUnitRestDocumentation))
                .build();
    }

    @Test
    public void getPersonByIdShouldReturnOk() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/persons/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("persons/get-by-id",
                        pathParameters(parameterWithName("id")
                                .description("Identifier of the person to be obtained.")),
                        responseFields(
                                fieldWithPath("id")
                                        .description("Unique identifier of the person."),
                                fieldWithPath("firstName")
                                        .description("First Name of the person."),
                                fieldWithPath("lastName")
                                        .description("Last Name of the person."),
                                fieldWithPath("age")
                                        .description("Age of the person in years.")
                        )
                ));
    }

}