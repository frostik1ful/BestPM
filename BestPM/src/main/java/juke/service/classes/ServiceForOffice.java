package juke.service.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import juke.entities.Office;
import juke.repositories.OfficeRepository;
import juke.service.interfases.OfficeService;
import juke.utils.interfaces.OfficeGenerator;

@Service
public class ServiceForOffice implements OfficeService {

	private OfficeRepository officeRepository;
	private OfficeGenerator generator;

	@Autowired
	public ServiceForOffice(OfficeRepository officeRepository, OfficeGenerator generator) {
		this.officeRepository = officeRepository;
		this.generator = generator;
	}

	@Override
	public boolean generateAndSave(int level) {
		if (officeRepository.findByLevel(level).isPresent()) {
			return false;
		} else {
			Office office = generator.generateOffice(level);
			officeRepository.save(office);
			return true;
		}
	}

	@Override
	public boolean save(Office office) {
		Office officeCheck = officeRepository.save(office);
		if (officeCheck != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Office getOfficeByLevel(int level) {
		Optional<Office> optional = officeRepository.findByLevel(level);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return new Office();
		}
	}

	@Override
	public List<Office> getAll() {
		List<Office> officeList = new ArrayList<>();
		if (officeRepository.count() < 3) {
			for (int i = 2; i <= 3; i++) {
				officeList.add(generator.generateOffice(i));
			}
			return officeList;
		} else {
			return officeRepository.findAll();
		}
	}

}
