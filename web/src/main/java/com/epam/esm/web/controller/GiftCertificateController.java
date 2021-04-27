package com.epam.esm.web.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.link.builder.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/certificates")
public class GiftCertificateController {
  private final GiftCertificateService giftCertificateService;
  private final LinkBuilder<GiftCertificateDto> giftCertificateDtoLinkBuilder;

  @Autowired
  public GiftCertificateController(
      GiftCertificateService giftCertificateService,
      LinkBuilder<GiftCertificateDto> giftCertificateDtoLinkBuilder) {
    this.giftCertificateService = giftCertificateService;
    this.giftCertificateDtoLinkBuilder = giftCertificateDtoLinkBuilder;
  }

  @GetMapping()
  public List<GiftCertificateDto> getAll(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam(value = "tag", required = false) List<String> tagNames,
      @RequestParam(value = "sort", required = false) List<String> sorts) {

    return giftCertificateService.getAllByParams(page, size, name, description, tagNames, sorts)
        .stream()
        .map(giftCertificateDtoLinkBuilder::build)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public GiftCertificateDto get(@PathVariable Long id) {
    return giftCertificateDtoLinkBuilder.build(
        giftCertificateService
            .getById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Requested resource not found (id = " + id + ")")));
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
            () -> new EntityNotFoundException("The Gift certificate didn't add to DB"));

    Long id = savedGiftCertificateDto.getId();
    String url = request.getRequestURL().toString();
    response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
    return giftCertificateDtoLinkBuilder.build(savedGiftCertificateDto);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDto update(
      @PathVariable long id, @RequestBody GiftCertificateDto giftCertificateDto) {
    GiftCertificateDto updatedGiftCertificateDto =
        giftCertificateService
            .update(id, giftCertificateDto)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Gift certificate with id=" + id + " didn't update"));
    return giftCertificateDtoLinkBuilder.build(updatedGiftCertificateDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    giftCertificateService.delete(id);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDto updatePrice(
      @PathVariable long id, @RequestBody GiftCertificateDto giftCertificateDto) {
    GiftCertificateDto patchingGiftCertificateDto =
        giftCertificateService
            .updatePrice(id, giftCertificateDto)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "The price of the Gift certificate with id=" + id + " didn't update"));
    return giftCertificateDtoLinkBuilder.build(patchingGiftCertificateDto);
  }
}
