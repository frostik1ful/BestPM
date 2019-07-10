package juke.service.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import juke.entities.Credit;
import juke.entities.User;
import juke.repositories.CreditRepository;
import juke.repositories.UserRepository;
import juke.service.interfases.CreditService;
import utils.CreditType;

@Service
public class ServiceForCredits implements CreditService {

	private CreditRepository creditRepository;
	private UserRepository userRepository;

	@Autowired
	public ServiceForCredits(CreditRepository creditRepository, UserRepository userRepository) {
		this.creditRepository = creditRepository;
		this.userRepository = userRepository;

	}

	@Override
	public boolean createCredit(Credit credit) {
		creditRepository.save(credit);
		return true;
	}

	@Override
	public List<Credit> getAllCredit() {
		List<Credit> credits = new ArrayList<>(creditRepository.findAll());
		return credits;
	}

	@Override
	public boolean deleteCreditById(int id) {
		creditRepository.delete(id);
		return false;
	}

	@Override
	public boolean saveInCredit(Credit credit) {
		creditRepository.saveAndFlush(credit);
		return false;
	}

	@Override
	public Credit getCreditById(int id) {
		Credit credit = creditRepository.getOne(id);
		return credit;
	}

	@Override
	public List<String> timeForCredit() {
		List<String> messages = new ArrayList<>();
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Credit> credits = user.getCreditList();

		for (Credit credit : credits) {
			if (credit.getTimeToReturn() >= 1) {
				credit.setTimeToReturn(credit.getTimeToReturn() - 1);
				if (credit.getBaseTimeToReturn() - credit.getTimeToReturn() > 0) {
					credit.setAmount(
							credit.getAmount() + (int) (credit.getAmount() * ((credit.getPercent() / 30) / 100)));
				}
				if (credit.getTimeToReturn() <= 3) {
					// Here must be message!!!
					if (credit.getTimeToReturn() > 1)
						messages.add("Срок кредита " + credit.getAmount() + "$ истекает через "
								+ credit.getTimeToReturn() + " дня");
					else if (credit.getTimeToReturn() == 1)
						messages.add("Срок кредита " + credit.getAmount() + "$ истекает через "
								+ credit.getTimeToReturn() + " день");
					else if (credit.getTimeToReturn() == 0)
						messages.add("кредит просрочен, завтра будет списано " + credit.getAmount() + "$");

				}
			} else if (credit.getTimeToReturn() == 0) {
				user.setCreditRating(user.getCreditRating() - credit.getCreditRatingChange());
				returnCredit(credit.getId());

			}
		}
		return messages;
	}

	@Override
	public void returnCredit(int id) {

		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Credit credit = getCreditById(id);
		user.setBalance(user.getBalance() - credit.getAmount());
		if (credit.getBaseTimeToReturn() - credit.getTimeToReturn() >= 30) {
			user.setCreditRating(user.getCreditRating() + credit.getCreditRatingChange());
		}
		user.getCreditList().remove(credit);
		creditRepository.delete(credit);
		userRepository.save(user);

	}

	@Override
	public void claimCredit(Credit credit) {

		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		credit.setUser(user);
		creditRepository.save(credit);
		user.setBalance(user.getBalance() + credit.getAmount());
		user.getCreditList().add(credit);
		userRepository.save(user);

	}

	@Override
	public Credit createCreditForFactory(User user, CreditType type) {
		Credit credit = new Credit();
		Random rand = new Random();
		float rating = user.getCreditRating();
		float ratingChange = rating / 10;
		int time = 0;
		int amount = 0;
		float percent = 0.0f;

		switch (type) {
		case SHORTTERM:
			time = rand.nextInt(20) + 10;
			amount = (int) (rating * 250) + rand.nextInt((int) (rating * 250));
			percent = (rand.nextInt(10) + 10) + rand.nextFloat();
			credit.setAmount(amount);
			credit.setTimeToReturn(time);
			credit.setBaseTimeToReturn(time);
			credit.setCreditRating(rating);
			credit.setCreditRatingChange(ratingChange);
			credit.setPercent(percent);
			credit.setCreditType(type);
			break;
		case MIDDLETERM:
			time = rand.nextInt(60) + 30;
			amount = (int) (rating * 500) + rand.nextInt((int) (rating * 500));
			percent = (rand.nextInt(5) + 8) + rand.nextFloat();
			credit.setAmount(amount);
			credit.setTimeToReturn(time);
			credit.setBaseTimeToReturn(time);
			credit.setCreditRating(rating);
			credit.setCreditRatingChange(ratingChange);
			credit.setPercent(percent);
			credit.setCreditType(type);
			break;
		case LONGTERM:
			time = rand.nextInt(90) + 90;
			amount = (int) (rating * 1000) + rand.nextInt((int) (rating * 1000));
			percent = (rand.nextInt(5) + 5) + rand.nextFloat();
			credit.setAmount(amount);
			credit.setTimeToReturn(time);
			credit.setBaseTimeToReturn(time);
			credit.setCreditRating(rating);
			credit.setCreditRatingChange(ratingChange);
			credit.setPercent(percent);
			credit.setCreditType(type);
			break;
		default:
			break;
		}

		return credit;
	}

}
