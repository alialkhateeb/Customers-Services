package com.customer.service.demo.dto;

import javax.validation.constraints.NotBlank;

public class Services {

    private int serviceId;
    @NotBlank(message = "Please provide a service name")
    private String serviceName;
    @NotBlank(message = "Please provide description of the service")
    private String servicesDescription;

    public Services(int serviceId, String serviceName, String servicesDescription) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.servicesDescription = servicesDescription;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicesDescription() {
        return servicesDescription;
    }

    public void setServicesDescription(String servicesDescription) {
        this.servicesDescription = servicesDescription;
    }

    @Override
    public String toString() {
        return "Services{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", servicesDescription='" + servicesDescription + '\'' +
                '}';
    }
}
