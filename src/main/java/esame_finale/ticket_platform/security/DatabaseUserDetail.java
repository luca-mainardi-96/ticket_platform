package esame_finale.ticket_platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.model.Ruolo;

public class DatabaseUserDetail implements UserDetails{
    private Integer id;

    private String username;

    private String password;

    private Set<GrantedAuthority> authorities;

    public DatabaseUserDetail(Operatore op){
        this.id = op.getId();
        this.username = op.getUsername();
        this.password = op.getPassword();
        this.authorities = new HashSet<>();
        for(Ruolo ruoli : op.getRuoli()){
            SimpleGrantedAuthority sGA = new SimpleGrantedAuthority(ruoli.getNome());
            this.authorities.add(sGA);
        }
    }

    public Integer getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
