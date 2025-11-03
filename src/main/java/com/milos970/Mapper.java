package com.milos970;

import com.milos970.model.dto.PCRTestRequest;
import com.milos970.model.dto.PCRTestResponse;
import com.milos970.model.entity.PCRTest;

public final class Mapper
{
    public static PCRTestResponse toResponse(PCRTest test) {
        return new PCRTestResponse();
    }

    public static PCRTestRequest toRequest(PCRTest test) {
        return null;
    }
}
