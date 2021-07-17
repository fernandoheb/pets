package com.ifsp.pets.pets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pet {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		
		private Integer id;
		private String  nome;
		private String  especie;		
		private String  raca;
		
		public Pet() {super();}
		
		public Pet(String nome, String especie, String raca) {
			super();
			this.nome = nome;
			this.especie = especie;
			this.raca = raca;
		}
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getEspecie() {
			return especie;
		}
		public void setEspecie(String especie) {
			this.especie = especie;
		}
		public String getRaca() {
			return raca;
		}
		public void setRaca(String raca) {
			this.raca = raca;
		}	
		
}
