package com.example.socialntwrkgui.validators;
import com.example.socialntwrkgui.exceptions.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}
