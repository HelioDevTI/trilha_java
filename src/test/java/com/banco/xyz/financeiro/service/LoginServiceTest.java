package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.LoginAtualizarDTO;
import com.banco.xyz.financeiro.factory.LoginDTOFactory;
import com.banco.xyz.financeiro.factory.LoginFactory;
import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.recod.DadosAutenticacao;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.repository.LoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class LoginServiceTest {

    @MockitoBean
    private LoginRepository loginRepository;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginService loginService;



    @Test
    void getLoginTest(){

        Login login = LoginFactory.getLogin();
        Optional<Login> loginOptional = Optional.of(login);

        Mockito.when(loginRepository.findById(1L)).thenReturn(loginOptional);

        LoginRecord loginRecord = loginService.getLogin(1L);

        Assertions.assertEquals(login.getId(), loginRecord.id());
    }

    @Test
    void listaLoginsTest(){

        Login login = LoginFactory.getLogin();
        Login login2 = LoginFactory.getLogin();
        login2.setIdUsuario(2L);
        login2.setEmail("teste2@email.com");

        List<Login> listaLogin = List.of(login, login2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Login> page = new PageImpl<>(listaLogin, pageable, listaLogin.size());

        Mockito.when(loginRepository.findAll(pageable)).thenReturn(page);

        Page<LoginRecord> paginaLogin = loginService.listaLogins(pageable);

        Assertions.assertEquals(2, paginaLogin.getTotalElements());

    }

    @Test
    void salvarLoginTest(){

        String msg = "Salvo com Sucesso!";

        String msgRetorno = loginService.salvarLogin(LoginDTOFactory.getLogin());

        Assertions.assertEquals(msg, msgRetorno);
    }

    @Test
    void atualizarLoginTest(){

        String msg = "Atualizado com Sucesso!";

        LoginAtualizarDTO login = new LoginAtualizarDTO();
        login.setSenha("122333");
        login.setEmail("teste@teste.com");
        login.setIdLogin(1L);

        Mockito.when(loginRepository.getReferenceById(login.getIdLogin())).thenReturn(LoginFactory.getLogin());

        String msgRetorno = loginService.atualizarLogin(login);

        Assertions.assertEquals(msg, msgRetorno);
    }

    @Test
    void excluirLoginNaoEncontradoTest(){

        String msg = "Usuario nao encontrado";

        Mockito.when(loginRepository.getReferenceById(1L)).thenReturn(new Login());

        ResponseEntity<String> response = loginService.excluirLogin(1L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }

    @Test
    void excluirLoginTest(){

        String msg = "Usuario excluido com Sucesso!";

        Mockito.when(loginRepository.getReferenceById(1L)).thenReturn(LoginFactory.getLogin());

        ResponseEntity<String> response = loginService.excluirLogin(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }

    @Test
    void fazerLoginTest(){

        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "Teste User";
            }
        };

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);

        Authentication authenticationRetorno = loginService.fazerLogin(new DadosAutenticacao("teste@teste.com", "12345789"));

        Assertions.assertTrue(authenticationRetorno.isAuthenticated());
    }
}
