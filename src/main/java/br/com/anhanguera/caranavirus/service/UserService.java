package br.com.anhanguera.caranavirus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.repository.UserRepository;

@Service
public class UserService {

	private UserRepository usuarioRepository;

	public UserService(UserRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public void save(User usuario) {
		usuarioRepository.save(usuario);
	}

	public Optional<User> loadById(Long id) {
		return usuarioRepository.findById(id);
	}

	public List<User> findAll() {
		return usuarioRepository.findAll();
	}

	public void delete(User usuario) {
		usuarioRepository.delete(usuario);
	}

}
