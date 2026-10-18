package BT_LTGK;

import java.util.ArrayList;
import java.util.Scanner;

public class Book {
	// Thuộc tính
	private int ID;
	private String title;
	private String author;
	private String genre; 
	private double price;
	private String isbn;
	private static int count = 0; 

	// Danh sách lưu tất cả các cuốn sách 
	private static ArrayList<Book> list = new ArrayList<>();
	private static Scanner sc = new Scanner(System.in);

	// Hàm dựng
	public Book(String title, String author, String genre, double price, String isbn) {
		this.ID = ++count; 
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.price = price;
		this.isbn = isbn;
	}

	public Book() {

	}

	// Phương thức get/set
	public int getID() {
		return ID;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public double getPrice() {
		return price;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	// Hiển thị thông tin sách
	public void introduce() {
		System.out.println("ID: " + ID + " | Title: " + title + " | Author: " + author
				+ " | Genre: " + genre + " | Price: " + price + " | ISBN: " + isbn);
	}

	// CÁC HÀM NHẬP DỮ LIỆU 

	// Nhập chuỗi
	public static String inputString(String message) {
		while (true) {
			System.out.print(message);
			String s = sc.nextLine().trim();
			if (!s.isEmpty()) {
				return s;
			}
			System.out.println("Không được để trống, nhập lại!");
		}
	}

	// Nhập thể loại
	public static String inputGenre() {
		while (true) {
			System.out.println("Chọn thể loại: 1. Network  2. AI  3. Programming");
			System.out.print("Nhập lựa chọn (1-3): ");
			String s = sc.nextLine().trim();
			if (s.equals("1")) return "Network";
			if (s.equals("2")) return "AI";
			if (s.equals("3")) return "Programming";
			System.out.println("Chỉ được chọn 1, 2 hoặc 3!");
		}
	}

	// Nhập giá phải là số > 0
	public static double inputPrice() {
		while (true) {
			System.out.print("Nhập giá: ");
			try {
				double p = Double.parseDouble(sc.nextLine().trim());
				if (p > 0) {
					return p;
				}
				System.out.println("Giá phải lớn hơn 0!");
			} catch (NumberFormatException e) {
				System.out.println("Giá phải là số, nhập lại!");
			}
		}
	}

	// Kiểm tra chuỗi chỉ gồm chữ số
	public static boolean isAllDigits(String s) {
		if (s.isEmpty()) return false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) < '0' || s.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}

	// Kiểm tra ISBN có đúng 1 trong 5 định dạng trong đề không?
	public static boolean isValidIsbn(String isbn) {
		// Độ dài từng phần của 5 định dạng hợp lệ
		int[][] formats = { { 5, 2, 2, 1 }, { 4, 1, 4, 1 }, { 3, 3, 3, 1 }, { 2, 6, 1, 1 }, { 2, 3, 4, 1 } };

		String[] parts = isbn.split("-"); // tách theo dấu gạch ngang
		if (parts.length != 4) {
			return false;
		}
		for (int i = 0; i < 4; i++) {
			if (!isAllDigits(parts[i])) {
				return false;
			}
		}
		for (int f = 0; f < formats.length; f++) {
			boolean match = true;
			for (int i = 0; i < 4; i++) {
				if (parts[i].length() != formats[f][i]) {
					match = false;
					break;
				}
			}
			if (match) {
				return true;
			}
		}
		return false;
	}

	// Nhập ISBN
	public static String inputIsbn() {
		while (true) {
			System.out.print("Nhập ISBN (vd: 12345-67-89-0): ");
			String s = sc.nextLine().trim();
			if (isValidIsbn(s)) {
				return s;
			}
			System.out.println("ISBN sai định dạng! Các định dạng hợp lệ:");
			System.out.println("xxxxx-xx-xx-x | xxxx-x-xxxx-x | xxx-xxx-xxx-x | xx-xxxxxx-x-x | xx-xxx-xxxx-x");
		}
	}

	// CÁC CHỨC NĂNG CHÍNH

	// 1. Thêm sách 
	public static void addBooks() {
		int n = 0; 
		while (true) {
			System.out.println("\n--- Thêm sách thứ " + (n + 1) + " ---");
			String title = inputString("Nhập tiêu đề: ");
			String author = inputString("Nhập tác giả: ");
			String genre = inputGenre();
			double price = inputPrice();
			String isbn = inputIsbn();

			Book b = new Book(title, author, genre, price, isbn);
			list.add(b);
			System.out.println("Đã thêm sách có ID = " + b.getID());
			n++;

			if (n >= 3) {
				System.out.print("Bạn có muốn tiếp tục không (Y/N)? ");
				String answer = sc.nextLine().trim();
				if (!answer.equalsIgnoreCase("Y")) {
					break; 
				}
			}
		}
	}

	// 2. Tìm sách theo tiêu đề 
	public static void findBooks() {
		String keyword = inputString("Nhập tiêu đề sách: ");
		boolean found = false;
		for (int i = 0; i < list.size(); i++) {
			// Đưa về chữ thường để không phân biệt hoa/thường
			if (list.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) {
				list.get(i).introduce();
				found = true;
			}
		}
		if (!found) {
			System.out.println("Không tìm thấy sách nào!");
		}
	}

	// Tìm sách theo ID
	public static Book findById(int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID() == id) {
				return list.get(i);
			}
		}
		return null;
	}

	// 3. Sửa hoặc xóa sách
	public static void updateOrDelete() {
		System.out.print("Nhập ID sách cần sửa/xóa: ");
		int id;
		try {
			id = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("ID phải là số nguyên!");
			return;
		}

		Book b = findById(id);
		if (b == null) {
			System.out.println("Không tìm thấy sách có ID = " + id);
			return;
		}
		b.introduce();

		System.out.print("Bạn muốn cập nhật (U) hay xóa (D) sách? ");
		String choice = sc.nextLine().trim();

		if (choice.equalsIgnoreCase("U")) {
			// Nhấn Enter để giữ nguyên giá trị cũ
			System.out.println("(Nhấn Enter để giữ nguyên giá trị cũ)");

			System.out.print("Title mới [" + b.getTitle() + "]: ");
			String title = sc.nextLine().trim();
			if (!title.isEmpty()) {
				b.setTitle(title);
			}

			System.out.print("Author mới [" + b.getAuthor() + "]: ");
			String author = sc.nextLine().trim();
			if (!author.isEmpty()) {
				b.setAuthor(author);
			}

			System.out.print("Đổi Genre (hiện tại: " + b.getGenre() + ")? (Y/N): ");
			if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
				b.setGenre(inputGenre());
			}

			System.out.print("Đổi Price (hiện tại: " + b.getPrice() + ")? (Y/N): ");
			if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
				b.setPrice(inputPrice());
			}

			System.out.print("Đổi ISBN (hiện tại: " + b.getIsbn() + ")? (Y/N): ");
			if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
				b.setIsbn(inputIsbn());
			}
			System.out.println("Cập nhật thành công!");
		} else if (choice.equalsIgnoreCase("D")) {
			list.remove(b);
			System.out.println("Đã xóa sách có ID = " + id);
		} else {
			System.out.println("Chỉ được chọn U hoặc D!");
		}
	}

	// 4. Hiển thị sách sắp xếp theo giá tăng dần
	public static void report() {
		if (list.isEmpty()) {
			System.out.println("Danh sách trống!");
			return;
		}

		// Tạo bản sao để sắp xếp, không làm đổi thứ tự danh sách gốc
		ArrayList<Book> sorted = new ArrayList<>(list);

		// Sắp xếp theo giá tăng dần
		for (int i = 0; i < sorted.size() - 1; i++) {
			for (int j = 0; j < sorted.size() - 1 - i; j++) {
				if (sorted.get(j).getPrice() > sorted.get(j + 1).getPrice()) {
					Book temp = sorted.get(j);
					sorted.set(j, sorted.get(j + 1));
					sorted.set(j + 1, temp);
				}
			}
		}

		System.out.println("\nDANH SÁCH SÁCH SẮP XẾP THEO GIÁ TĂNG DẦN");
		for (int i = 0; i < sorted.size(); i++) {
			sorted.get(i).introduce();
		}
	}

	// HÀM MAIN 
	public static void main(String[] args) {
		while (true) {
			System.out.println("\nCHƯƠNG TRÌNH QUẢN LÝ SÁCH");
			System.out.println("1. Thêm sách");
			System.out.println("2. Tìm kiếm sách");
			System.out.println("3. Cập nhật / Xóa sách");
			System.out.println("4. Báo cáo (Sắp xếp theo giá)");
			System.out.println("5. Thoát");
			System.out.print("(Vui lòng chọn từ 1 đến 5 để sử dụng chương trình): ");

			String choice = sc.nextLine().trim();

			if (choice.equals("1")) {
				addBooks();
			} else if (choice.equals("2")) {
				findBooks();
			} else if (choice.equals("3")) {
				updateOrDelete();
			} else if (choice.equals("4")) {
				report();
			} else if (choice.equals("5")) {
				System.out.println("Tạm biệt!");
				break;
			} else {
				System.out.println("Vui lòng chọn từ 1 đến 5!");
			}
		}
	}
}
