package com.votenolivro.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.votenolivro.model.livros.Livro;
import com.votenolivro.repository.interfaces.ILivroRepository;

@Service
@Transactional
public class LivroServiceImpl {
	@Autowired
	private ILivroRepository livroRepository;
	private int TOTAL_DE_LIVROS = 2;

	public List<Livro> listarLivrosParaVotar() {
		List<Livro> list = livroRepository.listAll();
		return processarListagem(list, new ArrayList<Livro>());
	}

	public void processarVotos(List<Livro> livros) 
	{
		if(!isNotEmptyOrNull(livros)){
			return;
		}
		Map<Long, Livro> map = transformToMap(livroRepository.listAll());
		List<Livro> livrosVotados = processarVoto(livros,map);
		livroRepository.saveOrUpdateAll(livrosVotados);
	}
	
	private List<Livro> processarVoto(List<Livro> livros, Map<Long, Livro> map) {
		List<Livro> livrosVotados = new ArrayList<Livro>();
		for (Livro livro : livros) {
			if(map.containsKey(livro.getId())){
				Livro livroEncontrado = map.get(livro.getId());
				livroEncontrado.adicionarVoto();
				if(!livrosVotados.contains(livroEncontrado)){
					livrosVotados.add(livroEncontrado);
				}
			}
		}
		return livrosVotados;
	}

	private Map<Long, Livro> transformToMap(List<Livro> livros) {
		Map<Long, Livro> map = new HashMap<Long, Livro>();
		if(isNotEmptyOrNull(livros)){
			for (Livro livro : livros) {
				map.put(livro.getId(), livro);
			}
		}
		return map;
	}

	private boolean isNotEmptyOrNull(List<Livro> livros) {
		return livros != null && !livros.isEmpty();
	}

	private List<Livro> processarListagem(List<Livro> livros,List<Livro> livrosRetornar) {
		if(livros == null || livros.isEmpty()){
			return null;
		}
		if(livros.size() == 1){
			return livros;
		}
		if(livrosRetornar.size() == TOTAL_DE_LIVROS){
			return livrosRetornar;
		}
		int total = livros.size();
		int index = (int)Math.floor(Math.random() * total);
		Livro livroSorteado = livros.get(index);
		if(!livrosRetornar.contains(livroSorteado)){
			livrosRetornar.add(livroSorteado);	
		}
		return processarListagem(livros, livrosRetornar);
	}

	public void setLivroRepository(ILivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}

	public List<Livro> listarRanking() {
		return livroRepository.listarRanking();
	}

	public void adicionar(String nome) {
		Livro livro = new Livro();
		livro.setNome(nome);
		livroRepository.save(livro);
	}

	public List<Livro> listarTodos() {
		return livroRepository.listAll();
	}

	public void cargaLivros() {
		List<Livro> list = new ArrayList<Livro>();
		Livro livro = new Livro();
		livro.setNome("Harry Potter");
		Livro livro1 = new Livro();
		livro1.setNome("Senhor dos Aneis");
		Livro livro2 = new Livro();
		livro2.setNome("50 tons de cinza");
		Livro livro3 = new Livro();
		livro3.setNome("Diario de um Banana");
		Livro livro4 = new Livro();
		livro4.setNome("Turma da Monica O Livro");
		list.add(livro);
		list.add(livro1);
		list.add(livro2);
		list.add(livro3);
		list.add(livro4);
		livroRepository.saveOrUpdateAll(list);
	}
	
	
}
