package com.epam.esm.web.controller;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.EntityNotFoundException;
import com.epam.esm.web.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<Tag> getAll(@RequestParam(value = "sort", required = false) String sort) {
        return tagService.getAll(sort);
    }

    @GetMapping("/{id}")
    public Tag get(@PathVariable Long id) throws ServiceException {
        Optional<Tag> optionalTag =  tagService.getById(id);
        return optionalTag.
                orElseThrow(() -> new EntityNotFoundException("Requested resource not found (id = " + id +")"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Tag save(@RequestBody Tag tag) {
        Optional<Tag> optionalTag = tagService.save(tag);
        return optionalTag.
                orElseThrow(() -> new TagAlreadyExistsException("The Tag already exists in the DB"));
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable long id) {
        tagService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requested resource not found (id = " + id +")"));
        tagService.delete(id);
        return "The Tag with id = " + id + " was deleted";
    }
}
