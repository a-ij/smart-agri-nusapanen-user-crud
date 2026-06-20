package com.project.smartcommunity.service;

import com.project.smartcommunity.dto.MonitoringResponse;

import java.util.List;

public interface MonitoringService {
    List<MonitoringResponse> getFoodAvailability();
}
