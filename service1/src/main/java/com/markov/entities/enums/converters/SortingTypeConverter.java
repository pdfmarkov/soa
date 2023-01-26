package com.markov.entities.enums.converters;

import com.markov.entities.enums.Position;
import com.markov.entities.enums.SortingType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SortingTypeConverter implements AttributeConverter<SortingType, String> {

    @Override
    public String convertToDatabaseColumn(SortingType sortingType) {
        if (sortingType == null) {
            return null;
        }
        return sortingType.getSortingType();
    }

    @Override
    public SortingType convertToEntityAttribute(String sortingType) {
        if (sortingType == null) {
            return null;
        }

        return Stream.of(SortingType.values())
                .filter(s -> s.getSortingType().equals(sortingType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
