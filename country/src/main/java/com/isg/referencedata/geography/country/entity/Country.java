package com.isg.referencedata.geography.country.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(name = "Country", description = "This is model class of Country, it contains property and getter-setter methods")
public class Country {

    @Id
    @Schema(description = "This is Numeric Id of the Country as per ISO Standard", required = true, maxLength = 999, example = "356")
    private int id;

    @Schema(description = "Name of the Country, maximum size of Country name is 60 character", required = true, example = "India", maxLength = 60)
    @Size(max = 60, message = "CountryName cannot be more than 60 characters")
    @NotEmpty
    private String name;

    @Schema(description = "It is 3 letter aplha code of country as per ISO Standard", required = true, example = "IND", maxLength = 3)
    @Size(min = 3, max = 3, message = "Alpha code must be of 3 character")
    @NotEmpty
    private String code;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;

    public Country() {

    }

    public Country(int id,
            @Size(max = 10, message = "CountryName cannot be more than 60 characters") @NotEmpty String name,
            @Size(min = 3, max = 3, message = "Alpha code must be of 3 character") @NotEmpty String code,
            String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "Country [id=" + id + ", name=" + name + ", code=" + code + ", createdBy=" + createdBy + ", createdDate="
                + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
    }

}
