package com.isdma.dscatalog.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.isdma.dscatalog.dto.UserDTO;
import com.isdma.dscatalog.dto.UserInsertDTO;
import com.isdma.dscatalog.dto.UserUpdateDTO;
import com.isdma.dscatalog.services.UserService;

//O nosso recourse implementa o controller Rest, é a nossa API(Application programming interface) do nosso backend
//sao os recursos disponibilizados para os  frontoffice
//Neste caso esta classe é o recurso da entidade User

@RestController // Annotation para o Spring saber que é uma class de resources, forma de
				// implementar algo que ja foi implementado
@RequestMapping(value = "/users") // rota REST do nosso recurso
public class UserResource {

	@Autowired // Para injetar automaticamente as dependencias
	private UserService service;

	/*
	 * @GetMapping 
	 * public ResponseEntity<List<UserDTO>> findAll(){
	 * 
	 * List<UserDTO> list = service.findAll();
	 * 
	 * /*List<User> list = new ArrayList<>(); list.add(new
	 * User(1L,"Books")); list.add(new User(2L, "Electronics"));
	 * 
	 * return ResponseEntity.ok(list);
	 * 
	 * }
	 */
	// Fazer o findall mas paginado que é melhor abordagem
		//requestParam sao anotation para personalizarmos as requisições com muitas definições dadas pelo Spring
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "firstName") String orderBy
			){
		
		//Declarar objeto especial do Spring e instancio com um metodo de builder delel com os parametros que queremos definidos acima
		PageRequest pagerequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		
		Page<UserDTO> list = service.findAllPaged(pagerequest);
		
		return ResponseEntity.ok(list);
		
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {

		UserDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);

	}

	@PostMapping // Post para inserir
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
		UserDTO newDto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();

		return ResponseEntity.created(uri).body(newDto);
		// por defeito com o ok dá o codigo de suecesso 200 de requesição com sucesso
		// mas nos queremos neste caso o 201 que quer dizer inserção com sucesso
		// Neste caso até vamos querer alem do codigo 201 ter no cabeçalho o caminho
		// para o novo objeto criado
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
		UserDTO newdto = service.update(id, dto);

		return ResponseEntity.ok().body(newdto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();// nao tem corpo a resposta

	}

}
