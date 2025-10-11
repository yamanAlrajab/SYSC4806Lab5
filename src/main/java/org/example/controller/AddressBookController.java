package org.example.controller;

import org.example.model.AddressBook;
import org.example.repository.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AddressBookController {

    private final AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    // Home -> show first address book (if any) using the Thymeleaf view
    @GetMapping("/")
    public String home(Model model) {
        Optional<AddressBook> first = addressBookRepository.findAll().iterator().hasNext()
                ? Optional.of(addressBookRepository.findAll().iterator().next())
                : Optional.empty();

        if (first.isPresent()) {
            model.addAttribute("addressBook", first.get());
            model.addAttribute("buddies", first.get().getBuddies());
        } else {
            model.addAttribute("addressBook", null);
            model.addAttribute("buddies", null);
            model.addAttribute("message", "No address books yet.");
        }
        return "addressbook";
    }

    // View a specific book (lowercase path)
    @GetMapping("/addressbooks/{id}/view")
    public String view(@PathVariable Long id, Model model) {
        AddressBook ab = addressBookRepository.findById(id).orElse(null);
        model.addAttribute("addressBook", ab);
        model.addAttribute("buddies", ab != null ? ab.getBuddies() : null);
        return "addressbook";
    }
}
