# Use uma imagem base do Java com Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Copie os arquivos do projeto para o container
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

# Construa a aplicação usando Maven
RUN mvn -f /usr/src/app/pom.xml clean package

# Use uma imagem base do Java para a aplicação final
FROM openjdk:17-slim

# Copie o JAR construído do estágio anterior para o container
COPY --from=build /usr/src/app/target/*.jar app.jar

# Exponha a porta da sua aplicação (geralmente 8080)
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]