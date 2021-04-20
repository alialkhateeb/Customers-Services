package com.customer.service.demo.dto;

public class Services {

    private int serviceId;
    private String serviceName;
    private String servicesDescription;


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
