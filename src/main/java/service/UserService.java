package service;

import java.util.List;

import org.springframework.stereotype.Service;

import beans.User;

@Service
public interface UserService {
	
	List<User> filterUsers(String classId, String role); // tìm kiếm người dùng
	
	// san moi
	
	// ngu/
	// an
	
	
	// lưu
	User save(User user);

}
