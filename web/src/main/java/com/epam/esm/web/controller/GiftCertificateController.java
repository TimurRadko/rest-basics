package com.epam.esm.web.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.EntityNotFoundException;
import com.epam.esm.web.exception.InvalidRequestBodyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/certificates")
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
    return giftCertificateService.getAllByParams(name, description, tagName, sort);
  }

  @GetMapping("/{id}")
  public GiftCertificateDto get(@PathVariable Long id) {
    Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificateService.getById(id);
    return optionalGiftCertificateDto.orElseThrow(
        () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public GiftCertificateDto save(
      @RequestBody GiftCertificateDto giftCertificateDto,
      HttpServletRequest request,
      HttpServletResponse response) {
    Optional<GiftCertificateDto> optionalGiftCertificateDto =
        giftCertificateService.save(giftCertificateDto);

    GiftCertificateDto savedGiftCertificateDto =
        optionalGiftCertificateDto.orElseThrow(
            () -> new EntityNotFoundException("Gift certificate didn't add to DB"));
    Long id = savedGiftCertificateDto.getId();
    String url = request.getRequestURL().toString();
    response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
    return giftCertificateDto;
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDto update(
      @PathVariable long id, @RequestBody GiftCertificateDto giftCertificateDto) {
    return giftCertificateService
        .update(id, giftCertificateDto)
        .orElseThrow(
            () -> new EntityNotFoundException("Gift certificate with id=" + id + " didn't update"));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    giftCertificateService.delete(id);
  }
}
