package com.markov.entities.enums.converters;

import com.markov.entities.enums.EyeColor;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EyeColorConverter implements AttributeConverter<EyeColor, String> {

    @Override
    public String convertToDatabaseColumn(EyeColor status) {
        if (status == null) {
            return null;
        }
        return status.getEyeColor();
    }

    @Override
    public EyeColor convertToEntityAttribute(String eyeColor) {
        if (eyeColor == null) {
            return null;
        }

        return Stream.of(EyeColor.values())
                .filter(s -> s.getEyeColor().equals(eyeColor))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}