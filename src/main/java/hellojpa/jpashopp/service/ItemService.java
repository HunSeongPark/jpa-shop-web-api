package hellojpa.jpashopp.service;

import hellojpa.jpashopp.domain.item.Item;
import hellojpa.jpashopp.repository.ItemRepository;
import hellojpa.jpashopp.web.BookForm;
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

    @Transactional
    public void updateItem(BookForm form) {
        // repository 내 em.find를 통해 영속성 컨텍스트가 관리하는 item 객체를 가져옴
        // 이후 해당 객체에 대한 수정내용은 그대로 db에 반영됨 (Dirty Checking)
        Item item = itemRepository.findOne(form.getId());

        item.update(form.getName(), form.getPrice(), form.getStockQuantity());
    }

    public Item findItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
