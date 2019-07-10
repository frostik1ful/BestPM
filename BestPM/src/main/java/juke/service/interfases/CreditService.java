package juke.service.interfases;

import java.util.List;

import juke.entities.Credit;
import juke.entities.User;
import utils.CreditType;


public interface CreditService {
	boolean createCredit(Credit credit);

	boolean deleteCreditById(int id);

	Credit getCreditById(int id);
	
	Credit createCreditForFactory(User user, CreditType type);

	List<Credit> getAllCredit();

	boolean saveInCredit(Credit credit);
	
	List<String> timeForCredit();
	
	void returnCredit(int id);
	
	void claimCredit(Credit credit);
	
	
}
