package com.customer.service.demo.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "service")
public class ServiceEntity {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_service", joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<CustomerEntity> customer;

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

    public List<CustomerEntity> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerEntity> customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceEntity)) return false;

        ServiceEntity serviceEntity = (ServiceEntity) o;

        return serviceId == serviceEntity.serviceId;
    }

    @Override
    public int hashCode() {
        return serviceId;
    }
}
