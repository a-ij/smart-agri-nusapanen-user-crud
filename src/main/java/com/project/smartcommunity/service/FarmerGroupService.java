package com.project.smartcommunity.service;

import com.project.smartcommunity.dto.FarmerGroupRequest;
import com.project.smartcommunity.model.FarmerGroup;

public interface FarmerGroupService extends CrudService<FarmerGroup, FarmerGroupRequest> {
    FarmerGroup assignCommodity(Long groupId, Long commodityId);
}
