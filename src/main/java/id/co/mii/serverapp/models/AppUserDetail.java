package id.co.mii.serverapp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class AppUserDetail implements UserDetails {

  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // tanpa privilege
    // return user
    //   .getRoles()
    //   .stream()
    //   .map(role ->
    //     new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())
    //   )
    //   .collect(Collectors.toList());
    // dengan privilage
    List<GrantedAuthority> authorities = new ArrayList<>();

    user
      .getRoles()
      .forEach(role -> {
        authorities.add(
          new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()) // ROLE_USER
        );
        role
          .getPrivileges()
          .forEach(privilege -> {
            authorities.add(
              new SimpleGrantedAuthority(privilege.getName().toUpperCase())
            );
          });
      });

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getIsAccountLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.getIsEnabled();
  }
}