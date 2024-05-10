package com.java.phone;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PhoneBookMain {
    private static PhoneBookDAO phoneBookDAO = new PhoneBookDAOImpl();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)){
            while (true) {
            	printMenu();
                System.out.print("메뉴번호: ");
                int menu = sc.nextInt();
                sc.nextLine();

                switch (menu) {
                    case 1:
                        listPhoneBooks();
                        break;
                    case 2:
                        addPhoneBook(sc);
                        break;
                    case 3:
                        deletePhoneBook(sc);
                        break;
                    case 4:
                        searchPhoneBook(sc);
                        break;
                    case 5:
                        System.out.println("************************************");
                        System.out.println("*           감사합니다.            *");
                        System.out.println("************************************");
                        return;
                    default:
                        System.out.println("[다시 입력해 주세요]");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void printMenu() {
    	  System.out.println("********************************************");
          System.out.println("*          전화번호 관리 프로그램          *");
          System.out.println("********************************************");
          System.out.println("  1.리스트  2.등록  3.삭제  4.검색  5.종료");
          System.out.println();
    }

    private static void listPhoneBooks() throws SQLException {
    	System.out.println("---------------------------------------");
        List<PhoneBookVO> phoneBooks = phoneBookDAO.getList();
        for (PhoneBookVO pb : phoneBooks) {
            System.out.println(pb.getId() + ". " + pb.getName() + " " + maskNumber(pb.getHp()) + " " + maskNumber(pb.getTel()));
        }
       System.out.println();
    }

    private static void addPhoneBook(Scanner scanner) {
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("핸드폰 번호: ");
        String hp = scanner.nextLine();
        System.out.print("전화번호: ");
        String tel = scanner.nextLine();

        PhoneBookVO vo = new PhoneBookVO(name, hp, tel);
        boolean success = phoneBookDAO.insert(vo);

        System.out.println("정보 등록이 " + (success ? "성공했어요." : "실패했어요."));
        System.out.println();
    }

    private static void deletePhoneBook(Scanner scanner) {
        System.out.print("삭제할 ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 소비
        boolean success = phoneBookDAO.delete(id);
        System.out.println("삭제가 " + (success ? "성공했습니다." : "실패했습니다."));
        System.out.println();
    }

    private static void searchPhoneBook(Scanner scanner) {
        System.out.print("검색할 이름: ");
        String name = scanner.nextLine();
        PhoneBookVO pb = phoneBookDAO.searchPhoneBook(name);
        if (pb != null) {
            System.out.println(pb.getId() + ". " + pb.getName() + " " + maskNumber(pb.getHp()) + " " + maskNumber(pb.getTel()));
        } else {
            System.out.println("찾을 수 없습니다.");
            System.out.println();
        }
    }

    private static String maskNumber(String number) {
        String[] parts = number.split("-");
        if (parts.length < 3) {
            return number;
        }
        return parts[0] + "-****-**" + parts[2].substring(parts[2].length() - 2);
    }

}