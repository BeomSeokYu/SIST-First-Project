package proj.main;

import java.util.ArrayList;
import java.util.InputMismatchException;

import proj.dao.BookDAO;
import proj.util.Constant;
import proj.util.Pub;
import proj.vo.BookVO;

public class BookMain {
	private BookDAO bDao;
	private ArrayList<BookVO> list;
	private int page;
	private int maxPage;
	
	public BookMain() {
		bDao = new BookDAO();
	}
	
//	public static void main(String[] args) {
//		Pub.id = "asdf";
//		//DB connector 실행
//		DBCon.getConnection();
//		//메인 메뉴 호출
//		if(Pub.id.equals(Constant.ADMIN_ID))
//			new BookMain().bookAdminMenu();
//		else
//			new BookMain().bookMemberMenu();
//	}
	
	
	
	/********************************************************************************
	 * 공용 메서드
	 ********************************************************************************/
	/**
	 * COM_005 도서 목록 (전체 목록, 검색 목록)
	 * USR_012 검색 목록
	 */
	public void bookList(int listID) {
		if (listID == Constant.LIST_ALL) {
			list = (ArrayList<BookVO>) bDao.selectAllBook();
		}
		while (true) {
			boolean loop = false;
			if(!list.isEmpty()) { // 리스트가 비어있지 않을 경우
				System.out.println(Constant.HD_BOOK_LIST);
				System.out.printf(Constant.FORMAT_BOOK_LIST_COL,"번호", "저자명", "출판사", "제목");
				System.out.println(Constant.EL_XL);
				maxPage = list.size()/10;
				int start = page * 10;
				int end = start + 10;
				if (end > list.size()) {
					end = list.size();
				}
				for(int i = start; i < end; i++) {
					System.out.printf(Constant.FORMAT_BOOK_LIST_DAT, 
							i+1, list.get(i).getAuthor(), list.get(i).getPublisher(), list.get(i).getBookName());
				}
				System.out.println("\t\t\t\t- "+(page+1)+"/"+(maxPage+1)+" page -");
				System.out.println(Constant.EL_XL);
				System.out.println("(번호)자세히 보기 (0)뒤로 ([)이전 페이지 (])다음 페이지");
				System.out.println(Constant.EL_L);
				System.out.print(Constant.SELECT);
				String input = Pub.sc.nextLine();
				try { 
					int bookidx = Integer.parseInt(input)-1;
					if(bookidx == -1) {
						if(Pub.id.equals(Constant.ADMIN_ID)) {
							bookAdminMenu();
						} else {
							bookMemberMenu();
						}
					} else if (bookidx > -1 && bookidx < list.size()){
						bookInfo(list.get(bookidx), listID);
					} else {
						System.out.println(">> 없는 번호 입니다.");
						loop = true;
					}
				} catch (NumberFormatException e) {
					pageControl(input);
					loop = true;
				}
			} else { // 리스트가 비었을 경우
				if(listID == Constant.LIST_ALL) {
					System.out.println(">> 저장된 목록이 없습니다");
					if(Pub.id.equals(Constant.ADMIN_ID)) {
						bookAdminMenu();
					} else {
						bookMemberMenu();
					}
				}
				else {
					emptySearch();
				}
			}
			if (!loop) break;
		}
	}
	private void pageControl(String input) {
		switch (input) {
		case "[":
			if (page > 0) page--;
			break;
		case "]":
			if (page < maxPage) page++;
			break;
		default:
			System.out.println(">> 잘못된 입력입니다.");
			break;
		}
	}
	
