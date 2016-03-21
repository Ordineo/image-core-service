package be.ordina.ordineo.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by DeDu on 17/03/2016.
 */
@FeignClient("image-ordineo")
public interface ImageClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/images/upload")
    public void uploadImage(@RequestParam("file") MultipartFile image);

    @RequestMapping(method = RequestMethod.GET, value = "/api/images/{url:.+}")
    public byte[] getImage(@PathVariable String url);

}