package com.customer.service.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_desciption")
    private String serviceDescription;

    @Column(name = "service_created")
    private Date serviceCreated;

    @Column(name = "customer_foreign")
    private Integer customerId;


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

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Date getServiceCreated() {
        return serviceCreated;
    }

    public void setServiceCreated(Date serviceCreated) {
        this.serviceCreated = serviceCreated;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        return serviceId == service.serviceId;
    }

    @Override
    public int hashCode() {
        return serviceId;
    }
}
