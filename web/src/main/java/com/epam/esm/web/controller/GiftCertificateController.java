package com.epam.esm.web.controller;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<GiftCertificateDto> showAll() {
        return giftCertificateService.getAll();
    }

    @GetMapping("/{id}")
    public GiftCertificateDto get(@PathVariable Long id) {
        Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificateService.getById(id);
        return optionalGiftCertificateDto.
                orElseThrow(() -> new EntityNotFoundException("Requested resource not found (id = " + id +")"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificateService.save(giftCertificateDto);
        return optionalGiftCertificateDto.
                orElseThrow(() -> new EntityNotFoundException("Gift certificate did't add to DB"));
    }

    //TODO: Before release delete this method
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Gift Controller";
    }
}
