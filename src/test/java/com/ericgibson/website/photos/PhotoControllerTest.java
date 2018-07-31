package com.ericgibson.website.photos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.MOCK_MULTIPART_FILE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PhotoControllerTest {

    @MockBean
    private AmazonClient amazonClient;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGetIndex() throws Exception {
        mvc
                .perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPhotosIndex() throws Exception {
        mvc
                .perform(get("/photos")
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(view().name("photos/index"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPhotosNew() throws Exception {
        mvc
                .perform(get("/photos/new")
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(view().name("photos/new"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldPostPhotos() throws Exception {
        Mockito.when(amazonClient.putObject(BUCKET_NAME, MOCK_MULTIPART_FILE)).thenReturn(true);
        mvc
                .perform(multipart("/photos")
                        .file(MOCK_MULTIPART_FILE)
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(redirectedUrl("/photos"))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldDeletePhotosWithKey() throws Exception {
        Mockito.doNothing().when(amazonClient).deleteObject(BUCKET_NAME, "mockKey");
        mvc
                .perform(delete("/photos/{key}", "mockKey").with(csrf().asHeader()).with(user("user")))
                .andExpect(redirectedUrl("/photos"))
                .andExpect(status().isFound());
    }
}