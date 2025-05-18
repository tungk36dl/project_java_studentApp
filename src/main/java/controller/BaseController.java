package controller;

import org.springframework.beans.factory.annotation.Autowired;

import dao.DaoClasses;
import dao.DaoCohort;
import dao.DaoMajor;
import dao.DaoScore;
import dao.DaoScoreComponent;
import dao.DaoSubject;
import dao.DaoSubjectDetail;
import dao.DaoUser;


public abstract class BaseController {
	 	@Autowired
	     DaoClasses daoClasses;

	    @Autowired
	     DaoMajor daoMajor;
	    
	    @Autowired
	     DaoCohort daoCohort;
	    
		@Autowired
		 DaoSubject daoSubject;
		
		@Autowired
		 DaoSubjectDetail daoSubjectDetail;
	    
	    @Autowired
	     DaoUser daoUser;
	    
	    @Autowired
	    DaoScore daoScore;
	    
	    @Autowired
	    DaoScoreComponent daoScoreComponent;  
	    

}
