package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.business.CalculoTransacoes;
import com.banco.xyz.financeiro.dto.DadosArquivoCargaTransacao;
import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.repository.ContaRepository;
import com.banco.xyz.financeiro.repository.TipoTransacaoRepository;
import com.banco.xyz.financeiro.repository.TransacaoRepository;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CargaTransacaoService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CalculoTransacoes calculoTransacoes;


    public String processarTrasacoes(MultipartFile arquivo){

        List<DadosArquivoCargaTransacao>  listaDadosArquivo = new ArrayList<>();

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

               adicionaDadosArquivo(linha, listaDadosArquivo);
               quantLinhaArq++;

           }

        }catch (IOException | IllegalArgumentException e){
            throw  new RuntimeException("Erro para ler o arquivo");
        }



        return salvarTrasacoes(listaDadosArquivo, quantLinhaArq);
    }

    private void adicionaDadosArquivo(Row linha, List<DadosArquivoCargaTransacao> listaDadosArquivo){


        String nome = linha.getCell(0).getStringCellValue();
        String email = linha.getCell(1).getStringCellValue();
        String tipoTrsacao = linha.getCell(2).getStringCellValue();
        String descricao =  linha.getCell(3).getStringCellValue();
        String moeda = linha.getCell(4).getStringCellValue();
        BigDecimal valor = BigDecimal.valueOf(linha.getCell(5).getNumericCellValue());
        LocalDate data = LocalDate.ofInstant(linha.getCell(6).getDateCellValue().toInstant(), ZoneId.systemDefault());

        double horaDouble = linha.getCell(7).getNumericCellValue();
        Date date = DateUtil.getJavaDate(horaDouble);
        LocalTime hora = LocalTime.of(date.getHours(), date.getMinutes(), date.getSeconds());

        listaDadosArquivo.add(new DadosArquivoCargaTransacao(nome, email, tipoTrsacao, descricao, moeda, valor, data, hora));

    }

    private String salvarTrasacoes(List<DadosArquivoCargaTransacao> dadosArquivo,  int quantTransaArq){

        AtomicInteger quantTrasaProce = new AtomicInteger();

        dadosArquivo.forEach(dadosTransacao -> {

            Long idConta = contaRepository.idContaUsuarioEmail(dadosTransacao.getNome(), dadosTransacao.getEmail());
            Long idTipo = tipoTransacaoRepository.idTipoMoedaDescricao(dadosTransacao.getMoeda(),
                    dadosTransacao.getTipoTransacao());

            if(calculoTransacoes.atualizaSaldo(idConta, dadosTransacao.getValor())) {

                Transacao transacao = new Transacao();
                transacao.setIdConta(idConta);
                transacao.setIdTipo(idTipo);
                transacao.setDescricao(dadosTransacao.getDescricao());
                transacao.setValor(dadosTransacao.getValor());
                transacao.setDataTransacao(dadosTransacao.getData().atTime(dadosTransacao.getHora()));

                transacaoRepository.save(transacao);
                quantTrasaProce.getAndIncrement();
            }
        });

        return "Aquivo com " + quantTransaArq + " trasações e foram cadastradas " + quantTrasaProce.get();

    }
}
