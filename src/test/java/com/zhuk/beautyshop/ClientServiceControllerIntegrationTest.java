package com.zhuk.beautyshop;

import com.zhuk.beautyshop.controller.ClientServiceController;
import com.zhuk.beautyshop.dto.RequestContext;
import com.zhuk.beautyshop.repo.JdbcClientServiceRepo;
import com.zhuk.beautyshop.repo.JpaClientServiceRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceControllerIntegrationTest {

    @MockBean
    private RequestContext requestContext;

    @MockBean
    private JpaClientServiceRepo jpaClientServiceRepo;

    @MockBean
    private JdbcClientServiceRepo jdbcClientServiceRepo;

    @Autowired
    private ClientServiceController clientServiceController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientServiceController).build();
    }

    @Test
    public void whenHeaderExistsCallJdbcRepo() throws Exception {
        when(requestContext.isJdbcHeaderNull()).thenReturn(false);
        mockMvc.perform(get("/services")
                .locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andReturn();
        verify(requestContext, times(1)).isJdbcHeaderNull();
        verify(jdbcClientServiceRepo, times(1)).findAll();
        verify(jpaClientServiceRepo, times(0)).findAll();
    }

    @Test
    public void ifHeaderDoesNotExistCallJdbcRepo() throws Exception {
        when(requestContext.isJdbcHeaderNull()).thenReturn(false);
        mockMvc.perform(get("/services/{master}", 1)
                .locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andReturn();
        verify(requestContext, times(1)).isJdbcHeaderNull();
        verify(jdbcClientServiceRepo, times(1)).findAllByCategoryIn(Mockito.anyList());
        verify(jpaClientServiceRepo, times(0)).findAllByCategoryIn(Mockito.anyList());
    }

    @Test
    public void ifHeaderDoesNotExistCallJpaRepo() throws Exception {
        when(requestContext.isJdbcHeaderNull()).thenReturn(true);
        mockMvc.perform(get("/services/{master}", 1)
                .locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andReturn();
        verify(requestContext, times(1)).isJdbcHeaderNull();
        verify(jdbcClientServiceRepo, times(0)).findAllByCategoryIn(Mockito.anyList());
        verify(jpaClientServiceRepo, times(1)).findAllByCategoryIn(Mockito.anyList());
    }

    @Test
    public void whenHeaderDoesNotExistCallJpaRepo() throws Exception {
        when(requestContext.isJdbcHeaderNull()).thenReturn(true);
        mockMvc.perform(get("/services")
                .locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andReturn();
        verify(requestContext, times(1)).isJdbcHeaderNull();
        verify(jdbcClientServiceRepo, times(0)).findAll();
        verify(jpaClientServiceRepo, times(1)).findAll();
    }
}
