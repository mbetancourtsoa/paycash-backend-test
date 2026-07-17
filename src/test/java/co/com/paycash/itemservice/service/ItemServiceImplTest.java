package co.com.paycash.itemservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.paycash.itemservice.dao.ItemRepository;
import co.com.paycash.itemservice.model.Item;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void returnsOnlyTitlesFromRepository() {
        Item item1 = Item.builder().title("Item A").build();
        Item item2 = Item.builder().title("Item B").build();
        when(itemRepository.findItemsWithAverageRatingLowerThan(3.0)).thenReturn(List.of(item1, item2));

        List<String> titles = itemService.getTitles(3.0);

        assertThat(titles).containsExactly("Item A", "Item B");
    }
}
