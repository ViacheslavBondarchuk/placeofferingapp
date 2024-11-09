package io.homeproject.placeofferingapp.geoapify.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 3:26 PM
 **/

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public sealed abstract class AbstractFilterInfo permits CategoryFilterInfo, ConditionFilterInfo {
    protected final String key;
    protected final String info;
    protected final String icon;
}
