package com.abo2.recode.domain.mentor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Entity
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id")
    private Long id;

    // Mentor를 참조하는 MentorSkill 엔티티
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MentorSkill> mentorSkills;

    @NotEmpty
    private String name;

    @NotEmpty
    private Integer careerYear; //경력

    @NotEmpty
    private Long rating; //평점

    @NotEmpty
    private String email; //멘토 연락처

}
