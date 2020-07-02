package com.mr2.sample_app_domain.parts;

public class PartsService {
    public boolean isDuplicated(Parts parts){
        PartsRepository repository = null;
        Parts p = repository.findOne(parts.getMaker(), parts.getModel());
        return null != p;
        //do something
    };
}
