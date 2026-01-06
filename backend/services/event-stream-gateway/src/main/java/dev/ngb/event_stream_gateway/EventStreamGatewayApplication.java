package dev.ngb.event_stream_gateway;

import dev.ngb.application.ApplicationService;
import dev.ngb.constant.AppConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        basePackages = AppConstants.BASE_PACKAGE,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = ApplicationService.class
        )
)
public class EventStreamGatewayApplication {
    static void main(String[] args) {
        SpringApplication.run(EventStreamGatewayApplication.class, args);
    }
}
