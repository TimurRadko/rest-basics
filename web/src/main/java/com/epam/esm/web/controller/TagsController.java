package com.epam.esm.web.controller;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagsController {
    private TagService tagService;

    @Autowired
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public List<Tag> showAll() {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag get(@PathVariable Long id) throws ServiceException {
        Optional<Tag> optionalTag =  tagService.getById(id);
        return optionalTag.
                orElseThrow(() -> new EntityNotFoundException("Requested resource not found (id = " + id +")"));
    }

    //TODO: Before release delete this method
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Tag Controller";
    }
}
