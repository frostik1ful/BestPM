package juke.factory;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import juke.entities.Project;
import juke.repositories.ProjectRepository;
import juke.utils.interfaces.ProjectFactory;
import utils.TypeOfPayment;

@Component
public class ProjectFactoryClass implements ProjectFactory {

	private ProjectRepository projectRepository;
	private Random random = new Random();
	// private final int MIN_DIFFICULT = 2000;

	private final String[] projectNames = { "Quadline", "Codeqvoway", "Kintone", "Physzoom", "Bamfix",
			"Spancorporation", "Triobase", "Overhow", "Faxphase", "Ventocone", "Ganjatexon", "Truecanla", "Nimgreen",
			"Sancode", "icetax", "Planettech", "finmedia", "nim-care", "Freshquote", "Technolax", "Xxx-dexon",
			"Statstrip", "Faseis", "Goldenzap", "Solhex", "Ganjatrax", "Caneholding", "Faselab", "Tampdonfan",
			"City-base", "Daltzatdom", "Roundkix", "Aplam", "Tintaxon", "Drilltrans", "Streetzoom", "Jayfix", "recity",
			"Duokix", "Roundstrip", "Indicare", "Planetlax", "Zonfase", "Tamsontex", "Toughfix", "Canecane", "Alphazap",
			"Inchdrill", "acetrans", "sumtrans", "Xxx-mednix", "Runtrans", "Keyex", "Ranbebase", "Namtech", "J-can",
			"Sub-holding", "Sub-lux", "Movedomcan", "Zenway", "Waregreen", "Runhow", "Funtexon", "Zoomzoom", "zapace",
			"Goldtalam", "Bamstrip", "Voltzoom", "Baselane", "D-antechi", "Freshtech", "Plus-tone", "truedox", "Zen-in",
			"Conflex", "Runlamlam", "dan-city", "Ozertrax", "Incare", "Medla", "Hatdantech", "Superis", "Inditexon",
			"Volcorporation", "X-remdom", "Freshgreen", "biocode", "Islamfan", "domcare", "Trippleron", "Freshfind",
			"Trustdom", "mathhigh", "Ranzap", "X-touch", "Lotlab", "Blacklux", "Tampbiolex", "Zonlam", "Tristechnology",
			"Zercone", "Vilazoom", "Strongcan", "Tranfind", "Donlane", "Sannix", "Zoomlam", "lamcone", "Inchex",
			"Technoplus", "Duoron", "Sanzap", "Groovehex", "Dingzim", "jay-flex", "Redzoom", "zamelectrics", "Gogoway",
			"Treemedia", "Canqvotax", "Physsanfan", "Sanbioit", "viataxon", "E-remfix", "Unalane", "Saltdex",
			"Singleice", "Tamhigh", "X-lathigh", "Singleice", "Redlane", "Hotvivadax", "Latbeplus", "Zotkayit", "Bioin",
			"Graveplanet", "Konredron", "Injocone", "Apronzim", "Techitrans", "Codetrans", "Vaiahow", "Goldtechno",
			"Zerlane", "Trestam", "hotquote", "E-zim", "groovetrax", "Math-fase", "Triohouse", "HelloWorld", "Juke",
			"Oak" };

	@Autowired
	public ProjectFactoryClass(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	// { Fixed by Sergey Christensen
	@Override
	public Project generateProject(float projectDifficult) {
		String projectName = projectNames[random.nextInt(projectNames.length - 1)];
		// long countAllProject = projectRepository.count();
		// long countMoreThanMedium =
		// projectRepository.countByDifficultLessThanEqual(10000F);
		boolean moreTimeMaterial = projectRepository.countByTypeOfPayment(
				TypeOfPayment.TIMEMATERIAL) > projectRepository.countByTypeOfPayment(TypeOfPayment.FIXPRICE);
		Optional<Project> optionalProject = projectRepository.findByNameAndUserIsNull(projectName);
		if (optionalProject.isPresent()) {
			return optionalProject.get();
		} else {
			// float difficult = 0.0F;
			// if (countMoreThanMedium < countAllProject / 2) {
			// difficult = (float) random.nextInt(10000) + 10000;
			// } else {
			// difficult = (float) random.nextInt(8000) + MIN_DIFFICULT;
			// }
			int time = Math.round(projectDifficult / 40f);
			int money = time * 150;
			Project project = new Project(projectName, time, money, projectDifficult, TypeOfPayment.FIXPRICE);
			if (moreTimeMaterial) {
				project.setTypeOfPayment(TypeOfPayment.FIXPRICE);
				project.setMoney(money + money / 2);
				project.setMoneyLeft(money + money / 2);
			} else {
				project.setTypeOfPayment(TypeOfPayment.TIMEMATERIAL);
			}
			return projectRepository.save(project);
		}
	}
	// } Fixed by Sergey Christensen
}
