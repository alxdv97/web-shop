package ru.alxdv.nfoshop.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "nfo-client", url = "${nfo-client.ribbon.listOfServers}")
public interface UserAuthService {
    @GetMapping("/auth/users/{username}")
    Boolean auth(@PathVariable String username);
}
