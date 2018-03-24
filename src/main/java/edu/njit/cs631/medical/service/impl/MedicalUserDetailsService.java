package edu.njit.cs631.medical.service.impl;

import edu.njit.cs631.medical.data.entity.security.Privilege;
import edu.njit.cs631.medical.data.entity.security.Role;
import edu.njit.cs631.medical.data.entity.security.User;
import edu.njit.cs631.medical.data.repository.security.UserRepository;
import edu.njit.cs631.medical.service.api.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MedicalUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(MedicalUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                userRepository.findAll().forEach((x) -> logger.error(x.toString()));
                throw new UsernameNotFoundException("No user found with username: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                            user.getPerson().getEmail(),
                            user.getPasswordHash(),
                            user.isEnabled(),
                            true, // accountNonExpired
                            true, // creditailsNonExpired
                            user.isEnabled(), // accountNonLocked
                            getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
