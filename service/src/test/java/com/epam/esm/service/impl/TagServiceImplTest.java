package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;
    private TagValidator tagValidator = Mockito.mock(TagValidator.class);

    private Tag firstTestEntity;
    private List<Tag> expectedTags;
    private static final String SORT = null;
    private static final long ID_FOR_MANIPULATIONS = 1L;

    @BeforeEach
    void setUp() {
        firstTestEntity = new Tag(1L, "test1");
        Tag secondTestEntity = new Tag(2L, "test2");
        expectedTags = Arrays.asList(firstTestEntity, secondTestEntity);
    }

    @Test
    void testGetAllShouldReturnTagListWhenTagsExist() {
        Mockito.lenient().when(tagRepository.getEntitiesListBySpecification(any()))
                .thenReturn(expectedTags);
        List<Tag> actualTags = tagService.getAll(SORT);
        assertEquals(expectedTags, actualTags);
    }

    @Test
    void testGetByIdShouldReturnRightTagWhenItExists() {
        Mockito.lenient().when(tagRepository.getEntityBySpecification(any()))
                .thenReturn(Optional.of(firstTestEntity));
        Tag actualTag = tagService.getById(ID_FOR_MANIPULATIONS).orElse(new Tag());

        assertEquals(firstTestEntity, actualTag);
    }

    @Test
    void testSaveShouldReturnWasSavedTagWhenParametersIsValid() {
        Mockito.lenient().when(tagRepository
                .getEntityBySpecification(any())).thenReturn(Optional.of(firstTestEntity));
        Mockito.lenient().when(tagRepository.save(firstTestEntity)).thenReturn(Optional.of(firstTestEntity));
        Mockito.lenient().when(tagValidator.validate(firstTestEntity)).thenReturn(true);

        Optional<Tag> optionalActualTag = tagService.save(firstTestEntity);

        assertEquals(firstTestEntity, optionalActualTag.get());
    }

    @Test
    void testSaveShouldThrowServiceExceptionWhenTagInNotValid() {
        Mockito.lenient().when(tagRepository.save(firstTestEntity)).thenReturn(Optional.of(firstTestEntity));
        Mockito.lenient().when(tagValidator.validate(firstTestEntity)).thenReturn(false);

        assertThrows(ServiceException.class, () -> tagService.save(firstTestEntity));
    }

    @Test
    void testDeleteShouldDeleteTagWhenIsExists() {
        int expectedResult = 1;
        Mockito.lenient().when(tagRepository.delete(ID_FOR_MANIPULATIONS)).thenReturn(expectedResult);
        int actualResult = tagService.delete(ID_FOR_MANIPULATIONS);
        assertEquals(expectedResult, actualResult);
    }
}