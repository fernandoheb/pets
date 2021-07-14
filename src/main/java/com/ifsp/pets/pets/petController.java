package com.ifsp.pets.pets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;





@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/pet")
public class petController {
	
	@Autowired
	PetRepository petRepo;
	
	
	@PostMapping("/add")
	public @ResponseBody String novoPet(@RequestParam String nome, @RequestParam String raca, @RequestParam String especie) {
		Pet pet = new Pet(nome,raca,especie);
		petRepo.save(pet);
		return "<h1 style='text-align:center'>Inserido com sucesso </h1> <br> <a href='http://localhost:8080/'> Voltar</a>";		
	}
	@PostMapping(path = "/addpet")
	public @ResponseBody String novoPet2(@RequestBody Pet newPet) {
		petRepo.save(newPet);
		return "Usuário inserido com sucesso";
	}
	@DeleteMapping(path="/kill/{id}")
	public @ResponseBody String huntdownPet(@PathVariable(required = true, name ="id") Integer id) {
		Optional<Pet> sadPet = petRepo.findById(id);
		if(sadPet.isPresent()) {
			String nome = sadPet.get().getNome();  
			petRepo.delete(sadPet.get());
			return "&#10014; RIP "+ nome +" &#10014;";
		}
		return "Usuário não encontrado";
	}
	
	@GetMapping(path="/kill/{id}")
	public @ResponseBody String huntdownGetPet(@PathVariable(required = true, name ="id") Integer id) {
		Optional<Pet> sadPet = petRepo.findById(id);
		if(sadPet.isPresent()) {
			String nome = sadPet.get().getNome();  
			petRepo.delete(sadPet.get());
			return "<h1 style='text-align:center'> &#10014; RIP "+ nome +" &#10014;</h1>" 
			+"<br> <a href='http://localhost:8080/'> Voltar</a>";
		}
		return "Pet não encontrado"
		+" <br> <a href='http://localhost:8080/'> Voltar</a>";
	}
	
	@PostMapping(path="update")
	  public @ResponseBody String AtualizaPorForm(@ModelAttribute("pet") Pet pet) {
		
		int id = pet.getId();
		Optional<Pet> petto = petRepo.findById(id);
		if(petto.isPresent()) {
			petto.get().setNome(pet.getNome());
			petto.get().setEspecie(pet.getEspecie());
			petto.get().setRaca(pet.getRaca());
			petRepo.save(petto.get());	
			return "Dados Atualizados <a href='list'> Voltar</a>";
		}		
	    return "Pet Não encontrado";
	  }
	
	@PutMapping(path="update/{id}")
	public @ResponseBody Optional<Pet> updateUser(@PathVariable(required = true, name="id") Integer id,
			@RequestBody Pet pet){ /* json do pet*/
			Optional<Pet> petto = petRepo.findById(id);
				if(petto.isPresent()) {
					petto.get().setNome(pet.getNome());
					petto.get().setEspecie(pet.getEspecie());
					petto.get().setRaca(pet.getRaca());
					petRepo.save(petto.get());	
					return petto;
				}		
		return null;		
	}
	
	
	@PutMapping(path = "/pet/{id}")
	public @ResponseBody ResponseEntity<?> alteraPet(@PathVariable(required = true, name = "id") Integer id,
			@RequestBody Pet pet){ /* json do pet*/
		Optional<Pet> petto = petRepo.findById(id);
		if(petto.isPresent()) {
			petto.get().setNome(pet.getNome());
			petto.get().setEspecie(pet.getEspecie());
			petto.get().setRaca(pet.getRaca());		
			
			return ResponseEntity.ok(petRepo.save(petto.get()));
		}		
		Map<String, String> mensagem = new HashMap<String, String>();
		mensagem.put("erro","não encontrado");
		return ResponseEntity.status(404).body(mensagem);
		
	}	
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Pet> retornaTodos() {
		return petRepo.findAll();
	}
	
	@GetMapping(path = "/getpet")
	public @ResponseBody Optional<Pet> retornaId(@RequestParam String id){
		return petRepo.findById(Integer.parseInt(id));
	}
	
	
	@GetMapping(path = "/locate/{id}")
	public @ResponseBody Optional<Pet> retornaPet(@PathVariable (required=true, name="id") Integer id){
		return petRepo.findById(id);
	}
	
	@GetMapping(path="/n/{Nome}")
	public @ResponseBody List<Pet> locateByNome(@PathVariable(required = true, name="Nome") String Nome){
		return petRepo.findByNome(Nome);		
	}
	
	@GetMapping(path="raca/{Raca}")
	public @ResponseBody List<Pet> locateByRaca(@PathVariable(required = true, name="Raca") String Raca){
		return petRepo.findByRaca(Raca);		
	}
	
	@GetMapping(path="especie/{Especie}")
	public @ResponseBody List<Pet> locateByEspecie(@PathVariable(required = true, name="Especie") String Especie){
		return petRepo.findByEspecie(Especie);		
	}
	
	@GetMapping(path="/list")
	public String ListarPets(Model model){
		model.addAttribute("pets",petRepo.findAll());
		return "list";		
	}
	
	
	@GetMapping(path="/p/{id}")
	public String DadosPet(@PathVariable (required=true, name="id") Integer id, Model model){
		model.addAttribute("pet",petRepo.findById(id));
		return "dados";		
	}
	
	
	
}
