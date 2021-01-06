package oit.is.chisakiken.hondaboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.repository.LoginUserRepository;
import oit.is.chisakiken.hondaboard.service.exception.DifferentOldPasswd;
import oit.is.chisakiken.hondaboard.service.exception.DuplicateUserException;
import oit.is.chisakiken.hondaboard.service.exception.NotFoundUserException;

@Service
public class UserAccountService implements UserDetailsService {
    @Autowired
    private LoginUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) throw new UsernameNotFoundException("");

        var userModel = userRepository.findByName(username);
        
        if (userModel == null) throw new UsernameNotFoundException("");

        return userModel.get();
    }

    @Transactional
    public void registerUser(String name, String password) throws DuplicateUserException {
        if (userRepository.findByName(name).isPresent()) {
            throw new DuplicateUserException();
        }
        var enc_pass = passwordEncoder.encode(password);
        var newUser = new LoginUser(name, enc_pass);
        userRepository.save(newUser);
    }

    @Transactional
    public void changePasswd(int userid, String oldpw, String newpw) throws NotFoundUserException,DifferentOldPasswd {
        var user = userRepository.findById(userid);
        if (!user.isPresent()) {
            throw new NotFoundUserException();
        }
        var enc_newpw = passwordEncoder.encode(newpw);
        if (!passwordEncoder.matches(oldpw, user.get().getPassword())){
            throw new DifferentOldPasswd();
        }
        userRepository.updatePassword(user.get().getId(), enc_newpw);
    }
}
