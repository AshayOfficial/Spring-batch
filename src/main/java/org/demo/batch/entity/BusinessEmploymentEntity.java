package org.demo.batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Business-Employment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessEmploymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String seriesReference;
    private String period;
    private Double dataValue;
    private Character suppressed;
    private Character status;
    private String units;
    private Integer magnitude;
    private String subject;
    private String groupss;
    private String seriesTitle1;
    private String seriesTitle2;
    private String seriesTitle3;

}
