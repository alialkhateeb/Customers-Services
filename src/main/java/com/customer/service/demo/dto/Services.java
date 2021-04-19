package com.customer.service.demo.dto;

public class Services {

    private String serviceName;
    private String ServicesDescription;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicesDescription() {
        return ServicesDescription;
    }

    public void setServicesDescription(String servicesDescription) {
        ServicesDescription = servicesDescription;
    }

    @Override
    public String toString() {
        return "Services{" +
                "serviceName='" + serviceName + '\'' +
                ", ServicesDescription='" + ServicesDescription + '\'' +
                '}';
    }
}
