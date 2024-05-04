package edu.tcu.cs.monning_meteorite_gallery.meteoriteUser;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<MeteoriteUser> findAll() {
        return this.userRepository.findAll();
    }

    public MeteoriteUser findById(Integer userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    public MeteoriteUser save(MeteoriteUser newMeteoritesUser) {
        // We NEED to encode plain text password before saving to the DB! TODO
        newMeteoritesUser.setPassword(this.passwordEncoder.encode(newMeteoritesUser.getPassword()));
        return this.userRepository.save(newMeteoritesUser);
    }

    /**
     * We are not using this update to change user password.
     *
     * @param userId
     * @param update
     * @return
     */
    public MeteoriteUser update(Integer userId, MeteoriteUser update) {
        MeteoriteUser oldMeteoriteUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
        oldMeteoriteUser.setUsername(update.getUsername());
        oldMeteoriteUser.setEnabled(update.isEnabled());
        oldMeteoriteUser.setRoles(update.getRoles());
        return this.userRepository.save(oldMeteoriteUser);
    }

    public void delete(Integer userId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
        this.userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username) // First, we need to find this user from database.
                .map(meteoriteUser -> new MyUserPrincipal(meteoriteUser)) // If found, wrap the returned user instance in a MyUserPrincipal instance.
                .orElseThrow(() -> new UsernameNotFoundException("username " + username + " is not found.")); // Otherwise, throw an exception.
    }

}
