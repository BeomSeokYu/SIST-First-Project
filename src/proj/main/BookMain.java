package proj.main;

import java.util.ArrayList;

import proj.dao.BookDAO;
import proj.dao.RentDAO;
import proj.dao.ReserDAO;
import proj.vo.BookVO;

public class BookMain {
	private BookDAO bDao;
	
	/****************************************************
	 * 공용 메서드
	 ****************************************************/
	// COM_005 전체 도서 목록
	public void bookList() {
		// bdao에서 모든 목록 불러와서 출력
		// 상세히 볼 도서번호 선택받아 bookInfo 호출
/*
		=================== 도서 목록 ===================
		도서번호    저자명       출판사         제목
		2         김영희       디비디비딥      데이터베이스 개론
		3         김철수       두빛          html+css+js
		32        김민수       자바자바잡      자바의 기초
		----------------------------------------------
		>> 상세히 볼 도서번호 선택 :
 */
	}
	// COM_006 도서 상세보기
	public void bookInfo(int bookid) {
/*		
 		================= 도서 상세 정보 =================
		도서번호 : 2
		제목 : 데이터베이스 개론
		저자명 : 김영희
		출판사 : 디비디비딥
		도서등록일 : 2022.02.12
		대출 : 가능
		예약 : 없음
		----------------------------------------------
		  1.대여      2.예약      3.뒤로
		----------------------------------------------
		>> 선택 :
 */
		// 대출 가능 상태라면 도서 대여 실행 가능 -> renDao에서 삽입/bDao에서 업데이트
		// 불가능 상태라면 도서 예약 실행 가능 -> resDao에서 삽입/bDao에서 업데이트
	}
	
	
	/****************************************************
	 * 회원 메서드
	 ****************************************************/
	// USR_010 도서 메뉴
	public void bookMenu() {
/*
		===================== 도서 =====================
		  1.도서목록     2.도서검색     3.도서반납    4.뒤로
		-----------------------------------------------
		>> 선택 : 
*/
	}
	// USR_011 도서 검색 메뉴
	public void searchMenu() {
/*
		=================== 도서 검색 ===================
		  1.도서명     2.저자명     3.출판사    4.뒤로
		-----------------------------------------------
		>> 선택 : 
 */
	}
	// USR_012 검색 목록
	public void searchList(ArrayList<BookVO> list) {
/*
 		================ {검색어} 검색 목록 ================
		도서번호    저자명       출판사         제목
		2         김영희       디비디비딥      데이터베이스 개론
		----------------------------------------------
		>> 상세히 볼 도서번호 선택 :
 */
		// bookInfo(int bookid)로 이동
		
/*
		================ {검색어} 검색 목록 ================
		검색 결과 없음
		----------------------------------------------
		  1.도서신청       2.뒤로
		----------------------------------------------
		>> 선택 :
 */
		// 검색 결과 없을 경우 도서 신청 BookManagement클래스의 appBook 호출
	}
	// USR_016 도서명검색 
	public void searchByName() {
/*
 		=================== 도서명 검색 ===================
		>> 검색 : 
		----------------------------------------------
 */
		// 검색어 입력 받아 bDao의 selectBookName()에 넘겨 쿼리 실행 후 searchList(ArrayList<BookVO> list)넘겨줌
	}
	// USR_017 저자명검색
	public void searchByAuthor(){
/*
		=================== 저자명 검색 ===================
		>> 검색 : 
		----------------------------------------------
 */
		// 검색어 입력 받아 bDao의 selectBookAuthor()에 넘겨 쿼리 실행 후 searchList(ArrayList<BookVO> list)넘겨줌
	}
	
	// USR_018 출판사검색
	public void searchByPubli() {
/*
		================== 출판사명 검색 ==================
		>> 검색 : 
		------------------------------------------------
 */
		// 검색어 입력 받아 bDao의 selectPublisher()에 넘겨 쿼리 실행 후 searchList(ArrayList<BookVO> list)넘겨줌
	}
	
	/****************************************************
	 * 관리자 메서드
	 ****************************************************/
	// ADM_005 도서 관리
	public void bookManageMenu() {
/*
		================ 도서 관리 ================
		  1.도서목록     2.도서등록     3.뒤로
		-------------------------------------------
		>> 선택 :
 */
	}
	// ADM_008 신규 도서 등록 
	public void regBook() {
/*
		=============== 신규 도서 등록 ===============
		제목 : 
		저자명 : 
		출판사 : 
		---------------------------------------
		>> 신규 도서가 등록되었습니다.
 */
	}
	// ADM_007 도서 삭제
	public void delBook(int bookId) {
/*
		=================== 도서 삭제 ===================
		>> 정말로 삭제하시겠습니까? (Y/n) :
		>> 삭제되었습니다. / >> 삭제를 취소하였습니다.
 */
	}
	// ADM_006 도서 수정
	public void modBook(int bookId) {
/*
		=================== 도서 수정 ===================
		제목 : 데이터베이스 개론
		저자명 : 김영희
		출판사 : 디비디비딥
		--------------------------------------------
		>> 도서가 수정되었습니다.
 */
	}
}
