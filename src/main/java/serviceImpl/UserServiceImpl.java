package serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.User;
import dao.DaoUser;
import service.UserService;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private DaoUser daoUser;

	@Override
	public List<User> filterUsers(String classId, String role) {
		// TODO Auto-generated method stub
		if(classId.isEmpty()) { // check người dung có chọn lớp học để tìm kiếm hay không
			classId = null;
		}
		if(role.isEmpty()) {
			role = null;
		}
		return daoUser.filterUsers(classId, role);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// san moi ruot duoi con moi

}
