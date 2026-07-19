package com.project.model;

import com.project.embeddable.Support;
import com.project.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String iataCode;

    @Column(nullable = false, unique = true)
    private String icaoCode;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Support support;

    private String alias;

    private String logoUrl;

    private String website;

    @Column(nullable = false , unique = true)
    private Long ownerId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AirlineStatus status = AirlineStatus.ACTIVE;

    private String alliance;

    private Long headquartersCityId;

    private Long updatedById;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @CreatedDate
    @Column(updatable = false , nullable = false)
    private Instant createdAt;

}
