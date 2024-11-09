package io.homeproject.placeofferingapp.geoapify.service;

import io.homeproject.placeofferingapp.geoapify.model.IpInfo;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 3:06 PM
 **/

public interface IpInfoService {

    IpInfo getIpInfo(String ip);
}
