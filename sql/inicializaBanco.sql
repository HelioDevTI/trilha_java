USE financeiro;

CREATE TABLE perfil (
  id INT PRIMARY KEY AUTO_INCREMENT,
  tipo VARCHAR(100) NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  data_criacao TIMESTAMP NOT NULL
);

CREATE TABLE usuario (
  id INT PRIMARY KEY AUTO_INCREMENT,
  perfil_id INT,
  nome VARCHAR(100) NOT NULL,
  cpf VARCHAR(15) NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  UNIQUE (cpf),
  FOREIGN KEY (perfil_id) REFERENCES perfil(id)
);

CREATE TABLE conta (
  id INT PRIMARY KEY AUTO_INCREMENT,
  usuario_id INT,
  numero INT NOT NULL,
  digito INT NOT NULL,
  agencia INT NOT NULL,
  saldo DOUBLE NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE login (
  id INT PRIMARY KEY AUTO_INCREMENT,
  usuario_id INT,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  data_login TIMESTAMP,
  data_atualizacao TIMESTAMP NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);


CREATE TABLE tipo_transacao (
  id INT PRIMARY KEY AUTO_INCREMENT,
  moeda VARCHAR(3) NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  ativo BOOLEAN,
  tipo VARCHAR(10) NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  CHECK (tipo IN('DEBITO', 'CREDITO'))
);

CREATE TABLE transacao (
  id INT PRIMARY KEY AUTO_INCREMENT,
  tipo_id INT,
  conta_id INT,
  descricao VARCHAR(255) NOT NULL,
  valor DOUBLE NOT NULL,
  valor_convertido DOUBLE,
  data_transacao TIMESTAMP NOT NULL,
  FOREIGN KEY (tipo_id) REFERENCES tipo_transacao(id),
  FOREIGN KEY (conta_id) REFERENCES conta(id)
);

INSERT INTO perfil (id, tipo, descricao, data_criacao)
VALUES(1, 'ADMINISTRADOR', 'Usuario responsavel pela aplicacao', CURRENT_TIMESTAMP());

INSERT INTO perfil (id, tipo, descricao, data_criacao)
VALUES(2, 'GERENTE', 'Usuario responsavel por administra correntistas', CURRENT_TIMESTAMP());

INSERT INTO perfil (id, tipo, descricao, data_criacao)
VALUES(3, 'CORRENTISTA', 'Usuario da conta correte', CURRENT_TIMESTAMP());


INSERT INTO usuario(id, perfil_id, nome, cpf, data_criacao)
values(1, 1, 'Administrador', '272.132.030-02', CURRENT_TIMESTAMP());

INSERT INTO usuario(id, perfil_id, nome, cpf, data_criacao)
values(2, 2, 'Gerente', '401.308.810-07', CURRENT_TIMESTAMP());

INSERT INTO usuario(id, perfil_id, nome, cpf, data_criacao)
values(3, 3, 'Correntista', '023.822.520-81', CURRENT_TIMESTAMP());

INSERT INTO usuario(id, perfil_id, nome, cpf, data_criacao)
values(4, 3, 'Segundo Correntista', '550.125.620-70', CURRENT_TIMESTAMP());


INSERT INTO login (id, usuario_id, email, senha, data_login, data_atualizacao)
values (1, 1, 'administrador@email.com', '$2a$04$GO2.8w2aXTUs6V.2czafwekoA/msiW45vjJrAnv1pFW//JotH9uz.', null, CURRENT_TIMESTAMP());

INSERT INTO login (id, usuario_id, email, senha, data_login, data_atualizacao)
values (2, 2, 'gerente@email.com', '$2a$04$JS9b3TwL1aBLM/n1E2qve.sAdxgfnK0o1ut5KUtM3YgEbhXJSzuAi', null, CURRENT_TIMESTAMP());

INSERT INTO login (id, usuario_id, email, senha, data_login, data_atualizacao)
values (3, 3, 'correntista@email.com', '$2a$04$.seZQjOcNY9ZTWqSMjOe4u/jdaKrfaRt7lfktOvq79p9xR03hAFyC', null, CURRENT_TIMESTAMP());

INSERT INTO login (id, usuario_id, email, senha, data_login, data_atualizacao)
values (4, 4, 'segundo.correntista@email.com', '$2a$04$YM6tYTfKN5X//bLtXz0..eNfO3YzjNy1aCpsxu04/TWAQr6zwyKMG', null, CURRENT_TIMESTAMP());

INSERT INTO conta (id, usuario_id, numero , digito, agencia , saldo, data_criacao)
values(1, 3, 11111, 1, 111, 100000.00, CURRENT_TIMESTAMP());

INSERT INTO conta (id, usuario_id, numero , digito, agencia , saldo, data_criacao)
values(2, 4, 22222, 2, 222, 200000.00, CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(1, 'R$', 'Compra Cartao Debito', 1, 'DEBITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(2, 'R$', 'Compra Cartao Credito', 1, 'CREDITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(3, 'R$', 'PIX Enviar', 1, 'DEBITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(4, 'R$', 'PIX Receber', 1, 'CREDITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(5, 'R$', 'Salario', 1, 'CREDITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(6, 'R$', 'Saque', 1, 'DEBITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(7, 'R$', 'Pagamento Aplicativo', 1, 'DEBITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(8, 'US$', 'Compra Cartao Credito Dolar', 1, 'DEBITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(9, '€', 'Compra Cartao Credito Euro', 1, 'DEBITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(10, '¥', 'Compra Cartao Credito Iene', 1, 'DEBITO', CURRENT_TIMESTAMP());

INSERT INTO tipo_transacao (id, moeda, descricao, ativo, tipo, data_criacao)
values(11, '$', 'Compra Cartao Credito Peso Argentino', 1, 'DEBITO', CURRENT_TIMESTAMP());
