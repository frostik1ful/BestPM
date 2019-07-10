package juke.service.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import juke.entities.Motivator;
import juke.repositories.MotivatorRepository;
import juke.service.interfases.MotivatorService;

@Service
public class ServiceForMotivator implements MotivatorService {

	private MotivatorRepository motivatorRepository;

	@Autowired
	public ServiceForMotivator(MotivatorRepository motivatorRepository) {
		this.motivatorRepository = motivatorRepository;
	}

	@Override
	public List<Motivator> getMotivators() {
		return motivatorRepository.findAll();
	}

	@Override
	public boolean save(Motivator motivator) {
		if (motivatorRepository.findByName(motivator.getName()).isPresent()) {
			return false;
		} else {
			motivatorRepository.save(motivator);
			return true;
		}
	}

	@Override
	public Motivator getById(Integer id) {
		return motivatorRepository.findOne(id);
	}

	@Override
	public void deleteMotivatorById(int motivatorId) {
		motivatorRepository.delete(motivatorId);
		
	}

	@Override
	public void saveChanged(Motivator motivator) {
		motivatorRepository.saveAndFlush(motivator);
		// TODO Auto-generated method stub
		
	}

}
