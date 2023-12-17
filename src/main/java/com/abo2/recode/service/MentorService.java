package com.abo2.recode.service;

import com.abo2.recode.domain.mentor.Mentor;
import com.abo2.recode.domain.mentor.MentorRepository;
import com.abo2.recode.domain.mentor.MentorSkill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.domain.skill.StudySkill;
import com.abo2.recode.domain.skill.StudySkillRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MentorService {

    SkillRepository skillRepository;
    StudySkillRepository studySkillRepository;
    MentorRepository mentorRepository;

    @Builder
    public MentorService(SkillRepository skillRepository, StudySkillRepository studySkillRepository, MentorRepository mentorRepository) {
        this.skillRepository = skillRepository;
        this.studySkillRepository = studySkillRepository;
        this.mentorRepository = mentorRepository;
    }

    /*
    * 추천 알고리즘
    * 종합 점수 = (w_similarity * 정규화된 유사도의 가중 평균) + (w_experience * 정규화된 경력) + (w_rating * 정규화된 평점)
    * 1. 스터디 룸과 멘토의 스킬 스택을 벡터로 표현
    * 2. 스킬 벡터간 유사도 측정
    * 3. 경력 점수, 평점에 가중치를 줘 종합 점수를 구함
    * 4. 종합 점수 상위 5명을 추천함.
    * */

    @CachePut("mentorRecommendCache")
    public Mentor addOrUpdateMentor(Mentor mentor) {
        // Mentor 엔티티를 저장 또는 업데이트하는 로직을 구현합니다.
        // mentorRepository.save(mentor) 또는 mentorRepository.update(mentor) 등을 사용하여 업데이트합니다.

        // 새로운 Mentor 엔티티를 반환하거나 업데이트된 정보를 반환합니다.
        return mentorRepository.save(mentor);
    }

    @CacheEvict("mentorRecommendCache")
    public void deleteMentor(Long mentorId) {
        mentorRepository.deleteById(mentorId);
    }

    @Cacheable("mentorRecommendCache")
    public List<Mentor> getMentorRecommend(Long studyRoomId){

        // 스킬 리스트
        List<String> skills = Arrays.asList(
                "Java", "Python", "Node.js", "C#", "Ruby", "Go", "PHP", "C++", "Scala", "Kotlin",
                "JavaScript", "React", "Angular", "Vue.js", "HTML/CSS", "TypeScript", "Sass", "Webpack", "Redux", "JQuery",
                "Django", "Flask", "Spring Boot", "ASP.NET", "Express.js", "Laravel", "Rust", "Elixir", "GraphQL", "Apache Kafka",
                "Next.js", "Gatsby", "Ember.js", "Bootstrap", "Tailwind CSS", "Figma", "MySQL", "MongoDB", "MariaDB", "NoSQL"
        );

        // StudyRoomId 기반 StudyRoom의 스킬 스택 리스트 가져오기
       List<StudySkill> skillList =  studySkillRepository.findByStudyRoomId(studyRoomId);

       List<String> skillnameList = new ArrayList<>();

       for(StudySkill studySkill : skillList){
           String skillName = studySkill.getSkill().getSkillName();
           skillnameList.add(skillName);
       }

       // StudyRoom의 스킬 스택 리스트에 맞춰 스터디룸 스킬 벡터 채우기
        int[] studyRoomSkillVector = new int[skills.size()];

        for (String skillName : skillnameList) {
            int index = skills.indexOf(skillName);
            if (index != -1) {
                studyRoomSkillVector[index] = 1;
            }
        }

        // Mentor의 스킬 스택 벡터 채우기
        Iterable<Mentor> mentorIterable = mentorRepository.findAll();
        List<Mentor> mentors = new ArrayList<>();
        mentorIterable.forEach(mentors::add);

        List<int[]> mentorSkillVectors = new ArrayList<>();

        for (Mentor mentor : mentors) {
            List<MentorSkill> mentorSkills = mentor.getMentorSkills();
            int[] mentorSkillVector = new int[skills.size()];

            for (MentorSkill mentorSkill : mentorSkills) {
                String skillName = mentorSkill.getSkill().getSkillName();
                int index = skills.indexOf(skillName);
                if (index != -1) {
                    mentorSkillVector[index] = 1;
                }
            }

            mentorSkillVectors.add(mentorSkillVector);
        }


        List<Double> similarityScores = new ArrayList<>();

        for (int[] mentorSkillVector : mentorSkillVectors) {

            double calculatedWeightedSimilarity
                    = calculateWeightedSimilarity(
                            calculateJaccardSimilarity(mentorSkillVector,studyRoomSkillVector),
                            calculateCosineSimilarity(mentorSkillVector, studyRoomSkillVector),
                             0.7,0.3);

            similarityScores.add(calculatedWeightedSimilarity);
        }

        // Mentor의 경력 점수와 평점에 대한 가중치
        double careerWeight = 0.4;
        double ratingWeight = 0.1;
        double similarityWeight = 0.5;

        // 멘토의 종합 점수를 저장할 리스트
        List<Double> totalScores = new ArrayList<>();

        // 멘토의 경력과 평점을 고려한 종합 점수 계산
        for (Mentor mentor : mentors) {
            double careerYear = mentor.getCareerYear();
            double rating = mentor.getRating();

            // 종합 점수 계산
            double totalScore =
                    ( similarityWeight * similarityScores.get(mentors.indexOf(mentor)) ) +
                    ( scaleCareerYear(careerYear,mentorRepository.findMinCareerYear(),mentorRepository.findMaxCareerYear()) * careerWeight ) +
                    ( scaleRating(rating,mentorRepository.findMinRating(),mentorRepository.findMaxRating()) * ratingWeight );

            totalScores.add(totalScore);
        }

        // 상위 5명의 멘토 추천 리스트
        List<Mentor> recommendedMentors = new ArrayList<>();

        // 상위 5개의 멘토를 추천 리스트에 추가
        for (int i = 0; i < 5; i++) {
            // 최대 종합 점수를 가진 멘토의 인덱스 찾기
            int maxIndex = totalScores.indexOf(Collections.max(totalScores));

            // 해당 멘토를 추천 리스트에 추가하고, 종합 점수 리스트에서 제거
            recommendedMentors.add(mentors.get(maxIndex));
            totalScores.remove(maxIndex);
        }

        return recommendedMentors;

    } //getMentorRecommend()

    // 경력 스케일링
    public static double scaleCareerYear(double originalExperience, double minExperience, double maxExperience) {
        return (originalExperience - minExperience) / (maxExperience - minExperience);
    }

    // 평점 스케일링
    public static double scaleRating(double originalRating, double minRating, double maxRating) {
        return (originalRating - minRating) / (maxRating - minRating);
    }

    //코사인 유사도 구하기
    public double calculateCosineSimilarity(int[] vector1, int[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("두 벡터의 길이가 동일해야 합니다.");
        }

        // 벡터의 내적 계산
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += Math.pow(vector1[i], 2);
            norm2 += Math.pow(vector2[i], 2);
        }

        // 벡터의 크기 계산
        double magnitude1 = Math.sqrt(norm1);
        double magnitude2 = Math.sqrt(norm2);

        // 코사인 유사도 계산
        double cosineSimilarity = dotProduct / (magnitude1 * magnitude2);

        return cosineSimilarity;
    } //calculateCosineSimilarity()
    
    //자카드 유사도 구하기
    public double calculateJaccardSimilarity(int[] set1, int[] set2) {
        if (set1.length != set2.length) {
            throw new IllegalArgumentException("두 집합의 길이가 동일해야 합니다.");
        }

        int intersectionCount = 0;
        int unionCount = 0;

        for (int i = 0; i < set1.length; i++) {
            if (set1[i] == 1 && set2[i] == 1) {
                intersectionCount++;
            }
            if (set1[i] == 1 || set2[i] == 1) {
                unionCount++;
            }
        }

        double jaccardSimilarity = (double) intersectionCount / unionCount;

        return jaccardSimilarity;
    } //calculateJaccardSimilarity()
    
    // 유사도 정규화하기
    public double normalizeCosineSimilarity(double similarity) {
        if (similarity < -1 || similarity > 1) {
            throw new IllegalArgumentException("유사도 값은 -1과 1 사이여야 합니다.");
        }

        // 정규화된 유사도 값 계산
        double normalizedSimilarity = (similarity - (-1)) / (1 - (-1));

        return normalizedSimilarity;
    }

    
    //각 유사도를 가중치에 맞게 가중 평균 구하기
    public double calculateWeightedSimilarity(double jaccardSimilarity, double cosineSimilarity, double weight1, double weight2) {
        double weightedSum = 0;
        double weightSum = 0;

        // jaccardSimilarity는 정규화 할 필요 없음
        cosineSimilarity = normalizeCosineSimilarity(cosineSimilarity);

        if (jaccardSimilarity < 0 || jaccardSimilarity > 1 || cosineSimilarity < 0 || cosineSimilarity > 1) {
            throw new IllegalArgumentException("유사도 값은 0과 1 사이여야 합니다.");
        }

        if (weight1 < 0 || weight2 < 0) {
            throw new IllegalArgumentException("가중치 값은 0 이상이어야 합니다.");
        }

        weightedSum += jaccardSimilarity * weight1 + cosineSimilarity * weight2;
        weightSum += weight1 + weight2;

        if (weightSum == 0) {
            throw new IllegalArgumentException("가중치의 합은 0이 아니어야 합니다.");
        }

        double weightedSimilarity = weightedSum / weightSum;

        return weightedSimilarity;
    }//calculateWeightedSimilarity()



}
