package challenge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")

public class RecipeValidationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private final HttpHeaders httpHeaders;
	
	public RecipeValidationTest() {
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
	}

	@Test
	public void save() {
		ResponseEntity<Void> response = restTemplate.exchange("/recipe", HttpMethod.POST, new HttpEntity<>("{}", httpHeaders), Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void update() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234", HttpMethod.PUT, new HttpEntity<>("{}", httpHeaders), Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void delete() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234", HttpMethod.DELETE, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void get() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234", HttpMethod.GET, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void listByIngredient() {
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/ingredient?ingredient=ovo", HttpMethod.GET, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void search() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/search?search=bolo", HttpMethod.GET, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void like() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234/like/1122", HttpMethod.POST, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void unlike() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234/like/1122", HttpMethod.DELETE, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void addComment() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234/comment", HttpMethod.POST, new HttpEntity<>("{}", httpHeaders), Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void updateComment() {
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234/comment/987", HttpMethod.PUT, new HttpEntity<>("{}", httpHeaders), Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void deleteComment() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/1234/comment/9876", HttpMethod.DELETE, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
