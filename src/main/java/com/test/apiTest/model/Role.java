package com.test.apiTest.model;


import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long level;

    //private Long subcategory_id;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    public Role(Long id, String name, Long level, Subcategory subcategory, Division division) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.subcategory = subcategory;
        this.division = division;
    }


//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Skill> skills = new HashSet<>();


//    public Role(Long id, String name, Long level, Long subcategory_id) {
//        this.id = id;
//        this.name = name;
//        this.level = level;
//        this.subcategory_id = subcategory_id;
//    }


    public Role() {
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }
//    public Long getSubcategory_id() {
//        return subcategory_id;
//    }

//    public void setSubcategory_id(Long subcategory_id) {
//        this.subcategory_id = subcategory_id;
//    }

    public Division getDivision() {
        return division;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Skill> getSkills() {
//        return skills;
//    }
//
//    public void setSkills(Set<Skill> skills) {
//        this.skills = skills;
//    }
}
