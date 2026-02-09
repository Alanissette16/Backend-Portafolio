# Etapa 1: Construcción (Build)
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiar archivos del wrapper de Gradle y configuración
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Dar permisos de ejecución al gradlew
RUN chmod +x gradlew

# Descargar dependencias (cache)
RUN ./gradlew dependencies --no-daemon

# Copiar el código fuente
COPY src src

# Construir el proyecto (omitiendo tests para rapidez)
RUN ./gradlew build -x test --no-daemon

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto del backend (por defecto 8080)
EXPOSE 8080

# Iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
