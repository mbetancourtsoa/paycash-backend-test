package co.com.paycash.itemservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.paycash.itemservice.dao.ItemRepository;
import co.com.paycash.itemservice.model.Item;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<String> getTitles(Double rating) {
        return itemRepository.findItemsWithAverageRatingLowerThan(rating)
                .stream()
                .map(Item::getTitle)
                .toList();
    }
}
