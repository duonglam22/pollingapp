package com.vnpt.polling.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit  {

    @CreatedBy
    @Column(updatable = false)
    @Setter @Getter
    private Long createdBy;

    @LastModifiedBy
    @Setter @Getter
    private Long updatedBy;


}
