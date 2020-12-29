package com.mapstruct.one.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mapstruct.one.domain.Education} entity.
 */
public class EducationDTO implements Serializable {
    
    private Long id;

    private String degreeName;

    private String institute;

    private Integer yearOfPassing;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Integer getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(Integer yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationDTO)) {
            return false;
        }

        return id != null && id.equals(((EducationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationDTO{" +
            "id=" + getId() +
            ", degreeName='" + getDegreeName() + "'" +
            ", institute='" + getInstitute() + "'" +
            ", yearOfPassing=" + getYearOfPassing() +
            "}";
    }
}
