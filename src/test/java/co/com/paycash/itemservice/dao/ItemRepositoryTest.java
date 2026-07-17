package co.com.paycash.itemservice.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import co.com.paycash.itemservice.model.Item;
import co.com.paycash.itemservice.model.Review;
import co.com.paycash.itemservice.model.User;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findsItemsBelowRatingIncludingItemsWithoutReviews() {
        User user = userRepository.save(User.builder().name("Ana").email("ana@example.com").build());

        Item lowRatedItem = itemRepository.save(Item.builder().title("Low Rated").build());
        reviewRepository.save(Review.builder().rating(1.0).item(lowRatedItem).user(user).build());
        reviewRepository.save(Review.builder().rating(2.0).item(lowRatedItem).user(user).build());

        Item highRatedItem = itemRepository.save(Item.builder().title("High Rated").build());
        reviewRepository.save(Review.builder().rating(4.5).item(highRatedItem).user(user).build());
        reviewRepository.save(Review.builder().rating(5.0).item(highRatedItem).user(user).build());

        Item noReviewsItem = itemRepository.save(Item.builder().title("No Reviews").build());

        var titles = itemRepository.findItemsWithAverageRatingLowerThan(3.0)
                .stream()
                .map(Item::getTitle)
                .toList();

        assertThat(titles).containsExactlyInAnyOrder("Low Rated", "No Reviews");
    }

    @Test
    void returnsNothingWhenAllItemsMeetOrExceedRating() {
        Item item = itemRepository.save(Item.builder().title("Item").build());
        User user = userRepository.save(User.builder().name("Luis").email("luis@example.com").build());
        reviewRepository.save(Review.builder().rating(5.0).item(item).user(user).build());

        var titles = itemRepository.findItemsWithAverageRatingLowerThan(0.0)
                .stream()
                .map(Item::getTitle)
                .toList();

        assertThat(titles).isEmpty();
    }
}
