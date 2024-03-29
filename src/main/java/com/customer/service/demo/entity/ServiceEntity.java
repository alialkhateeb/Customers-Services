package com.customer.service.demo.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "service_name")
    @NotBlank(message = "Please provide a name to the given service")
    private String serviceName;

    @Column(name = "service_desciption")
    @NotBlank(message = "Please provide description to the given service")
    private String serviceDescription;

    @Column(name = "service_created")
    @CreationTimestamp
    private Date serviceCreated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_service", joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<CustomerEntity> customer;

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

    public Set<CustomerEntity> getCustomer() {
        return customer;
    }

    public void setCustomer(Set<CustomerEntity> customer) {
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
