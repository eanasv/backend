package com.test.apiTest.service;

import com.test.apiTest.model.*;
import com.test.apiTest.repository.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExcelService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private TrainingNeedRepository trainingNeedRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SoftSkillRepository softSkillRepository;

    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;

    public static String getFormattedDate() {
        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Create a LocalDate for January 01 of the current year
        LocalDate januaryFirst = LocalDate.of(currentYear, 1, 1);

        // Format the date as "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return januaryFirst.format(formatter);
    }

    public static LocalDate getFormattedLoclDate() {
        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Create a LocalDate for January 01 of the current year
        LocalDate januaryFirst = LocalDate.of(currentYear, 1, 1);

        // No need to format it as a String, just return the LocalDate object
        return januaryFirst;
    }

    public static int calculateLevenshteinDistance(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (str1.charAt(i - 1) != str2.charAt(j - 1)) ? 1 : 0;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }

        return dp[str1.length()][str2.length()];
    }

    public static String findClosestRoleMatch(String roleFromExcel, List<String> rolesFromDatabase) {
        int minDistance = Integer.MAX_VALUE;
        String closestMatch = null;

        String processedStr1 = roleFromExcel.replaceAll("\\s", "").toLowerCase();
        for (String databaseRole : rolesFromDatabase) {
            String processedStr2 = databaseRole.replaceAll("\\s", "").toLowerCase();
            int distance = calculateLevenshteinDistance(processedStr1, processedStr2);
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = databaseRole;
            }
        }

        int threshold = 5; // Adjust the threshold based on your requirement
        if (minDistance <= threshold) {
            return closestMatch;
        } else {
            // No close match found, handle this case as per your requirement
            return null;
        }
    }

    public String StringShaper(String value) {

        String str = value;
        str = str.replaceAll("[\\s.]+$", "");
        return str;
    }

    public Boolean saveExcelDataCorrect(MultipartFile file, String entityName, List<String> errorMessages) throws IOException {
        Boolean repoNullFlag = false;// if the searched item nt found in db


        List<String> allRoleFromDb = roleRepository.findAllRoleName();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = null;

            // Loop through sheets to find the desired sheet by name "Employee Competency Evaluation"
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                // repoNullFlag = false;
                if (workbook.getSheetAt(i).getSheetName().equals("Employee Competency Evaluation")) {
                    sheet = workbook.getSheetAt(i);
                    break;
                }
            }

            // Handle the case where the desired sheet is not found
            if (sheet == null) {
                errorMessages.add("Employee Competency Evaluation' not found!");
            }

            // Read column headings from first row of sheet
            Row headerRow = sheet.getRow(0);
            Map<String, Integer> columnIndexes = new HashMap<>();
            String reservedEmployeeNumber = "";
            int rowNumber = 1;
            List<Skill> skills = new ArrayList<>();


            // error handling for Header Evaluation result
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                String columnName = cell.getStringCellValue().trim();
                // Check if the column name contains "evaluation result" (case-insensitive)
                if (columnName.toLowerCase().contains("evaluation result")) {
                    columnIndexes.put("Evaluation Result", i);
                } else {
                    columnIndexes.put(columnName, i);
                }
            }


            List<Skill> skillsToSave = new ArrayList<>();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);
                /*start of Eanas new code to match the entity name for the upload option*/
                if (entityName != null) {
                    String excelEntityName = getStringCellValue(sheet.getRow(1).getCell(columnIndexes.get("Organization")));
                    if (!entityName.equalsIgnoreCase(excelEntityName)) {
                        errorMessages.add("User's Entity name provided does not match with the Excel data.");
                        break;
                    }
                }
                //end
                Employee employee = new Employee();
                String employee_number = getStringCellValue(dataRow.getCell(columnIndexes.get("Employee Number")));


                if (repoNullFlag == true && employee_number == null) {
                    continue;
                }
                if (employee_number != null ) {
                    reservedEmployeeNumber = employee_number;
                    rowNumber = rowIndex;
                    // skills = new ArrayList<>();
                    //firstRow =sheet.getRow(rowNumber);
                }

                // Check if employee already exists
                // Employee existingEmployee = employeeRepository.findByEmployeeNumber(employee.getEmployeeNumber());
                Employee existingEmployee = employeeRepository.findByEmployeeNumber(reservedEmployeeNumber);


                List<String> skillLists;
                String closestRoleMatch;
                Integer roleId = 0;
                if (existingEmployee == null && reservedEmployeeNumber != "") {// for the first row in merged cells
                    //Integer employee_number= getIntegerCellValue(dataRow.getCell(columnIndexes.get("Employee Number")));
                    // String jobName = StringShaper(getStringCellValue(dataRow.getCell(columnIndexes.get("ICT Role"))));
                    String role = StringShaper(getStringCellValue(dataRow.getCell(columnIndexes.get("ICT Role"))));//allRoleFromDb
                     closestRoleMatch = findClosestRoleMatch(role, allRoleFromDb);

                    String employee_name = StringShaper(getStringCellValue(dataRow.getCell(columnIndexes.get("Employee Name"))));

                    String entity = StringShaper(getStringCellValue(dataRow.getCell(columnIndexes.get("Organization"))));
                    Integer subcategoryId = 0;
                    if (closestRoleMatch == null) {
                        // Handle incorrect or misspelled role
                        // Log an error message (you can use a logger for this purpose)
                        errorMessages.add("Error: Incorrect or misspelled role in row " + employee_name + ": " + role);
                        // Continue to the next iteration of the loop
                        repoNullFlag = true;
                        continue;
                    } else {
                        Role RoleDetails = roleRepository.findSubcategoryIdByRoleName1(closestRoleMatch);
                        roleId = Math.toIntExact(RoleDetails.getId());
                        subcategoryId = RoleDetails.getSubcategory().getId();
                        repoNullFlag = false;
                    }


                    String subcategoryName = subcategoryRepository.findTetById(Math.toIntExact(subcategoryId));
                    String categoryName = categoryRepository.findNameById(subcategoryRepository.findCatIdById(Long.valueOf(subcategoryId)));
                    Integer level = roleRepository.findLevelByRoleName(role);
                    employee.setEmployeeNumber(employee_number);
                    employee.setName(employee_name);
                    employee.setCategory(categoryName);
                    employee.setSubCategory(subcategoryName);
                    employee.setEntity(entity);
                    employee.setJob(role);
                    employee.setLevel(level);
                    // employeesToSave.add(employee);
                    employeeRepository.save(employee);
                } else if (existingEmployee != null) {
                    repoNullFlag = false;
                    Row firstRow = sheet.getRow(rowNumber);
                    existingEmployee.setEmployeeNumber(reservedEmployeeNumber);
                    String exmployeeName = getStringCellValue(firstRow.getCell(columnIndexes.get("Employee Name")));
                    existingEmployee.setName(StringShaper(exmployeeName));
                    //getStringCellValue(dataRow.getCell(columnIndexes.get("Organization")))
                    String employeeEntity = getStringCellValue(firstRow.getCell(columnIndexes.get("Organization")));
                    existingEmployee.setEntity(StringShaper(employeeEntity));

                    String role = getStringCellValue(firstRow.getCell(columnIndexes.get("ICT Role")));

                     closestRoleMatch = findClosestRoleMatch(role, allRoleFromDb);
                    // Role RoleDetails = roleRepository.findSubcategoryIdByRoleName1(role);

                    Integer subcategoryId = 0;
                    if (closestRoleMatch == null) {
                        // Handle incorrect or misspelled role
                        // Log an error message (you can use a logger for this purpose)
                        errorMessages.add("Error: Incorrect or misspelled role in row " + exmployeeName + ": " + role);
                        // Continue to the next iteration of the loop
                        repoNullFlag = true;
                        continue;
                    } else {
                        Role RoleDetails = roleRepository.findSubcategoryIdByRoleName1(closestRoleMatch);
                        roleId = Math.toIntExact(RoleDetails.getId());
                        subcategoryId = RoleDetails.getSubcategory().getId();
                        existingEmployee.setJob(closestRoleMatch);
                    }

                    String subcategoryName = subcategoryRepository.findTetById(Math.toIntExact(subcategoryId));
                    String categoryName = categoryRepository.findNameById(subcategoryRepository.findCatIdById(Long.valueOf(subcategoryId)));
                    Integer level = roleRepository.findLevelByRoleName(role);
                    existingEmployee.setCategory(StringShaper(categoryName));
                    existingEmployee.setSubCategory(StringShaper(subcategoryName));
                    existingEmployee.setLevel(level);


//                    existingEmployee.setLevel(getIntegerCellValue(firstRow.getCell(columnIndexes.get("Level"))));

                    employeeRepository.save(existingEmployee);
                }
                Employee UpdatedemployeeDetails = employeeRepository.findByEmployeeNumber(reservedEmployeeNumber);

                // Read skills from row
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String rowSkillName = getStringCellValue(dataRow.getCell(columnIndexes.get("ICT Competency")));
                if(rowSkillName==null) {
                    errorMessages.add("Skill name is null");
                    continue;
                }
                String skillName =StringShaper(rowSkillName);
                skillLists = getSkillsByRoleId(roleId);
                String closestSkillMatch = findClosestRoleMatch(skillName, skillLists);
                String evaResult = getStringCellValue(dataRow.getCell(columnIndexes.get("Evaluation Result")));
                String evaluationResult = "Not achieved";//StringShaper(getStringCellValue(dataRow.getCell(columnIndexes.get("Evaluation Result"))));
                if (evaResult != null) {
                    evaluationResult = StringShaper(evaResult);
                }


                // Date dateFromExcel = dataRow.getCell(columnIndexes.get("Evaluation Date")).getDateCellValue();

                Cell dateCell = dataRow.getCell(columnIndexes.get("Evaluation Date"));
                Date dateFromExcel = null;
                String dateStringFromExcel = getFormattedDate();
                LocalDate localDate = getFormattedLoclDate();
                if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                    // If the cell is not null, numeric, and formatted as a date, read the date value
                    dateFromExcel = dateCell.getDateCellValue();
                    dateStringFromExcel = dateFormat.format(dateFromExcel);localDate = dateFromExcel.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } else {
                    // Handle the case where the cell is null, not numeric, or not formatted as a date
                    // You can set a default date or throw an exception, depending on your use case
                    // For example, setting a default date:
                    // dateFromExcel = new Date(); // set a default date value
                    dateStringFromExcel = getFormattedDate();
                    localDate = getFormattedLoclDate();
                }
                String skillToSave = closestSkillMatch;
                if (closestSkillMatch == null) {
                    skillToSave = skillName;
                }

                Skill existingSkill = skillRepository.findByNameAndDateAndEmployeeNumber(skillToSave, dateStringFromExcel, reservedEmployeeNumber);

                Skill skill = new Skill();
                //update existing skill set
                if (existingSkill != null && existingSkill.getDate().equals(localDate)) {
                    existingSkill.setEmployee(UpdatedemployeeDetails);
                    existingSkill.setName(skillToSave);
                    existingSkill.setDate(localDate);
                    existingSkill.setEvaluationResult(evaluationResult);
                    skillsToSave.add(existingSkill);
                    skillRepository.save(existingSkill);
                    // response = true;
                } else {
                    //existingSkill.setEvaluationResult(getStringCellValue(dataRow.getCell(columnIndexes.get("Evaluation Result"))));
                    //skillsToSave.add(existingSkill);
                    //add new skill set
                    skill.setEmployee(UpdatedemployeeDetails);
                    skill.setName(skillToSave);
                    skill.setDate(localDate);
                    skill.setEvaluationResult(evaluationResult);
                    //skillsToSave.add(existingSkill);
                    skillRepository.save(skill);
                    // response = true;
                }
            }

