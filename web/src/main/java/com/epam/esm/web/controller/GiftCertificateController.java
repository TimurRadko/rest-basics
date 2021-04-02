package com.epam.esm.web.controller;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.EntityNotFoundException;
import com.epam.esm.web.exception.InvalidRequestBodyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public List<GiftCertificateDto> getAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tagName,
            @RequestParam(value = "sort", required = false) String sort) {
        if (name == null && description == null && tagName == null) {
            return giftCertificateService.getAll(sort);
        }
        List<GiftCertificateDto> giftCertificateDtos = new ArrayList<>();
        if (name != null || description != null) {
            giftCertificateDtos = giftCertificateService
                    .getGiftCertificatesByNameOrDescriptionPart(name, description, sort);
        }
        if (tagName != null) {
            giftCertificateDtos = giftCertificateService
                    .getGiftCertificatesByTagName(giftCertificateDtos, tagName, sort);
        }
        return giftCertificateDtos;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto get(@PathVariable Long id) {
        Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificateService.getById(id);
        return optionalGiftCertificateDto.
                orElseThrow(() -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto) {
        try {
            Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificateService.save(giftCertificateDto);
            return optionalGiftCertificateDto.
                    orElseThrow(() -> new EntityNotFoundException("Gift certificate didn't add to DB"));
        } catch (ServiceException e) {
            throw new InvalidRequestBodyException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(id, giftCertificateDto)
                .orElseThrow(() -> new EntityNotFoundException("Gift certificate with id=" + id + " didn't update"));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        giftCertificateService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
        giftCertificateService.delete(id);
        return "The Gift Certificate with id = " + id + " was deleted";
    }
}
