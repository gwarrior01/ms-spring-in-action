package com.optimagrowth.organization.events.source;

import com.optimagrowth.organization.events.model.OrganizationChangeModel;
import com.optimagrowth.organization.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Source {

    private final StreamBridge streamBridge;

    public void publishOrganizationChange(String action, String organizationId) {
        log.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);

        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                organizationId,
                UserContextHolder.getContext().getCorrelationId()
        );

        streamBridge.send("outboundOrgChanges-out-0", change);
    }
}
