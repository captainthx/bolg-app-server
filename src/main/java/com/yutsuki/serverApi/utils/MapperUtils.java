package com.yutsuki.serverApi.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, T> T mapOne(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> MapperUtils.mapOne(element, targetClass))
                .collect(Collectors.toList());
    }
}
