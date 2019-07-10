package juke.factory;

import java.util.Random;

import org.springframework.stereotype.Component;

import juke.utils.interfaces.ChooseGenerator;
import utils.Rating;

@Component
public class ChooseGeneratorClass implements ChooseGenerator {

	private Random random = new Random();

	@Override
	public boolean generateChooseProgrammer(int price, int programmerSalary, Rating rating) {
		boolean choose = false;
		int difference = price - programmerSalary;
		int randomChoise = 0;
		switch (rating) {
		case JUNIOR:
			randomChoise = random.nextInt(100) - 50;
			break;
		case MIDDLE:
			randomChoise = random.nextInt(200) - 50;
			break;

		default:
			randomChoise = random.nextInt(300) - 100;
			break;
		}
		if (difference < randomChoise) {
			choose = false;
		} else {
			choose = true;
		}
		return choose;
	}

}
