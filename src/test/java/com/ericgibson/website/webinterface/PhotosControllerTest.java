package com.ericgibson.website.webinterface;

import com.ericgibson.website.gateways.CloudStorageGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.ericgibson.website.TestingConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PhotosControllerTest {

    @MockBean
    private CloudStorageGateway gateway;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGetJavaScript() throws Exception {
        mvc
                .perform(get("/js/photos.js"))
                .andExpect(view().name("photos.js"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetIndex() throws Exception {
        mvc
                .perform(get("/"))
                .andExpect(view().name("index.html"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPhotosIndex() throws Exception {
        mvc
                .perform(get("/photos")
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(view().name("photos/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPhotosNew() throws Exception {
        mvc
                .perform(get("/photos/new")
                        .with(csrf().asHeader())
                        .with(user("user")))
                .andExpect(view().name("photos/new.html"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldPostPhotos() throws Exception {
        Mockito.doNothing().when(gateway).putObject(STORAGE, KEY, FILE);
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
        Mockito.doNothing().when(gateway).deleteObject(STORAGE, "mockKey");
        mvc
                .perform(delete("/photos/{key}", "mockKey").with(csrf().asHeader()).with(user("user")))
                .andExpect(redirectedUrl("/photos"))
                .andExpect(status().isFound());
    }
}