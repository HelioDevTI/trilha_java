package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.DadosArquivoCargaUsuario;
import com.banco.xyz.financeiro.model.Conta;
import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.repository.ContaRepository;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.util.FinanceiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class CargaUsuarioService {


    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ContaRepository contaRepository;

    public  List<DadosArquivoCargaUsuario> processarUsuarios(MultipartFile arquivo){

        List<DadosArquivoCargaUsuario> listaArquivoUsuario = new ArrayList<>();

        int quantLinhaArq = 0;

        try(InputStream inputStream = arquivo.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){

            XSSFSheet sheet =  workbook.getSheetAt(0);

            for(Row linha : sheet){

                //Pula linha em Branco
                if(linha == null)
                    continue;

                //Pular o cabecalho
                if(linha.getRowNum() == 0)
                    continue;

                adicionaDadosArquivo(linha, listaArquivoUsuario);
                quantLinhaArq++;

            }

        }catch (IOException | IllegalArgumentException e){
            throw  new RuntimeException("Erro para ler o arquivo carga Usuario");
        }

        salvarUsuarios(listaArquivoUsuario, quantLinhaArq);

        return listaArquivoUsuario;
    }

    private void adicionaDadosArquivo(Row linha, List<DadosArquivoCargaUsuario> listaDadosArquivo){

        boolean isCorrentista = varificaCorrentista(linha);

        String nome = linha.getCell(0).getStringCellValue();
        String cpf = linha.getCell(1).getStringCellValue();
        String perfil = linha.getCell(2).getStringCellValue();
        String email =  linha.getCell(3).getStringCellValue();
        String senha = linha.getCell(4).getStringCellValue();
        Long numeroConta = null;
        Long digitoConta = null;
        Long agenciaConta = null;
        BigDecimal valorDeposito = null;

        FinanceiroUtil.validarCPF(cpf);

        if(isCorrentista) {
            numeroConta = Double.valueOf(linha.getCell(5).getNumericCellValue()).longValue();
            digitoConta = Double.valueOf(linha.getCell(6).getNumericCellValue()).longValue();
            agenciaConta = Double.valueOf(linha.getCell(7).getNumericCellValue()).longValue();
            valorDeposito = BigDecimal.valueOf(linha.getCell(8).getNumericCellValue());
        }

        log.info(">>>>>> ADICIONADO {}", nome);

        listaDadosArquivo.add(new DadosArquivoCargaUsuario(nome, cpf, perfil, email, senha, numeroConta, digitoConta, agenciaConta, valorDeposito, isCorrentista));


    }

    private String salvarUsuarios(List<DadosArquivoCargaUsuario> dadosArquivoUsuarios, int quantUsuarioArq){

        LocalDateTime dataAtual = LocalDateTime.now();

        AtomicInteger quantUsuProce = new AtomicInteger();

        dadosArquivoUsuarios.forEach(usu ->{

            Long idPerfil = perfilRepository.idPerfilTipo(usu.getPerfil());

            Usuario usuario = new Usuario();
            usuario.setNome(usu.getNome());
            usuario.setCpf(usu.getCpf());
            usuario.setPerfil(idPerfil);
            usuario.setDataCriacao(dataAtual);

           Usuario usuarioSalvo = usuarioRepository.save(usuario);

           Login login = new Login();
           login.setIdUsuario(usuarioSalvo.getId());
           login.setEmail(usu.getEmail());
           login.setSenha(usu.getSenha());
           login.setDataAutlizacao(dataAtual);

           loginRepository.save(login);

           if(usu.isCorrentista()){

               Conta conta = new Conta();
               conta.setIdUsuario(usuarioSalvo.getId());
               conta.setNumero(usu.getNumeroConta());
               conta.setDigito(usu.getDigitoConta());
               conta.setAgencia(usu.getAgenciaConta());
               conta.setSaldo(usu.getValorDeposito());
               conta.setDataCriacao(dataAtual);

               contaRepository.save(conta);
           }

            quantUsuProce.getAndIncrement();
        });

        return "Aquivo com " + quantUsuarioArq + " usuarios e foram cadastrados " + quantUsuProce.get();
    }

    private boolean varificaCorrentista(Row linha){

        return !(linha.getCell(5) == null) ||
               !(linha.getCell(6) == null) ||
               !(linha.getCell(7) == null) ;

    }

}
