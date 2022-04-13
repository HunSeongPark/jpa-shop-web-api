package hellojpa.jpashopp.service;

import hellojpa.jpashopp.domain.item.Item;
import hellojpa.jpashopp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hunseong on 2022/04/13
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
