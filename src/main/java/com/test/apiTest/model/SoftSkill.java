package com.test.apiTest.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "soft_skills")
@NoArgsConstructor
public class SoftSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    private String skillName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Constructors, getters, and setters


    public SoftSkill(Long skillId, String skillName, Role role) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.role = role;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