//            employeeRepository.saveAll(employeesToSave);
//            skillRepository.saveAll(skillsToSave);

            // response = true;
        } catch (IOException e) {
            //throw new RuntimeException(e);
            errorMessages.add("Error processing row " + e.getMessage());
        }
        return errorMessages.isEmpty();
    }

    // Method to check if a cell is part of a merged region
    private boolean isCellMerged(Sheet sheet, int rowIndex, int columnIndex) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
            if (mergedRegion.isInRange(rowIndex, columnIndex)) {
                return true;
            }
        }
        return false;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim().toLowerCase();
            case NUMERIC:
                return Double.toString(cell.getNumericCellValue()).trim();
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue()).trim();
            default:
                return null;
        }
    }

    private Integer getIntegerCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (Objects.requireNonNull(cell.getCellType()) == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return null;
    }

    private LocalDate getDateCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } else {
            return null;
        }
    }

    public Boolean saveTrainingNeedExcelData(MultipartFile file, String entityName, List<String> errorMessages) throws IOException {
        Boolean response;
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = null;

            // Loop through sheets to find the desired sheet by name "Employee Competency Evaluation"
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (workbook.getSheetAt(i).getSheetName().equals("Learning Courses")) {
                    sheet = workbook.getSheetAt(i);
                    break;
                }
            }
            //Sheet sheet = workbook.getSheetAt(0);

            // Read column headings from first row of sheet
            Row headerRow = sheet.getRow(0);

            String reservedEmployeeNumber = "";
            int rowNumber = 1;
            String reservedLinkedCompetency = "";

            Map<String, Integer> columnIndexes = new HashMap<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                String columnName = cell.getStringCellValue().trim();
                columnIndexes.put(columnName, i);
            }

            Integer firstEmployee_numer;
            if (entityName != null) {
                 firstEmployee_numer = getIntegerCellValue(sheet.getRow(1).getCell(columnIndexes.get("Employee Number")));
                 String organizationName = employeeRepository.getEntityNameByEmployeeNumber(firstEmployee_numer);
                if (!entityName.equalsIgnoreCase(organizationName)) {
                    errorMessages.add("User's Entity name provided does not match with the Excel data.");
                    return  false;
                }
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);

                // Read employee details from row


                //eanas start for merged cells

                String employeeNumber = getStringCellValue(dataRow.getCell(columnIndexes.get("Employee Number")));
                String course = getStringCellValue(dataRow.getCell(columnIndexes.get("Course")));
                String linkedCompetency = getStringCellValue(dataRow.getCell(columnIndexes.get("Linked Competency")));
                String enrollmentStatus = getStringCellValue(dataRow.getCell(columnIndexes.get("Enrollment Status")));
                String enrollmentDate = getStringCellValue(dataRow.getCell(columnIndexes.get("Enrollment Date")));

                if (employeeNumber != null || employeeNumber !="") {
                    reservedEmployeeNumber = employeeNumber;
                    rowNumber = rowIndex;
                    //firstRow =sheet.getRow(rowNumber);
                }

                if (linkedCompetency != null) {
                    reservedLinkedCompetency = linkedCompetency;
                    rowNumber = rowIndex;
                    //firstRow =sheet.getRow(rowNumber);
                }
                if (linkedCompetency == null) {
                   continue;
                }


                // Check if employee already exists
                Employee existingEmployee = employeeRepository.findByEmployeeNumber(reservedEmployeeNumber);
                TrainingNeed existingTrainingNeedOfEmployee = trainingNeedRepository.findByEmployeeNumberAndEnrollmentStatus(reservedEmployeeNumber, reservedLinkedCompetency);


                // end of merged cells
                if (existingEmployee == null && employeeNumber != null) {
                    //employeesToSave.add(employee);
                } else if (existingTrainingNeedOfEmployee == null && (reservedEmployeeNumber != "" || reservedEmployeeNumber !=null)) {//new training for employee
                    TrainingNeed trainingNeed = new TrainingNeed();
                    trainingNeed.setEmployee(existingEmployee);
                    trainingNeed.setLinkedCompetency(reservedLinkedCompetency);

                    trainingNeedRepository.save(trainingNeed);
                    Course newCourse = new Course();

                    newCourse.setCourse(course);
                    newCourse.setEnrollmentDate(enrollmentDate);
                    newCourse.setEnrollmentStatus(enrollmentStatus);
                    newCourse.setTrainingNeed(trainingNeed);

                    courseRepository.save(newCourse);

                } else if (employeeNumber == null || existingTrainingNeedOfEmployee != null) { // if training exist
                    Course existingCourse = courseRepository.findByCourse(course, existingTrainingNeedOfEmployee.getId());

                    if (existingCourse == null) {
                        Course newCourse = new Course();

                        newCourse.setCourse(course);
                        newCourse.setEnrollmentDate(enrollmentDate);
                        newCourse.setEnrollmentStatus(enrollmentStatus);
                        newCourse.setTrainingNeed(existingTrainingNeedOfEmployee);
                        courseRepository.save(newCourse);
                    } else {
                        existingCourse.setCourse(course);
                        existingCourse.setEnrollmentDate(enrollmentDate);
                        existingCourse.setEnrollmentStatus(enrollmentStatus);
                        existingCourse.setTrainingNeed(existingTrainingNeedOfEmployee);
                        courseRepository.save(existingCourse);
                    }
                }// eanas- if code ending

            }

            //response = true;
        } catch (IOException e) {
            //throw new RuntimeException(e);
            errorMessages.add("Error processing row " + e.getMessage());
        }
        return errorMessages.isEmpty();
    }

    public List<String> getSkillsByRoleId(Integer roleId) {
        List<TechnicalSkill> technicalSkills = technicalSkillRepository.findByRoleId(roleId);
        List<SoftSkill> softSkills = softSkillRepository.findByRoleId(roleId);

        List<String> combinedSkills = new ArrayList<>();

        // Add technical skills to combinedSkills list
        for (TechnicalSkill technicalSkill : technicalSkills) {
            combinedSkills.add(technicalSkill.getSkillName());
        }

        // Add soft skills to combinedSkills list
        for (SoftSkill softSkill : softSkills) {
            combinedSkills.add(softSkill.getSkillName());
        }
        return combinedSkills;
    }


// for multi course for single linked competancy




}
