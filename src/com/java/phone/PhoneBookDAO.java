package com.java.phone;

import java.sql.SQLException;
import java.util.List;

public interface PhoneBookDAO {
  public List<PhoneBookVO> getList() throws SQLException;               // 전체 목록
  public PhoneBookVO searchPhoneBook(String name);   // PK로 객체 찾기
  public boolean insert(PhoneBookVO phoneBookVO);  // 레코드 삽입
  public boolean delete(Integer id);              // 레코드 삭제

}
