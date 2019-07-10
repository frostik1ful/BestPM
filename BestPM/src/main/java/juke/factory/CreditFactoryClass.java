package juke.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import juke.entities.Credit;
import juke.entities.User;
import juke.repositories.UserRepository;
import juke.service.interfases.CreditService;
import juke.utils.interfaces.CreditFactory;
import utils.CreditType;

@Component
public class CreditFactoryClass implements CreditFactory{
	
	
	Random rand = new Random();
	private final String [] banks = {"Первый Национальный", "Второй народный", "УВРННСТБНЧЕ-Банк", "САМ-СОН Кредит Груп", "Банк Кинбурна", "МВН БАнк", "Кредит для всех",};
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private UserRepository userRepository;
		
	public CreditFactoryClass(){
		
	}
	
	public List<Credit> getListCredits() {
		List<Credit> creditsList = new ArrayList<>();
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		for (int i = 0; creditsList.size() < 10; i++) {
			if (i < 3) {
				
				Credit credit = creditService.createCreditForFactory(user, CreditType.SHORTTERM);
				credit.setBankName(banks[rand.nextInt(banks.length - 1)]);
				creditsList.add(credit);
				continue;
				
			} else if (i >= 3 && i < 6) {
				Credit credit = creditService.createCreditForFactory(user, CreditType.MIDDLETERM);
				credit.setBankName(banks[rand.nextInt(banks.length - 1)]);
				creditsList.add(credit);
				continue;
			} else if (i >= 6) {
				Credit credit = creditService.createCreditForFactory(user, CreditType.LONGTERM);
				credit.setBankName(banks[rand.nextInt(banks.length - 1)]);
				creditsList.add(credit);
				continue;
			}
			
			
		}
		return creditsList;
	}

}
