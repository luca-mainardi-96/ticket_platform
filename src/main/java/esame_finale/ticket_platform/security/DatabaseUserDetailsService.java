package esame_finale.ticket_platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.repository.OperatoreRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private OperatoreRepository opRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Operatore> operOpt = opRepo.findByUsername(username);
        if(operOpt.isPresent()){
            return new DatabaseUserDetail(operOpt.get());
        } else {
            throw new UsernameNotFoundException ("Username not found.");
        }
    }
}
