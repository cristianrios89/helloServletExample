package com.greeninc.user.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Custom object mapper for serialization/deserialization of objects.
 */
public final class CustomMapper {

    /**
     * Object to String serializer.
     */
    private static final ObjectMapper OBJECT_MAPPER = constructMapper();

    private CustomMapper() {
    }

    private static ObjectMapper constructMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * @param o {@link Object}
     * @return {@link String}
     */
    public static Optional<String> serialize(final Object o) {
        try {
            return Optional.of(OBJECT_MAPPER.writeValueAsString(o));
        } catch (final JsonProcessingException e) {
            return Optional.empty();
        }
    }

    /**
     * @param localDate {@link LocalDate}
     * @return {@link String}
     */
    public static String serializeLocalDate(final LocalDate localDate) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }

    /**
     * Deserialize string of a class.
     *
     * @param string to deserialize.
     * @param clazz  to be constructed.
     * @param <T>    of type.
     * @return {@link T} {@link T}
     */
    public static <T> Optional<T> deserialize(final String string, final Class<T> clazz) {
        try {
            return Optional.of(OBJECT_MAPPER.readValue(string, clazz));
        } catch (final JsonProcessingException e) {
            return Optional.empty();
        }
    }

    /**
     * Deserialize string of a class.
     *
     * @param inputStream to deserialize.
     * @param clazz       to be constructed.
     * @param <T>         of type.
     * @return {@link T} {@link T}
     */
    public static <T> Optional<T> deserialize(final InputStream inputStream, final Class<T> clazz) {
        try {
            return Optional.of(OBJECT_MAPPER.readValue(inputStream, clazz));
        } catch (final IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Deserialize list of a class.
     *
     * @param inputStream to deserialize.
     * @param clazz       to be constructed.
     * @param <T>         of type.
     * @return {@link T} {@link T}
     */
    public static <T> Optional<List<T>> deserializeList(final InputStream inputStream, final Class<T> clazz) {
        try {
            return Optional.of(OBJECT_MAPPER.readValue(inputStream, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz)));
        } catch (final IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Deserializes a list of a class.
     *
     * @param string {@link String}.
     * @param clazz  {@link Class}.
     * @param <T>    {@link T}.
     * @return {@link Optional} of {@link List}.
     */
    public static <T> Optional<List<T>> deserializeList(final String string, final Class<T> clazz) {
        try {
            OBJECT_MAPPER.registerModule(new JavaTimeModule());
            return Optional.of(OBJECT_MAPPER.readValue(string, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz)));
        } catch (final JsonProcessingException e) {
            return Optional.empty();
        }
    }

}
