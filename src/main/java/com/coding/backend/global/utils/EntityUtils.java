package com.coding.backend.global.utils;

import com.coding.backend.global.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;

public class EntityUtils {

    public static <T, ID> T getByIdOrThrow(JpaRepository<T, ID> repository, ID id, String notFoundMessage) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));
    }
    public static <T> List<T> getListOrEmpty(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }


}
