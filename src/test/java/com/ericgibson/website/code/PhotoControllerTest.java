package com.ericgibson.website.code;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PhotoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGetIndex() throws Exception {
        mvc
                .perform(get("/photos")
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(view().name("photos/index"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetNew() throws Exception {
        mvc
                .perform(get("/photos/new")
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(view().name("photos/new"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldPostPhoto() throws Exception {
        mvc
                .perform(post("/photos/new", "/PutObjectTestFile.txt")
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(redirectedUrl("/photos"))
                .andExpect(status().isFound());
    }
}