package com.mapstruct.one.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Education.
 */
@Entity
@Table(name = "education")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "degree_name")
    private String degreeName;

    @Column(name = "institute")
    private String institute;

    @Column(name = "year_of_passing")
    private Integer yearOfPassing;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public Education degreeName(String degreeName) {
        this.degreeName = degreeName;
        return this;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getInstitute() {
        return institute;
    }

    public Education institute(String institute) {
        this.institute = institute;
        return this;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Integer getYearOfPassing() {
        return yearOfPassing;
    }

    public Education yearOfPassing(Integer yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
        return this;
    }

    public void setYearOfPassing(Integer yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Education)) {
            return false;
        }
        return id != null && id.equals(((Education) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", degreeName='" + getDegreeName() + "'" +
            ", institute='" + getInstitute() + "'" +
            ", yearOfPassing=" + getYearOfPassing() +
            "}";
    }
}
