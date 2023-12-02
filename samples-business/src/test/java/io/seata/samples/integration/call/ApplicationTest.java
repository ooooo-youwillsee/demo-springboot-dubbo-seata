package io.seata.samples.integration.call;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.seata.samples.integration.common.dto.BusinessDTO;
import io.seata.samples.integration.common.response.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Slf4j
public class ApplicationTest {

    private String url = "http://localhost:8104/business/dubbo/buy";

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    void test() {
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setUserId("1");
        businessDTO.setCommodityCode("C201901140001");
        businessDTO.setCount(2);
        businessDTO.setAmount(new BigDecimal("100"));

        ResponseEntity<ObjectResponse> responseEntity = restTemplate.postForEntity(url, businessDTO,
                ObjectResponse.class);
        log.info("response: \n {} ", JSON.toJSONString(responseEntity.getBody(), SerializerFeature.PrettyFormat));
    }
}
