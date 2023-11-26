package com.test.apiTest.helper;

import com.test.apiTest.repository.EmployeeICTRoleRepo;

public class ManageExceldata {

    private EmployeeICTRoleRepo employeeICTRoleRepo;

//    public static List convertExcelToEmployeeDetails(InputStream is){
//        List list = new ArrayList<>();
//
//        try {
//            XSSFWorkbook workbook=new XSSFWorkbook(is);
//            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//
//
//                if (workbook.getSheetName(i).equalsIgnoreCase("Employee ICT Role")) {
//                    XSSFSheet sheet = workbook.getSheet("Employee ICT Role");
//                    int rowNumber = 0;
//                    Iterator<Row> iterator = sheet.iterator();
//                    while (iterator.hasNext()) {
//                        Row row = iterator.next();
//                        if (rowNumber == 0) {
//                            rowNumber++;
//                            continue;
//                        }
//                        Iterator<Cell> cells = row.iterator();
//
//                        int cid = 0;
//
//                        EmployeeICTRole employee = new EmployeeICTRole();
//                        while (cells.hasNext()) {
//                            Cell cell = cells.next();
//                            switch (cid) {
//                                case 0:
//                                    employee.setEmployee_number((int) cell.getNumericCellValue());
//                                    break;
//                                case 1:
//                                    employee.setEmployee_name((String) cell.getStringCellValue());
//                                    break;
//                                case 2:
//                                    employee.setJob((String) cell.getStringCellValue());
//                                    break;
//                                case 3:
//                                    employee.setOrganization((String) cell.getStringCellValue());
//                                    break;
//                                case 4:
//                                    employee.setIct_role((String) cell.getStringCellValue());
//                                    break;
//
//                                default:
//                                    break;
//                            }
//                            cid++;
//
//                        }
//                        list.add(employee);
//
//                    }
//                }
//                else if (workbook.getSheetName(i).equalsIgnoreCase("Learning Courses")) {
//                    XSSFSheet sheet = workbook.getSheet("Learning Courses");
//                    int rowNumber = 0;
//                    Iterator<Row> iterator = sheet.iterator();
//                    while (iterator.hasNext()) {
//                        Row row = iterator.next();
//                        if (rowNumber == 0) {
//                            rowNumber++;
//                            continue;
//                        }
//                        Iterator<Cell> cells = row.iterator();
//
//                        int cid = 0;
//                        Course course = new Course();
//                        while (cells.hasNext()) {
//                            Cell cell = cells.next();
//                            switch (cid) {
//                                case 0:
//                                    course.setEmployee_number((int) cell.getNumericCellValue());
//                                    break;
//                                case 1:
//                                    course.setEmployee_name((String) cell.getStringCellValue());
//                                    break;
//                                case 2:
//                                    course.setCourse((String) cell.getStringCellValue());
//                                    break;
//                                case 3:
//                                    course.setLinked_competency((String) cell.getStringCellValue());
//                                    break;
//                                case 4:
//                                    course.setEnrollment_date((String) cell.getStringCellValue());
//                                    break;
//                                case 5:
//                                    course.setEnrollment_status((String) cell.getStringCellValue());
//                                    break;
//
//                                default:
//                                    break;
//                            }
//                            cid++;
//
//                        }
//                        list.add(course);
//
//                    }
//                }
//                else if (workbook.getSheetName(i).equalsIgnoreCase("Employee Competency Evaluation")) {
//                    XSSFSheet sheet = workbook.getSheet("Employee Competency Evaluation");
//                    int rowNumber = 0;
//                    Iterator<Row> iterator = sheet.iterator();
//                    while (iterator.hasNext()) {
//                        Row row = iterator.next();
//                        if (rowNumber == 0) {
//                            rowNumber++;
//                            continue;
//                        }
//                        Iterator<Cell> cells = row.iterator();
//
//                        int cid = 0;
//
//                        EmployeeEvaluation employeeEvaluation = new EmployeeEvaluation();
//                        while (cells.hasNext()) {
//                            Cell cell = cells.next();
//                            switch (cid) {
//                                case 0:
//                                    employeeEvaluation.setEmployee_number((int) cell.getNumericCellValue());
//                                    break;
//                                case 1:
//                                    employeeEvaluation.setEmployee_name((String) cell.getStringCellValue());
//                                    break;
//                                case 2:
//                                    employeeEvaluation.setJob((String) cell.getStringCellValue());
//                                    break;
//                                case 3:
//                                    employeeEvaluation.setOrganization((String) cell.getStringCellValue());
//                                    break;
//                                case 4:
//                                    employeeEvaluation.setIct_role((String) cell.getStringCellValue());
//                                    break;
//                                case 5:
//                                    employeeEvaluation.setIct_competency((String) cell.getStringCellValue());
//                                    break;
//                                case 6:
//                                    employeeEvaluation.setEvaluation_result((String) cell.getStringCellValue());
//                                    break;
//                                case 7:
//                                    employeeEvaluation.setEvaluation_date((Date) cell.getDateCellValue());
//                                    break;
//
//                                default:
//                                    break;
//                            }
//                            cid++;
//
//                        }
//                        list.add(employeeEvaluation);
//
//                    }
//                } else if (workbook.getSheetName(i).equalsIgnoreCase("Employee Role")) {
//                    XSSFSheet sheet = workbook.getSheet("Employee Role");
//                    int rowNumber = 0;
//                    Iterator<Row> iterator = sheet.iterator();
//                    while (iterator.hasNext()) {
//                        Row row = iterator.next();
//                        if (rowNumber == 0) {
//                            rowNumber++;
//                            continue;
//                        }
//                        Iterator<Cell> cells = row.iterator();
//
//                        int cid = 0;
//
//                        EmployeeRole employeeRole = new EmployeeRole();
//                        while (cells.hasNext()) {
//                            Cell cell = cells.next();
//                            switch (cid) {
//                                case 0:
//                                    employeeRole.setEmployee_number((int) cell.getNumericCellValue());
//                                    break;
//                                case 1:
//                                    employeeRole.setEntity((String) cell.getStringCellValue());
//                                    break;
//                                case 2:
//                                    employeeRole.setJob((String) cell.getStringCellValue());
//                                    break;
//                                case 3:
//                                    employeeRole.setCategory((String) cell.getStringCellValue());
//                                    break;
//                                case 4:
//                                    employeeRole.setSub_category((String) cell.getStringCellValue());
//                                    break;
//                                case 5:
//                                    employeeRole.setRole((String) cell.getStringCellValue());
//                                    break;
//                                case 6:
//                                    employeeRole.setEvaluation_result((String) cell.getStringCellValue());
//                                    break;
////                                case 7:
////                                    if (cell.getCellType() == CellType.BLANK) {
////                                        employeeRole.setEvaluation_date(null);
////                                    } else {
////                                        employeeRole.setEvaluation_date((Date) cell.getDateCellValue());
////                                    }
////                                    break;
//                                case 8: employeeRole.setTechnical_skills((String) cell.getStringCellValue());
//                                    break;
//                                case 9: employeeRole.setSoft_skills((String) cell.getStringCellValue());
//                                    break;
//
//                                default:
//                                    break;
//                            }
//                            cid++;
//
//                        }
//                        list.add(employeeRole);
//                        System.out.println((list));
//
//                    }
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
}
