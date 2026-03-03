package com.framar.site.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.framar.site.model.Project;
import com.framar.site.model.MediaItem;

@Service
public class ProjectService {
	
	private final List<Project> projects = Arrays.asList(
				new Project(
						"pirovac-riva-sanacija",
						"/img/projekti/Pirovac-riva/video-thumb10.jpg",
						"Sanacija Stara riva - Pirovac",
						"Pirovac",
						"2025",
						"Niskogradnja i podvodni radovi",
						"Sanacije rive Stara riva u Pirovcu.",
						Arrays.asList(
							MediaItem.image("/img/projekti/Pirovac-riva/01.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/02.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/03.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/04.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/05.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/06.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/07.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/08.jpg"),
							MediaItem.image("/img/projekti/Pirovac-riva/09.jpg"),
							MediaItem.video("/video/projekti/Pirovac-riva/10.mp4",
									"/img/projekti/Pirovac-riva/video-thumb10.jpg")
						)
				
				),
			
				new Project(
						"prvić-luka-riva-sanacija",
						"/img/projekti/prvićLuka-riva/07.jpg",
						"Sanacija rive Kekino žalo - Prvić Luka",
						"Prvić Luka",
						"2025",
						"Niskogradnja i podvodni radovi",
						"Sanacija rive Kekino žalo u Prvić Luci",
						Arrays.asList(
							MediaItem.image("/img/projekti/prvićLuka-riva/01.jpg"),
							MediaItem.video("/video/projekti/prvićLuka-riva/02.mp4",
									"/img/projekti/prvićLuka-riva/video-thumb02.jpg"),
							MediaItem.image("/img/projekti/prvićLuka-riva/03.jpg"),
							MediaItem.image("/img/projekti/prvićLuka-riva/04.jpg"),
							MediaItem.video("/video/projekti/prvićLuka-riva/05.mp4",
									"/img/projekti/prvićLuka-riva/video-thumb05.jpg"),
							MediaItem.image("/img/projekti/prvićLuka-riva/06.jpg"),
							MediaItem.image("/img/projekti/prvićLuka-riva/07.jpg")
						)
				),
				new Project(
						"vranjica - cijevi - 2025",
						"/img/projekti/Vranjica/video-thumb01.jpg",
						"Podvodni radovi - Vranjica",
						"Vranjica",
						"2025",
						"Podvodni radovi",
						"Postavljanje obujmica i opteživaća na kanalizacijskoj cijevi. ",
						Arrays.asList(
								MediaItem.video("/video/projekti/Vranjica/01.mp4", 
										"/img/projekti/Vranjica/video-thumb01.jpg"),
								MediaItem.video("/video/projekti/Vranjica/02.mp4", 
										"/img/projekti/Vranjica/video-thumb02.jpg"),
								MediaItem.video("/video/projekti/Vranjica/03.mp4", 
										"/img/projekti/Vranjica/video-thumb03.jpg")
						)
				)
		);
	
	public List<Project> getProjects() {
		return projects;
	}
	
	public Optional<Project> getBySlug(String slug) {
		return projects.stream().filter(p -> p.getSlug().equals(slug)).findFirst();
	}
}
