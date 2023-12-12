package com.abo2.recode.domain.estimate;

import com.abo2.recode.domain.badge.Badge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_id")
    private Long id;

    private Integer point;

    @ManyToOne
    @JoinColumn(name = "study_member_estimate_id")
    private StudyMemberEstimate studyMemberEstimate;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;
}
