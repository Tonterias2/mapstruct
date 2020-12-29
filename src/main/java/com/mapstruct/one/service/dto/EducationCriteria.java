package com.mapstruct.one.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mapstruct.one.domain.Education} entity. This class is used
 * in {@link com.mapstruct.one.web.rest.EducationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /educations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EducationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter degreeName;

    private StringFilter institute;

    private IntegerFilter yearOfPassing;

    public EducationCriteria() {
    }

    public EducationCriteria(EducationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.degreeName = other.degreeName == null ? null : other.degreeName.copy();
        this.institute = other.institute == null ? null : other.institute.copy();
        this.yearOfPassing = other.yearOfPassing == null ? null : other.yearOfPassing.copy();
    }

    @Override
    public EducationCriteria copy() {
        return new EducationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(StringFilter degreeName) {
        this.degreeName = degreeName;
    }

    public StringFilter getInstitute() {
        return institute;
    }

    public void setInstitute(StringFilter institute) {
        this.institute = institute;
    }

    public IntegerFilter getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(IntegerFilter yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EducationCriteria that = (EducationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(degreeName, that.degreeName) &&
            Objects.equals(institute, that.institute) &&
            Objects.equals(yearOfPassing, that.yearOfPassing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        degreeName,
        institute,
        yearOfPassing
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (degreeName != null ? "degreeName=" + degreeName + ", " : "") +
                (institute != null ? "institute=" + institute + ", " : "") +
                (yearOfPassing != null ? "yearOfPassing=" + yearOfPassing + ", " : "") +
            "}";
    }

}
