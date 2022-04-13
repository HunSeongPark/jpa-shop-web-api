package hellojpa.jpashopp.web.controller;

import hellojpa.jpashopp.domain.item.Book;
import hellojpa.jpashopp.domain.item.Item;
import hellojpa.jpashopp.service.ItemService;
import hellojpa.jpashopp.web.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Hunseong on 2022/04/13
 */
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(@ModelAttribute BookForm bookForm) {
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid @ModelAttribute BookForm bookForm, BindingResult result) {
        if (result.hasErrors()) {
            return "items/createItemForm";
        }

        Book book = Book.createBook(bookForm.getName(), bookForm.getPrice(),
                bookForm.getStockQuantity(), bookForm.getAuthor(), bookForm.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Book book = (Book) itemService.findItem(itemId);
        BookForm bookForm = BookForm.fromEntity(book);
        model.addAttribute("form" ,bookForm);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute BookForm form) {
        itemService.updateItem(form);
        return "redirect:/items";
    }
}
