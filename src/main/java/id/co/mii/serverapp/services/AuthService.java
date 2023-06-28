package id.co.mii.serverapp.services;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.requests.LoginRequest;
import id.co.mii.serverapp.models.dto.requests.UserRequest;
import id.co.mii.serverapp.models.dto.response.LoginResponse;
import id.co.mii.serverapp.repositories.UserRepository;

@Service
@AllArgsConstructor
public class AuthService {
  private UserRepository userRepository;
  private ModelMapper modelMapper;
  private RoleService roleService;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private AppUserDetailService appUserDetailService;

  public User register(UserRequest userRequest) {
    Employee employee = modelMapper.map(userRequest, Employee.class);
    User user = modelMapper.map(userRequest, User.class);

    employee.setUser(user);
    user.setEmployee(employee);

    // set default role
    List<Role> roles = new ArrayList<>();
    roles.add(roleService.getById(3));
    user.setRoles(roles);

    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

    return userRepository.save(user);
  }

  public LoginResponse login(LoginRequest loginRequest) {
    // authentication => login request = username & password
    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(),
        loginRequest.getPassword());

    // set principle
    Authentication auth = authenticationManager.authenticate(authReq);
    SecurityContextHolder.getContext().setAuthentication(auth);

    User user = userRepository
        .findByUsernameOrEmployee_Email(
            loginRequest.getUsername(),
            loginRequest.getUsername())
        .get();

    UserDetails userDetails = appUserDetailService.loadUserByUsername(
        loginRequest.getUsername());

    List<String> authorities = userDetails
        .getAuthorities()
        .stream()
        .map(authority -> authority.getAuthority())
        .collect(Collectors.toList());

    // response => user detail = username, email, List<GrantedAuthority>
    return new LoginResponse(
        user.getUsername(),
        user.getEmployee().getEmail(),
        authorities);
  }
}
