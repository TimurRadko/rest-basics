package com.epam.esm.web.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.persistence.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public List<GiftCertificate> showAllGiftCertificates() {
        return giftCertificateService.getAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate showCertificate(@PathVariable Long id) {
        return giftCertificateService.getById(id);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Gift Controller";
    }
}
