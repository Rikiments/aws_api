# --- ESTÁGIO 1: Build (Construção) ---
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos do projeto para o contêiner
COPY . .

# Roda o comando do Maven para "empacotar" o projeto
RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: Run (Execução) ---
FROM eclipse-temurin:21-jre

# Define o diretório de trabalho
WORKDIR /app

# Copia APENAS o arquivo .jar que foi gerado no estágio "builder"
COPY --from=builder /app/target/*.jar app.jar

# Diz ao Spring para usar o perfil "prod"
ENV SPRING_PROFILES_ACTIVE=prod

# Expõe a porta 8080 (a porta padrão do Spring Boot)
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner subir
ENTRYPOINT ["java", "-jar", "app.jar"]