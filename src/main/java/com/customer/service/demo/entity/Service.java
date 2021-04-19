package com.customer.service.demo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp serviceCreated;


}
