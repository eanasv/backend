package com.test.apiTest.service;

import com.test.apiTest.dto.CompanyAverageDTO;
import com.test.apiTest.dto.MonthlyAverageDTO;
import com.test.apiTest.model.Employee;
import com.test.apiTest.model.Skill;
import com.test.apiTest.repository.EmployeeRepository;
import com.test.apiTest.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public void addSkillToEmployee(String employeeId, Skill skill) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id : " + employeeId));

        skill.setEmployee(employee);
        skillRepository.save(skill);
    }

    public List<Skill> getAllSkills() {

        List<Skill> skills = skillRepository.findAll();
        return skills;

    }

//    public List<Map<String, Object>> getCompanySkillAverages(LocalDate startDate, LocalDate endDate) {
//        List<Object[]> results = skillRepository.getCompanySkillAverages(startDate, endDate);
//        Map<String, Map<String, Double>> averagesMap = new LinkedHashMap<>();
//        for (Object[] result : results) {
//            String companyName = (String) result[0];
//            String month = (String) result[1];
//            Double averageScore = (Double) result[2];
//            if (!averagesMap.containsKey(companyName)) {
//                averagesMap.put(companyName, new LinkedHashMap<>());
//            }
//            averagesMap.get(companyName).put(month, averageScore);
//        }
//        List<Map<String, Object>> responseList = new ArrayList<>();
//        for (Map.Entry<String, Map<String, Double>> entry : averagesMap.entrySet()) {
//            Map<String, Object> responseMap = new LinkedHashMap<>();
//            responseMap.put("companyName", entry.getKey());
//            responseMap.put("averages", entry.getValue());
//            responseList.add(responseMap);
//        }
//        return responseList;
//    }


    public List<CompanyAverageDTO> generateMonthlyAverages(List<Object[]> results, LocalDate startDate, LocalDate endDate) {
        Map<String, Map<String, BigDecimal>> scoreMap = new HashMap<>();

        for (Object[] result : results) {
            String companyName = (String) result[0];
            String month = (String) result[1];
            BigDecimal averageScore = (BigDecimal) result[2];

            if (!scoreMap.containsKey(companyName)) {
                scoreMap.put(companyName, new HashMap<>());
            }
            scoreMap.get(companyName).put(month, averageScore);
        }

        List<CompanyAverageDTO> companyAverages = new ArrayList<>();

        for (Map.Entry<String, Map<String, BigDecimal>> entry : scoreMap.entrySet()) {
            String companyName = entry.getKey();
            Map<String, BigDecimal> scores = entry.getValue();
            List<MonthlyAverageDTO> monthlyAverages = generateMonthlyAveragesForCompany(scores, startDate, endDate);

            companyAverages.add(new CompanyAverageDTO(companyName, monthlyAverages));
        }

        return companyAverages;
    }

    public List<MonthlyAverageDTO> generateMonthlyAveragesForCompany(Map<String, BigDecimal> scores, LocalDate startDate, LocalDate endDate) {
        List<MonthlyAverageDTO> monthlyAverages = new ArrayList<>();

        LocalDate currentMonth = startDate;
        BigDecimal previousScore = BigDecimal.ZERO;

        while (currentMonth.isBefore(endDate) || currentMonth.isEqual(endDate)) {
            String month = currentMonth.format(DateTimeFormatter.ofPattern("MMM yyyy"));
            BigDecimal averageScore = scores.getOrDefault(month, previousScore);

            monthlyAverages.add(new MonthlyAverageDTO(month, averageScore));

            previousScore = averageScore;
            currentMonth = currentMonth.plusMonths(1);
        }

        return monthlyAverages;
    }

    public List<CompanyAverageDTO> generateMonthlyAveragesOfOneEntity(List<Object[]> results, LocalDate startDate, LocalDate endDate) {
        Map<String, Map<String, BigDecimal>> scoreMap = new HashMap<>();

        for (Object[] result : results) {
            String companyName = (String) result[0];
            String month = (String) result[1];
            BigDecimal averageScore = (BigDecimal) result[2];

            if (!scoreMap.containsKey(companyName)) {
                scoreMap.put(companyName, new HashMap<>());
            }
            scoreMap.get(companyName).put(month, averageScore);
        }

        List<CompanyAverageDTO> companyAverages = new ArrayList<>();

        for (Map.Entry<String, Map<String, BigDecimal>> entry : scoreMap.entrySet()) {
            String companyName = entry.getKey();
            Map<String, BigDecimal> scores = entry.getValue();
            List<MonthlyAverageDTO> monthlyAverages = generateMonthlyAveragesForCompany(scores, startDate, endDate);

            companyAverages.add(new CompanyAverageDTO(companyName, monthlyAverages));
        }

        return companyAverages;
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
    }


}
