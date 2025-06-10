package com.optimagrowth.organization.events.source;

import com.optimagrowth.organization.events.model.OrganizationChangeModel;
import com.optimagrowth.organization.model.Organization;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Source {

    private final StreamBridge streamBridge;
    private final Tracer tracer;

    public void publishOrganizationChange(String action, Organization organization) {
        log.info("Sending Kafka message {} for Organization Id: {}", action, organization.getId());

        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                tracer.currentSpan().context().traceId(),
                organization
        );

        streamBridge.send("outboundOrgChanges-out-0", change);
    }
}
