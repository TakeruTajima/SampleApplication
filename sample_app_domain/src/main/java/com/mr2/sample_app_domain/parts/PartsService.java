package com.mr2.sample_app_domain.parts;

import androidx.annotation.NonNull;

public class PartsService {
    private final PartsRepository repository;

    public PartsService(@NonNull PartsRepository repository) {
        this.repository = repository;
    }

    public boolean isDuplicated(Parts parts){
        return repository.find(parts.getMaker(), parts.getModel()).isPresent();
        //do something
    }
}
