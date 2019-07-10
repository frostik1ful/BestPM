package juke.service.interfases;

import java.util.List;

import juke.entities.Office;

public interface OfficeService {

	public boolean generateAndSave(int level);

	public boolean save(Office office);

	public Office getOfficeByLevel(int level);

	public List<Office> getAll();

}
