package com.example.userservice;

import com.example.userservice.dto.model.RoleDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests {
	WebTestClient webTestClient;
	@LocalServerPort int port;
	public UserServiceApplicationTests(@LocalServerPort int port){
		webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:"+port).build();
	}

	@Test
	void contextLoads() {
	}
	@Test
	@Order(1)
	void createRole() {
		RoleDto role = new RoleDto();
		role.setRoleName("admin");
		role.setRoleDescription("Admin role");
		webTestClient.post()
			.uri("/api/v1/role")
				.contentType(APPLICATION_JSON)
				.bodyValue(role)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.roleName").isEqualTo("admin");
	}
	@Test
	@Order(2)
	void createDuplicateRole(){
		RoleDto role = new RoleDto();
		role.setRoleName("admin");
		role.setRoleDescription("Admin role");
		webTestClient.post()
			.uri("/api/v1/role")
			.contentType(APPLICATION_JSON)
			.bodyValue(role)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().is4xxClientError()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.message").isEqualTo("Role already exists");
	}
	@Order(3)
	@Test void getAllRoles(){
		webTestClient.get()
			.uri("/api/v1/role")
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBodyList(RoleDto.class);
	}
	@Test
	@Order(4)
	void deleteRole(){
		webTestClient.delete()
			.uri("/api/v1/role/"+1)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isNoContent();
	}

}
