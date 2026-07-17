package co.com.paycash.itemservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import co.com.paycash.itemservice.exception.GlobalExceptionHandler;
import co.com.paycash.itemservice.service.ItemService;

@WebMvcTest(controllers = ItemController.class)
@org.springframework.context.annotation.Import(GlobalExceptionHandler.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    void returnsTitlesAsUtf8Json() throws Exception {
        when(itemService.getTitles(3.5)).thenReturn(List.of("Auriculares Bluetooth", "Silla Ergonómica"));

        mockMvc.perform(get("/titles").param("rating", "3.5"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$[0]").value("Auriculares Bluetooth"))
                .andExpect(jsonPath("$[1]").value("Silla Ergonómica"));
    }

    @Test
    void returnsBadRequestWhenRatingIsMissing() throws Exception {
        mockMvc.perform(get("/titles"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
