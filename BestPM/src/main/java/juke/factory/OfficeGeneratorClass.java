package juke.factory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import juke.entities.Office;
import juke.repositories.OfficeRepository;
import juke.utils.interfaces.OfficeGenerator;

@Component
public class OfficeGeneratorClass implements OfficeGenerator {

	private OfficeRepository officeRepository;

	@Autowired
	public OfficeGeneratorClass(OfficeRepository officeRepository) {
		this.officeRepository = officeRepository;
	}

	@Override
	public Office generateOffice(int level) {
		Optional<Office> optionalOffice = officeRepository.findByLevel(level);
		if (optionalOffice.isPresent()) {
			return optionalOffice.get();
		}
		Office office = null;
		switch (level) {
		case 1:
			office = generate("Garage", 5, (short) level);
			office.setPrice(0);
			office.setPriceDonate(0);
			office.setImageURL("/resources/images/Garage.jpeg");
			break;
		case 2:
			office = generate("Office", 20, (short) level);
			office.setPrice(150000);
			office.setPriceDonate(20000);
			office.setImageURL("/resources/images/Room.jpeg");
			break;
		case 3:
			office = generate("Company", 50, (short) level);
			office.setPrice(300000);
			office.setPriceDonate(60000);
			office.setImageURL("/resources/images/Office.jpg");
			break;
		default:
			office = generate("Unknown", 0, (short) 0);
			break;
		}
		return officeRepository.save(office);
	}

	private Office generate(String name, int countProgrammers, int level) {
		Office office = new Office();
		office.setOfficeName(name);
		office.setCountProgrammers(countProgrammers);
		office.setLevel(level);
		return office;
	}

}
