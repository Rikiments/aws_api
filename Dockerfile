# --- ESTÁGIO 1: Build (Construção) ---
# MUDADO: Usando imagem com Maven e OpenJDK 21
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos do projeto para o contêiner
COPY . .

# Roda o comando do Maven para "empacotar" o projeto (e pula os testes)
RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: Run (Execução) ---
# MUDADO: Usando imagem "slim" apenas com o OpenJDK 21
FROM openjdk:21-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia APENAS o arquivo .jar que foi gerado no estágio "builder"
COPY --from=builder /app/target/*.jar app.jar

# !! PONTO-CHAVE !!
# Diz ao Spring para usar o perfil "prod", ativando o application-prod.properties
ENV SPRING_PROFILES_ACTIVE=prod

# Expõe a porta 8080 (a porta padrão do Spring Boot)
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner subir
ENTRYPOINT ["java", "-jar", "app.jar"]