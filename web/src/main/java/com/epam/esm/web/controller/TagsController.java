package com.epam.esm.web.controller;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {
    private TagService tagService;

    @Autowired
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public List<Tag> showAllTags() {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag showTag(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Tag Controller";
    }
}
