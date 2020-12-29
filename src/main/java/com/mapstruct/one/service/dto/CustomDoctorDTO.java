package com.mapstruct.one.service.dto;

import com.mapstruct.one.domain.Doctor;
import com.mapstruct.one.domain.Education;

import java.io.Serializable;
// import java.time.Instant;
// import java.util.Objects;

public class CustomDoctorDTO implements Serializable {

    /**
     *
     */
    // private static final long serialVersionUID = 1L;

    // private Long id;

    // private Doctor name;

    // private Education degreeName;

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public Doctor getName() {
    //     return name;
    // }

    // public void setName(Doctor name) {
    //     this.name = name;
    // }

    // public Education getDegreeName() {
    //     return degreeName;
    // }

    // public void setDegreeName(Education degreeName) {
    //     this.degreeName = degreeName;
    // }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((degreeName == null) ? 0 : degreeName.hashCode());
    //     result = prime * result + ((id == null) ? 0 : id.hashCode());
    //     result = prime * result + ((name == null) ? 0 : name.hashCode());
    //     return result;
    // }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null)
    //         return false;
    //     if (getClass() != obj.getClass())
    //         return false;
    //     CustomDoctorDTO other = (CustomDoctorDTO) obj;
    //     if (degreeName == null) {
    //         if (other.degreeName != null)
    //             return false;
    //     } else if (!degreeName.equals(other.degreeName))
    //         return false;
    //     if (id == null) {
    //         if (other.id != null)
    //             return false;
    //     } else if (!id.equals(other.id))
    //         return false;
    //     if (name == null) {
    //         if (other.name != null)
    //             return false;
    //     } else if (!name.equals(other.name))
    //         return false;
    //     return true;
    // }

    // @Override
    // public String toString() {
    //     return "CustomDoctorDTO [degreeName=" + degreeName + ", id=" + id + ", name=" + name + "]";
    // }



    private Long id;

    private String name;

    private String degreeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((degreeName == null) ? 0 : degreeName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomDoctorDTO other = (CustomDoctorDTO) obj;
        if (degreeName == null) {
            if (other.degreeName != null)
                return false;
        } else if (!degreeName.equals(other.degreeName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomDoctorDTO [degreeName=" + degreeName + ", id=" + id + ", name=" + name + "]";
    }

}


