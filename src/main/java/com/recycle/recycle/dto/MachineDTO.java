package com.recycle.recycle.dto;

import com.recycle.recycle.domain.Status;

import java.util.List;

public record MachineDTO(
        Status status,
        boolean isFull,
        List<String> containers)
{
}
