package com.zhuk.beautyshop;

import com.zhuk.beautyshop.repo.JdbcFavourRepo;
import com.zhuk.beautyshop.repo.JpaFavourRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FavourControllerIntegrationTest {

    @MockBean
    private JpaFavourRepo jpaClientServiceRepo;

    @MockBean
    private JdbcFavourRepo jdbcClientServiceRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenHeaderExistsCallJdbcRepo() throws Exception {
        mockMvc.perform(get("/services")
                .locale(Locale.ENGLISH)
                .header("JdbcTemplate", "true"))
                .andExpect(status().isOk())
                .andReturn();
        verify(jdbcClientServiceRepo, times(1)).findAllByLanguage(Locale.ENGLISH.getLanguage());
        verify(jpaClientServiceRepo, times(0)).findAllByLanguage(Locale.ENGLISH.getLanguage());
    }

    @Test
    public void whenHeaderDoesNotExistCallJpaRepo() throws Exception {
        mockMvc.perform(get("/services")
                .locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andReturn();
        verify(jdbcClientServiceRepo, times(0)).findAllByLanguage(Locale.ENGLISH.getLanguage());
        verify(jpaClientServiceRepo, times(1)).findAllByLanguage(Locale.ENGLISH.getLanguage());
    }
}
