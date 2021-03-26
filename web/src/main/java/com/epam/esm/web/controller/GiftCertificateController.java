package com.epam.esm.web.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.persistence.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {
    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/certificates")
    public List<GiftCertificate> showAllGiftCertificate() {
        return giftCertificateService.getAll();
    }

    @GetMapping("/hello-world")
    public String sayHello() {
        return "Hello world";
    }
}
