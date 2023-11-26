package com.test.apiTest.controller;

import com.test.apiTest.dto.CompanyAverageDTO;
import com.test.apiTest.dto.MonthlyAverageDTO;
import com.test.apiTest.model.Skill;
import com.test.apiTest.repository.SkillRepository;
import com.test.apiTest.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/getAllSkills")
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/companyAverageScore")
    public List<CompanyAverageDTO> getCompanyAverages(@RequestParam LocalDate startDate,
                                                      @RequestParam LocalDate endDate) {
        List<Object[]> results = skillRepository.findMonthlyAveragesByCompany(startDate, endDate);

        Map<String, List<MonthlyAverageDTO>> averagesMap = new HashMap<>();

        for (Object[] result : results) {
            String companyName = (String) result[0];
            String month = (String) result[1];
            BigDecimal averageScore = (BigDecimal) result[2];

            MonthlyAverageDTO monthlyAverage = new MonthlyAverageDTO(month, averageScore);

            if (!averagesMap.containsKey(companyName)) {
                averagesMap.put(companyName, new ArrayList<>());
            }
            averagesMap.get(companyName).add(monthlyAverage);
        }

        List<CompanyAverageDTO> companyAverages = new ArrayList<>();

        for (Map.Entry<String, List<MonthlyAverageDTO>> entry : averagesMap.entrySet()) {
            String companyName = entry.getKey();
            List<MonthlyAverageDTO> monthlyAverages = entry.getValue();
            companyAverages.add(new CompanyAverageDTO(companyName, monthlyAverages));
        }

        return companyAverages;
    }


    @GetMapping("/skill-ranks")
    public List<Map<String, Object>> getScores(@RequestParam List<String> entityNames) {
        List<Map<String, Object>> result = new ArrayList<>();
        //    List<Object[]> scores = skillRepository.getImprovementSkillRanksByEntity(entityNames);
//        for (Object[] score : scores) {
//            Map<String, Object> scoreMap = new HashMap<>();
//            scoreMap.put("entityName", score[0]);
//            scoreMap.put("avg_score_diff", score[1]);
//            scoreMap.put("rank", score[2]);
//            result.add(scoreMap);
//        }
//        return result;
        List<Object[]> scores = skillRepository.getImprovementSkillRanksByEntityOverYears(entityNames);
        for (Object[] score : scores) {
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("year", score[0]);
            scoreMap.put("score", score[1]);
            result.add(scoreMap);
        }
        return result;
    }

    @GetMapping("/averageScoreOfListOfEntities1")
    public List<CompanyAverageDTO> findMonthlyAveragesForArrayOfCompany(@RequestParam LocalDate startDate,
                                                                        @RequestParam LocalDate endDate, @RequestParam List<String> entityNames) {

        // List<Object[]> results = skillRepository.findMonthlyAveragesForArrayOfCompanies(startDate, endDate, entityNames);
        List<Object[]> results = skillRepository.findMonthlyAveragesForArrayOfEntities(startDate, endDate, entityNames);

        Map<String, List<MonthlyAverageDTO>> averagesMap = new HashMap<>();

        for (Object[] result : results) {
            String companyName = (String) result[0];
            String month = (String) result[1];
            //Double averageScore = (Double) result[2];
            BigDecimal averageScore = (BigDecimal) result[2];

            MonthlyAverageDTO monthlyAverage = new MonthlyAverageDTO(month, averageScore);

            if (!averagesMap.containsKey(companyName)) {
                averagesMap.put(companyName, new ArrayList<>());
            }
            averagesMap.get(companyName).add(monthlyAverage);
        }

        List<CompanyAverageDTO> companyAverages = new ArrayList<>();

        for (Map.Entry<String, List<MonthlyAverageDTO>> entry : averagesMap.entrySet()) {
            String companyName = entry.getKey();
            List<MonthlyAverageDTO> monthlyAverages = entry.getValue();
            monthlyAverages.sort(Comparator.comparing(MonthlyAverageDTO::getMonth)); // Sort monthlyAverages by month in ascending order

            companyAverages.add(new CompanyAverageDTO(companyName, monthlyAverages));
        }

        return companyAverages;
    }

    //test eanas

    // Execute the query and retrieve the result set
    @GetMapping("/averageScoreOfListOfEntities")
    public List<CompanyAverageDTO> findMonthlyAveragesForArrayOfCompany1(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam List<String> entityNames) {

        List<Object[]> results = skillRepository.findMonthlyAveragesForArrayOfEntities(startDate, endDate, entityNames);
        List<CompanyAverageDTO> companyAverages = skillService.generateMonthlyAverages(results, startDate, endDate);

        return companyAverages;
    }


    @GetMapping("/averageScoreOfEntity")
    public List<CompanyAverageDTO> getAveragesScoreForOneCompany(@RequestParam LocalDate startDate,
                                                                 @RequestParam LocalDate endDate, @RequestParam String entityName) {
        List<Object[]> results = skillRepository.findMonthlyAveragesForOneCompany(startDate, endDate, entityName);
        List<CompanyAverageDTO> companyAverages = skillService.generateMonthlyAveragesOfOneEntity(results, startDate, endDate);

//        Map<String, List<MonthlyAverageDTO>> averagesMap = new HashMap<>();
//
//        for (Object[] result : results) {
//            String companyName = (String) result[0];
//            String month = (String) result[1];
//            BigDecimal averageScore = (BigDecimal) result[2];
//
//            MonthlyAverageDTO monthlyAverage = new MonthlyAverageDTO(month, averageScore);
//
//            if (!averagesMap.containsKey(companyName)) {
//                averagesMap.put(companyName, new ArrayList<>());
//            }
//            averagesMap.get(companyName).add(monthlyAverage);
//        }
//
//        List<CompanyAverageDTO> companyAverages = new ArrayList<>();
//
//        for (Map.Entry<String, List<MonthlyAverageDTO>> entry : averagesMap.entrySet()) {
//            String companyName = entry.getKey();
//            List<MonthlyAverageDTO> monthlyAverages = entry.getValue();
//            companyAverages.add(new CompanyAverageDTO(companyName, monthlyAverages));
//        }

        return companyAverages;
    }


    @GetMapping("/latestScore")
    public List<CompanyAverageDTO> findLatestMonthlyAveragesForArrayOfCompanies(@RequestParam List<String> entityNames) {

        // List<Object[]> results = skillRepository.findMonthlyAveragesForArrayOfCompanies(startDate, endDate, entityNames);
        List<Object[]> results = skillRepository.findLatestMonthlyAveragesForArrayOfCompanies(entityNames);

        Map<String, List<MonthlyAverageDTO>> averagesMap = new HashMap<>();

        for (Object[] result : results) {
            String companyName = (String) result[0];
            String month = (String) result[1];
            BigDecimal averageScore = (BigDecimal) result[2];
            System.out.println(averageScore);

            MonthlyAverageDTO monthlyAverage = new MonthlyAverageDTO(month, averageScore);

            if (!averagesMap.containsKey(companyName)) {
                averagesMap.put(companyName, new ArrayList<>());
            }
            averagesMap.get(companyName).add(monthlyAverage);
        }

        List<CompanyAverageDTO> companyAverages = new ArrayList<>();

        for (Map.Entry<String, List<MonthlyAverageDTO>> entry : averagesMap.entrySet()) {
            String companyName = entry.getKey();
            List<MonthlyAverageDTO> monthlyAverages = entry.getValue();
            BigDecimal latestMonthlyScore = monthlyAverages.get(monthlyAverages.size() - 1).getAverageScore();//eanas
            companyAverages.add(new CompanyAverageDTO(companyName, monthlyAverages));
        }
        // Sort the companyAverages list in descending order of the latest monthly score
        Collections.sort(companyAverages, (o1, o2) -> {
            BigDecimal score1 = o1.getMonthlyAverages().get(o1.getMonthlyAverages().size() - 1).getAverageScore();
            BigDecimal score2 = o2.getMonthlyAverages().get(o2.getMonthlyAverages().size() - 1).getAverageScore();
            return score2.compareTo(score1);
        });
        return companyAverages;
    }


}
