package com.nt.Runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nt.entity.Movie;
import com.nt.service.IMovieMgmtService;
@Component
public class CrudRepTestRunner implements CommandLineRunner {
	@Autowired
	private IMovieMgmtService service;

	@Override
	public void run(String... args) throws Exception {
// invoke service method
		Movie movie = new Movie();
		movie.setMname("RRR");
		movie.setRating(4.5f);
movie.setYear("2023");
try {
	System.out.println(service.registerMovie(movie));
}
catch(Exception e) {
	e.printStackTrace();
}
	}

}