	/**
	 * COM_006 도서 상세보기
	 * @param bvo : BookVO
	 * @param ListID : int
	 */
	public void bookInfo(BookVO bvo, int ListID) {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_INFO);
			System.out.println("   도서번호 : \t" + bvo.getBookId());
			System.out.println("   도서명 : \t" + bvo.getBookName());
			System.out.println("   저자 : \t" + bvo.getAuthor());
			System.out.println("   출판사 : \t" + bvo.getPublisher());
			System.out.println("   등록일 : \t" + Pub.sdf.format(bvo.getRegDate()));
			System.out.println("   예약자수 : \t" + bvo.getReserNum() + " 명");
			System.out.println("   대여가능여부 : \t" + (bvo.getRentAvail().equals("N")?"불가능":"가능"));
			System.out.println(Constant.EL_M);
			if(Pub.id.equals(Constant.ADMIN_ID)) {
				System.out.println("   1.수정    2.삭제    3.뒤로");
			} else {
				System.out.println("   1.대여    2.예약    3.뒤로");
			}
			System.out.println(Constant.EL_M);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	if(Pub.id.equals(Constant.ADMIN_ID)) {
						modBook(bvo);
					} else {
						if("Y".equals(bvo.getRentAvail())) {
							new BookManagement().borrow(bvo.getBookId(), ListID);
						} else {
							System.out.println(">> 이미 대여중입니다.");
							loop = true;
						}
					}
					break;
			case 2:	if(Pub.id.equals(Constant.ADMIN_ID)) {
						delBook(bvo);
					} else {
						new BookManagement().reserve(bvo.getBookId(), ListID);
					}
					break;
			case 3:	bookList(ListID);
					break;
			default:System.out.println(">> 1~3 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if (!loop) break;
		}
	}
	
	
	
	
	
	/********************************************************************************
	 * 회원 메서드
	 ********************************************************************************/
	/**
	 * USR_010 도서 메뉴
	 */
	public void bookMemberMenu() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_MENU);
			System.out.println("  1.도서목록  2.도서검색  3.도서반납  4.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	page = 0;
					bookList(Constant.LIST_ALL);
					break;
			case 2:	searchMenu();
					break;
			case 3:	new BookManagement().borrowList(Pub.id, Constant.FLAG_MEMBER_BOOK);
					break;
			case 4:	new Main().memberMenu();
					break;
			default:System.out.println(">> 1~4 중에 선택해 주세요.");
					loop = true;
			}
			if(!loop) break;
		}
	}
	
	/**
	 * USR_011 도서 검색 메뉴
	 */
	public void searchMenu() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_SEARCH_MENU);
			System.out.println("  1.도서명   2.저자명   3.출판사   4.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	searchByName();
					break;
			case 2:	searchByAuthor();
					break;
			case 3:	searchByPubli();
					break;
			case 4:	bookMemberMenu();
					break;
			default:System.out.println(">> 1~4 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if(!loop) break;
		}
	}
	
	/**
	 * 검색 목록이 없을 경우 신청
	 */
	public void emptySearch() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_SEARCH_LIST);
			System.out.println("  검색 결과 없음");
			System.out.println(Constant.EL_S);
			System.out.println("  1.도서 신청     2.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = Pub.sc.nextInt();
			Pub.sc.nextLine();
			switch (select) {
			case 1:	new BookManagement().appBook();
					break;
			case 2:	searchMenu();
					break;
			default:System.out.println(">> 1~2 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if (!loop) break;
		}
	}
	
	/**
	 * USR_016 도서명검색 
	 */
	public void searchByName() {
		System.out.println(Constant.HD_BOOK_SEARCH_BOOK_NAME);
		System.out.print(">> 도서명 입력 : ");
		String sword = Pub.sc.nextLine();
		System.out.println(Constant.EL_S);
		list = (ArrayList<BookVO>) bDao.selectName(sword);
		page = 0;
		bookList(Constant.LIST_SEARCH);
	}
	/**
	 * USR_017 저자명검색
	 */
	public void searchByAuthor(){
		System.out.println(Constant.HD_BOOK_SEARCH_BOOK_NAME);
		System.out.print(">> 저자명 입력 : ");
		String sword = Pub.sc.nextLine();
		System.out.println(Constant.EL_S);
		list = (ArrayList<BookVO>) bDao.selectAuthor(sword);
		page = 0;
		bookList(Constant.LIST_SEARCH);
	}
	
	/**
	 * USR_018 출판사검색
	 */
	public void searchByPubli() {
		System.out.println(Constant.HD_BOOK_SEARCH_BOOK_NAME);
		System.out.print(">> 출판사 입력 : ");
		String sword = Pub.sc.nextLine();
		System.out.println(Constant.EL_S);
		list = (ArrayList<BookVO>) bDao.selectPublisher(sword);
		page = 0;
		bookList(Constant.LIST_SEARCH);
	}
	
	
	
	
	
	/********************************************************************************
	 * 관리자 메서드
	 ********************************************************************************/
	/**
	 * ADM_005 도서 관리
	 */
	public void bookAdminMenu() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_MENU);
			System.out.println("  1.도서목록     2.도서등록     3.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	page = 0;
					bookList(Constant.LIST_ALL);
					break;
			case 2:	regBookMenu();
					break;
			case 3:	new Main().adminMenu();
					break;
			default:System.out.println(">> 1~3 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if(!loop) break;
		}
	}
	public void regBookMenu() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_APP_BOOK_REGIST);
			System.out.println("  1.신청도서목록     2.신규도서등록     3.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	page = 0;
					new BookManagement().prpsBookList(Pub.id);
					break;
			case 2:	regNewBook();
					break;
			case 3:	bookAdminMenu();
					break;
			default:System.out.println(">> 1~3 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if(!loop) break;
		}
	}
	/**
	 * ADM_008 신규 도서 등록 
	 */
	public void regNewBook() {
		BookVO bvo = new BookVO();
		System.out.println(Constant.HD_BOOK_REGIST);
		System.out.print("  제목 : ");
		String title = Pub.sc.nextLine();
		System.out.print("  저자 : ");
		String auth = Pub.sc.nextLine();
		System.out.print("  출판사 : ");
		String pub = Pub.sc.nextLine();
		if (title.equals("") || auth.equals("") || pub.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 등록이 취소되었습니다.");
		} else {
			bvo.setBookName(title);
			bvo.setAuthor(auth);
			bvo.setPublisher(pub);
			if(bDao.insert(bvo)) {
				System.out.println(">> 등록되었습니다.");
			} else {
				System.out.println(">> 등록에 실패하였습니다.");
			}
		}
		bookAdminMenu();
	}
	/**
	 * ADM_007 도서 삭제
	 * @param bvo : BookVO
	 */
	public void delBook(BookVO bvo) {
		System.out.println(Constant.HD_BOOK_DEL);
		System.out.print(">> 정말로 삭제하시겠습니까? (Y/n) :");
		String select = Pub.sc.nextLine();
		if (select.equalsIgnoreCase("y") || select.equalsIgnoreCase("")) {
			if(bDao.delete(bvo.getBookId())) {
				System.out.println(">> 삭제되었습니다.");
				page = 0;
				bookList(Constant.LIST_ALL);
			}
		} else {
			System.out.println(">> 삭제를 취소하였습니다.");
			bookAdminMenu();
		}
	}
	/**
	 * ADM_006 도서 수정
	 * @param bvo : BookVO
	 */
	public void modBook(BookVO bvo) {
		System.out.println(Constant.HD_BOOK_MOD);
		System.out.print("  제목 : ");
		String title = Pub.sc.nextLine();
		System.out.print("  저자 : ");
		String auth = Pub.sc.nextLine();
		System.out.print("  출판사 : ");
		String pub = Pub.sc.nextLine();
		if (title.equals("") || auth.equals("") || pub.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 수정이 취소되었습니다.");
		} else {
			bvo.setBookName(title);
			bvo.setAuthor(auth);
			bvo.setPublisher(pub);
			if(bDao.update(bvo)) {
				System.out.println(">> 도서가 수정되었습니다.");
			} else {
				System.out.println(">> 도서 수정에 실패하였습니다.");
			}
		}
	}
}
