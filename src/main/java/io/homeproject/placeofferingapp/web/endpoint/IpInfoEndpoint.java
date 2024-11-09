package io.homeproject.placeofferingapp.web.endpoint;

import io.homeproject.placeofferingapp.geoapify.model.IpInfo;
import io.homeproject.placeofferingapp.geoapify.service.IpInfoService;
import io.homeproject.placeofferingapp.utils.WebUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * author: vbondarchuk
 * date: 9/7/2024
 * time: 2:08 PM
 **/


@RestController
@RequestMapping("/ipinfo")
public final class IpInfoEndpoint implements Endpoint {
    private final IpInfoService ipInfoService;

    public IpInfoEndpoint(IpInfoService ipInfoService) {
        this.ipInfoService = ipInfoService;
    }

    @GetMapping
    public IpInfo getIpInfo() {
        return ipInfoService.getIpInfo(WebUtils.getRequesterIp());
    }
}
