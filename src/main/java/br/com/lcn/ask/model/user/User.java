package br.com.lcn.ask.model.user;

import br.com.lcn.ask.model.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user_system")
public class User implements UserDetails {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	@NotBlank(message = "Login não pode ser vazio")
	@Size(max = 30, message="Login deve ter no maximo 30 caracteres")
	private String login;

	@NotBlank(message = "Nome não pode ser vazio")
	@Size(max = 80, message="Nome deve ter no maximo 30 caracteres")
	private String name;

	@NotBlank(message = "Senha não pode ser vazio")
	@Size(max = 255)
	private String password;

	@NotBlank(message = "E-mail não pode ser vazio")
	@Size(max = 60, message="E-mail deve ter no maximo 60 caracteres")	
	@Email(message = "E-mail esta no formato invalido")
	private String email;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_has_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>(0);
	
	@Column(columnDefinition = "SMALLINT")
	private boolean active;

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
